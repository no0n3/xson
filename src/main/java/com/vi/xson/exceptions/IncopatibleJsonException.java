package com.vi.xson.exceptions;

public class IncopatibleJsonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String PRIMITIVE_TYPE = "Cannot convert a primitive type to JsonObject.";
	public static final String ARRAY_TYPE = "The given object is JsonArray representative.";
	public static final String NOT_ARRAY_TYPE = "The given object is not a JsonArray representative.";

	public IncopatibleJsonException() {
		super();
	}

	public IncopatibleJsonException(String msg) {
		super(msg);
	}
}
