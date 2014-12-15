package com.vi.xson;

import java.util.Map;

import com.vi.xson.annotation.Jsonnable;

public class Util {

	public static boolean isString(Object x) {
		return x instanceof String;
	}

	public static boolean isStringPresent(String str) {
		return (null != str && !isEmptyString(str));
	}

	static boolean isEmptyString(String str) {
		return str.trim().equals("");
	}

	public static boolean isMap(Object value) {
		return value instanceof Map;
	}

	public static boolean isJsonnable(Object obj) {
		@SuppressWarnings("rawtypes")
		Class objectClass = obj.getClass();
		return isJsonnable(objectClass);
	}

	public static boolean isJsonnable(Class<?> cls) {
		return cls.isAnnotationPresent(Jsonnable.class);
	}
	
	public static boolean isPrimitive(Object x) {
		return x instanceof Number || x instanceof Boolean;
	}
}
