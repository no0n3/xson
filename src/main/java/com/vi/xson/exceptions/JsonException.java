package com.vi.xson.exceptions;

public class JsonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JsonException() {
		super();
	}

	public JsonException(String msg) {
		super(msg);
	}
}
