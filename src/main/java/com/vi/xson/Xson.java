package com.vi.xson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vi.xson.object.JsonArray;
import com.vi.xson.object.JsonObject;

public class Xson {

	static final String NO_TAG = "";

	private Xson() {
	}

	/**
	 * Constructs a JsonObject from the given json.
	 * 
	 * @param json
	 *            - target json.
	 * @return a new JsonObject that represents the json passed.
	 */
	public static JsonObject fromJsonToJsonObject(String json) {
		Map<String, Object> map = fromJsonToMap(json);
		if (null == map) {
			return null;
		} else {
			return mapToJsonObject(map);
		}
	}

	/**
	 * Constructs a Map from the given json.
	 * 
	 * @param json
	 *            - target json.
	 * @return a new Map that represents the json passed.
	 */
	public static Map<String, Object> fromJsonToMap(String json) {
		return FromJsonFacade.JSON_TO_MAP(json);
	}

	/**
	 * Constructs a JsonArray from the given json.
	 * 
	 * @param json
	 *            - target json.
	 * @return a new JsonArray that represents the json passed.
	 */
	public static JsonArray fromJsonToJsonArray(String json) {
		return toJsonArray(fromJsonToList(json));
	}

	/**
	 * Constructs a List from the given json.
	 * 
	 * @param json
	 *            - target json.
	 * @return a new List that represents the json passed.
	 */
	public static List<Object> fromJsonToList(String json) {
		return FromJsonFacade.JSON_TO_LIST(json);
	}

	/**
	 * Constructs a Object from the given json.
	 * 
	 * @param json
	 *            - target json.
	 * @param cls
	 *            - the Object's class.
	 * @return a new Object that represents the json passed.
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		return FromJsonFacade.FROM_JSON(json, cls);
	}

	public static String toJson(Object objectToJson) {
		return toJson(objectToJson, NO_TAG);
	}

	/**
	 * Converts an object to a string representing json form of this object.
	 * 
	 * @param objectToJson
	 *            - the object which to be transformed to json.
	 * @param tag
	 *            - the tag name. If the tag name is null or empty string then
	 *            it is treated as no tag at all.
	 * @return the object's json representative.
	 */
	public static String toJson(Object objectToJson, String tag) {
		if (null == objectToJson) {
			return "null";
		} else {
			return toJsonObject(objectToJson, tag).toString();
		}
	}

	public static JsonObject toJsonObject(Object objectToJson) {
		return toJsonObject(objectToJson, NO_TAG);
	}

	public static JsonObject toJsonObject(Object objectToJson, String tag) {
		return new ToJsonFacade().toJsonObject(objectToJson, tag);
	}

	/**
	 * Transforms a Map to a string representing the json form of this map.
	 * 
	 * @param map
	 *            - the map which to be transformed to json.
	 * @return a string that represents the json form of the map.
	 */
	public static <Key, Value> String mapToJson(Map<Key, Value> map) {
		return new ToJsonFacade().mapToJsonObject(map).toString();
	}

	/**
	 * Transforms a Map to a JsonObject. Example: <br>
	 * Map map = new HashMap() {<br>
	 * {<br>
	 * put("p1", 123);<br>
	 * put("p2", "sample string...");<br>
	 * }<br>
	 * };<br>
	 * Xson.mapToJsonObject(map).toString(); // will produce: {"p1" : 123, "p2"
	 * : "sample string..."}
	 * 
	 * @param map
	 *            - the map which to be transformed to a JsonObject.
	 * @return a JsonObject that represents the map.
	 */
	public static <Key, Value> JsonObject mapToJsonObject(Map<Key, Value> map) {
		return new ToJsonFacade().mapToJsonObject(map);
	}

	/**
	 * Transforms a byte array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(byte[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a short array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(short[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a int array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(int[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a long array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(long[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a float array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(float[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a double array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static String arrayToJson(double[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a double array to a string representing the json form of this
	 * array.
	 * 
	 * @param array
	 *            - the array which to be transformed to json.
	 * @return a string that represents the json form of the array.
	 */
	public static <T> String arrayToJson(T[] array) {
		return toJsonArray(array).toString();
	}

	/**
	 * Transforms a List to a string representing the json form of this list.
	 * 
	 * @param list
	 *            - the list which to be transformed to json.
	 * @return a string that represents the json form of the list.
	 */
	public static String arrayToJson(@SuppressWarnings("rawtypes") List list) {
		JsonArray jsonArray = toJsonArray(list);
		if (null == jsonArray) {
			return "null";
		} else {
			return jsonArray.toString();
		}
	}

	/**
	 * Transforms a Set to a string representing the json form of this set.
	 * 
	 * @param array
	 *            - the set which to be transformed to json.
	 * @return a string that represents the json form of the set.
	 */
	public static String arrayToJson(@SuppressWarnings("rawtypes") Set set) {
		JsonArray jsonArray = toJsonArray(set);
		if (null == jsonArray) {
			return "null";
		} else {
			return jsonArray.toString();
		}
	}

	/**
	 * Transforms a List to a JsonArray.
	 * 
	 * @param list
	 *            - the List to be transformed.
	 * @return the JsonArray representative of the List.
	 */
	public static JsonArray toJsonArray(@SuppressWarnings("rawtypes") List list) {
		if (null == list) {
			return null;
		} else {
			return new ToJsonFacade().toJsonArray(list);
		}
	}

	/**
	 * Transforms a Set to a JsonArray.
	 * 
	 * @param set
	 *            - the Set to be transformed.
	 * @return the JsonArray representative of the Set.
	 */
	public static JsonArray toJsonArray(@SuppressWarnings("rawtypes") Set set) {
		if (null == set) {
			return null;
		} else {
			return new ToJsonFacade().toJsonArray(set);
		}
	}

	/**
	 * Transforms an array of objects to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static <T> JsonArray toJsonArray(T[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a byte array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(byte[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a short array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(short[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a int array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(int[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a long array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(long[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a float array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(float[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

	/**
	 * Transforms a double array to a JsonArray.
	 * 
	 * @param array
	 *            - the array to be transformed.
	 * @return the JsonArray representative of the array.
	 */
	public static JsonArray toJsonArray(double[] array) {
		return new ToJsonFacade().toJsonArray(array);
	}

}