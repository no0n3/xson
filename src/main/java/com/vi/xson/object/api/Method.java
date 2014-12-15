package com.vi.xson.object.api;

import com.vi.xson.Util;

public class Method {
	private String name;

	public Method(String name) {
		if (!Util.isStringPresent(name)) {
			throw new RuntimeException("Invalid property name.");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
