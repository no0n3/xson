package com.vi.xson.object.api;

import com.vi.xson.Util;

public class Field {
	private String name;

	public Field(String name) {
		if (!Util.isStringPresent(name)) {
			throw new RuntimeException("Invalid property name.");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
