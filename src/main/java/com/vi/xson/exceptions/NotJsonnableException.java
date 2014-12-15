package com.vi.xson.exceptions;

public class NotJsonnableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotJsonnableException() {
		super();
	}

	public NotJsonnableException(String msg) {
		super(msg);
	}
}
