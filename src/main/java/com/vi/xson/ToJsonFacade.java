package com.vi.xson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;
import com.vi.xson.exceptions.IncopatibleJsonException;
import com.vi.xson.exceptions.JsonException;
import com.vi.xson.exceptions.NotJsonnableException;
import com.vi.xson.object.JsonArray;
import com.vi.xson.object.JsonObject;
import com.vi.xson.object.api.Parent;

class ToJsonFacade {

	private JsonObject jsonObject = new JsonObject();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	JsonObject toJsonObject(Object objectToJson, String tag) {
		if (null == objectToJson) {
			return null;
		} else if (Util.isJsonnable(objectToJson)) {
			try {
				toJson_(objectToJson, tag);
				return jsonObject;
			} catch (Exception e) {
				e.printStackTrace();
				throw new JsonException();
			}
		} else if (Util.isMap(objectToJson)) {
			return Xson.mapToJsonObject((Map) objectToJson);
		} else if (JsonObject.isJsonObject(objectToJson)) {
			return JsonObject.requireJsonObject(objectToJson);
		} else if (JsonArray.isJsonArrayRepresentitave(objectToJson)) {
			throw new IncopatibleJsonException(IncopatibleJsonException.ARRAY_TYPE);
		} else if (Util.isPrimitive(objectToJson)) {
			throw new IncopatibleJsonException(IncopatibleJsonException.PRIMITIVE_TYPE);
		} else {
			throw new NotJsonnableException("Class '" + objectToJson.getClass().getName() + "' is not Jsonnable.");
		}
	}

	@SuppressWarnings("unchecked")
	private void toJson_(Object objectToJson, String targetTag) throws Exception {
		if (null == objectToJson) {
			return;
		}

		@SuppressWarnings("rawtypes")
		Class objectClass = objectToJson.getClass();
		if (objectClass.isAnnotationPresent(Jsonnable.class)) {

			Annotation annotation = objectClass.getAnnotation(Jsonnable.class);
			@SuppressWarnings("unused")
			Jsonnable jsonnable = (Jsonnable) annotation;

			for (Field field : objectClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(toJson.class)) {
					Annotation anot = field.getAnnotation(toJson.class);
					toJson toJsonAnotObj = (toJson) anot;

					String[] tags = toJsonAnotObj.tags();
					if (!hasTag(tags, targetTag)) {
						continue;
					}
					String name = toJsonAnotObj.name();
					if (!Util.isStringPresent(name)) {
						name = field.getName();
					}

					String parent = toJsonAnotObj.parent();

					Object value = getValue(field, objectClass, toJsonAnotObj, objectToJson);

					getObject(name, new Parent(parent), value);
				}
			}
			for (Method method : objectClass.getMethods()) {
				if (method.isAnnotationPresent(toJson.class)) {
					Annotation anot = method.getAnnotation(toJson.class);
					toJson toJsonAnotObj = (toJson) anot;
					method.setAccessible(true);

					String[] tags = toJsonAnotObj.tags();
					if (!hasTag(tags, targetTag)) {
						continue;
					}
					String name = toJsonAnotObj.name();
					if (!Util.isStringPresent(name)) {
						throw new JsonException("No json name specified for method '" + method.getName() + "'.");
					}

					String parent = toJsonAnotObj.parent();

					if (method.getReturnType().equals(void.class)) {
						throw new JsonException("Method '" + method.getName() + "' returns void.");
					}
					Object value = method.invoke(objectToJson);

					getObject(name, new Parent(parent), value);
				}
			}
		}
	}

	private static boolean hasTag(String[] tags, String targetTag) {
		if (null == tags) {
			return true;
		} else if (0 == tags.length || (1 == tags.length && tags[0].equals(Xson.NO_TAG))) {
			return true;
		}
		if (null == targetTag) {
			return false;
		}

		for (String i : tags) {
			if (i.equals(targetTag)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private static Object getValue(Field field, Class objectClass, toJson toJsonAnotObj, Object objectToJson)
			throws Exception {
		Object value;

		String getterName = toJsonAnotObj.getterName();
		field.setAccessible(true);
		if (!Util.isStringPresent(getterName)) {
			value = field.get(objectToJson);
		} else {
			try {
				value = invokeMethod(objectToJson, objectClass, getterName);
			} catch (IllegalAccessException e) {
				value = field.get(objectToJson);
			}
		}

		return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object invokeMethod(Object instance, Class objectClass, String name) throws IllegalAccessException {
		try {
			Method method = objectClass.getMethod(name);
			Object value = method.invoke(instance);
			return value;
		} catch (Exception e) {
			throw new IllegalAccessException("");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getObject(String name, Parent parent, Object value) {

		if (Util.isMap(value)) {
			value = Xson.mapToJsonObject((Map) value);
		} else if (JsonArray.isJsonArrayRepresentitave(value)) {
			value = toJsonArray(value);
		} else if (Util.isJsonnable(value)) {
			value = Xson.toJsonObject(value);
		}

		String[] parents = parent.getParents();

		if (parents.length > 0) {
			JsonObject parentJsonObject;
			if (!jsonObject.containtsPropertyName(parents[0])) {
				jsonObject.addProperty(parents[0], new JsonObject());
			}
			Object foundParent = jsonObject.getProperty(parents[0]);

			parentJsonObject = JsonObject.requireJsonObject(foundParent);

			for (int i = 1; i < parents.length; i++) {
				Object currParentAsObject = parentJsonObject.getProperty(parents[i]);
				JsonObject currParent = JsonObject.requireJsonObject(currParentAsObject);
				if (null == currParent) {
					parentJsonObject.addProperty(parents[i], new JsonObject());
				}
				parentJsonObject = currParent;
			}
			parentJsonObject.addProperty(name, value);
		} else {
			jsonObject.addProperty(name, value);
		}

	}

	<Key, Value> JsonObject mapToJsonObject(Map<Key, Value> map) {
		JsonObject jsonObjectResult = new JsonObject();
		for (Entry<Key, Value> e : map.entrySet()) {
			Object keyObject = e.getKey();
			if (!(keyObject instanceof String)) {
				throw new JsonException("Object name is not String");
			}
			String key = keyObject.toString();
			jsonObjectResult.addProperty(key, getProperty(e.getValue()));
		}
		return jsonObjectResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getProperty(Object objectToJson) {
		if (null == objectToJson) {
			return null;
		} else if (Util.isMap(objectToJson)) {
			return mapToJsonObject((Map) objectToJson);
		} else if (JsonObject.isJsonObject(objectToJson)) {
			return JsonObject.requireJsonObject(objectToJson);
		} else if (JsonArray.isJsonArrayRepresentitave(objectToJson)) {
			return toJsonArray(objectToJson);
		} else if (Util.isPrimitive(objectToJson)) {
			return objectToJson;
		} else if (Util.isJsonnable(objectToJson)) {
			return Xson.toJsonObject(objectToJson);
		} else {
			return objectToJson;
		}
	}

	JsonArray toJsonArray(Object array) {
		return new JsonArray(array);
	}
}

