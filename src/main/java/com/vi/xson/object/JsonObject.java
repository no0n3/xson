package com.vi.xson.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vi.xson.Util;
import com.vi.xson.Xson;
import com.vi.xson.exceptions.IncopatibleJsonException;
import com.vi.xson.exceptions.JsonException;

public class JsonObject extends Json {

	private Map<String, Object> jsonObjetProperties = new HashMap<String, Object>();

	public JsonObject() {
	}

	public JsonObject(String name, Object property) {
		addProperty(name, property);
	}

	public JsonObject addProperty(String name, Object objectToBeAdded) {
		if (containtsPropertyName(name)) {
			JsonObject jsonObjectToBeAdded = null;
			JsonObject foundJsonObject = null;
			try {
				jsonObjectToBeAdded = requireJsonObject(objectToBeAdded);
				foundJsonObject = requireJsonObject(getProperty(name));
			} catch (JsonException e) {
				throw new JsonException("Cannot add value '" + objectToBeAdded.toString()
						+ "' because property with name '" + name + "' alredy exists.");
			}
			foundJsonObject.addAllProperties(jsonObjectToBeAdded);
			return this;
		} else {
			objectToBeAdded = turnToJsonIfCan(objectToBeAdded);
			jsonObjetProperties.put(name, objectToBeAdded);
		}
		return this;
	}

	private static Object turnToJsonIfCan(Object objectToBeConverted) {
		if (!Util.isString(objectToBeConverted) && !Util.isPrimitive(objectToBeConverted)
				&& !(objectToBeConverted instanceof JsonArray)) {
			try {
				objectToBeConverted = Xson.toJsonObject(objectToBeConverted);
			} catch (IncopatibleJsonException e) {
				if (e.getMessage().equals(IncopatibleJsonException.ARRAY_TYPE)) {
					objectToBeConverted = new JsonArray(objectToBeConverted);
				}
			}
		}
		return objectToBeConverted;
	}

	public static JsonObject requireJsonObject(Object object) {
		if (!(isJsonObject(object))) {
			throw new IncopatibleJsonException("Object marked as parent is not an json object.");
		}
		return (JsonObject) object;
	}

	public static boolean isJsonObject(Object object) {
		return object instanceof JsonObject;
	}

	public boolean containtsPropertyName(String name) {
		return jsonObjetProperties.containsKey(name);
	}

	private void addAllProperties(JsonObject otherObject) {
		for (Entry<String, Object> i : otherObject.jsonObjetProperties.entrySet()) {
			addProperty(i.getKey(), i.getValue());
		}
	}

	public Object getProperty(String propertyName) {
		return this.jsonObjetProperties.get(propertyName);
	}

	public byte getByte(String propertyName) {
		return (byte) this.jsonObjetProperties.get(propertyName);
	}

	public short getShort(String propertyName) {
		return (short) this.jsonObjetProperties.get(propertyName);
	}

	public int getInt(String propertyName) {
		return (int) this.jsonObjetProperties.get(propertyName);
	}

	public long getLong(String propertyName) {
		return (long) this.jsonObjetProperties.get(propertyName);
	}

	public float getFloat(String propertyName) {
		return (float) this.jsonObjetProperties.get(propertyName);
	}

	public double getDouble(String propertyName) {
		return (double) this.jsonObjetProperties.get(propertyName);
	}

	public JsonArray getJsonArray(String propertyName) {
		Object value = this.jsonObjetProperties.get(propertyName);
		// if (value instanceof JsonArray) {
		return JsonArray.requireJsonArray(value);
		// } else {
		// return new JsonArray(value);
		// }
	}

	public JsonObject getJsonObject(String propertyName) {
		Object value = this.jsonObjetProperties.get(propertyName);
		// if (value instanceof JsonObject) {
		return requireJsonObject(value);
		// } else {
		// return Xson.toJsonObject(value);
		// }
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int c = 0;
		for (Entry<String, Object> i : jsonObjetProperties.entrySet()) {
			sb.append(objectToJsonString(i.getKey()) + " : ");
			sb.append(objectToJsonString(i.getValue()));
			if (c++ < jsonObjetProperties.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	protected int protertiesCount() {
		return jsonObjetProperties.size();
	}
}