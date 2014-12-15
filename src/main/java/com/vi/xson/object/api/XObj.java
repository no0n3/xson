package com.vi.xson.object.api;

import com.vi.xson.Util;

public class XObj {
	private String name;
	private Parent parent;
	private Method method;
	private Field field;

	public XObj() {
	}

	public XObj(String name) {
		if (!Util.isStringPresent(name)) {
			throw new RuntimeException("Invalid name.");
		}
	}

	public XObj(String parent, String name) {
		if (!Util.isStringPresent(name)) {
			throw new RuntimeException("Invalid name.");
		}
		this.parent = new Parent(parent);
	}

	public XObj(Method method) {
		if (!Util.isStringPresent(method.getName())) {
			throw new RuntimeException("Invalid method.");
		}
		this.method = method;
		setName();
	}

	public XObj(Field field) {
		if (!Util.isStringPresent(field.getName())) {
			throw new RuntimeException("Invalid field.");
		}
		this.field = field;
		this.name = field.getName();
		this.parent = new Parent();
	}

	public XObj setField(String fieldName) {
		if (null != method) {
			throw new RuntimeException("Method is alredy set.");
		}
		this.field = new Field(fieldName);
		this.name = field.getName();
		return this;
	}

	public XObj setParent(String parentName) {
		this.parent = new Parent(parentName);
		return this;
	}

	public XObj setName(String name) {
		if (!Util.isStringPresent(name)) {
			throw new RuntimeException();
		}
		this.name = name;
		return this;
	}

	public XObj setGetter(String method) {
		if (null != field) {
			throw new RuntimeException("Field is alredy set.");
		}
		this.method = new Method(method);
		setName();
		return this;
	}

	public boolean hasMethod() {
		return null != method;
	}

	private void setName() {
		if (!Util.isStringPresent(name)) {
			if (null == method) {
				return;
			}
			String methodName = method.getName();
			if (methodName.startsWith("get")) {
				this.name = methodName.substring(3);
				char[] c = name.toCharArray();
				c[0] = (char) (c[0] ^ 32);
				this.name = new String(c);
			} else {
				this.name = methodName;
			}
		}
	}

	public String getName() {
		return name;
	}

	public Parent getParent() {
		if (null == parent) {
			parent = new Parent();
		}
		return parent;
	}

	public Field getField() {
		return field;
	}

	public String getMethodName() {
		if (null == method) {
			return null;
		}
		return method.getName();
	}
}
