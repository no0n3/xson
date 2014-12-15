package com.vi.xson.object.api;

import java.util.ArrayList;
import java.util.List;

public class Parent {
	private String name;

	public Parent() {
		this("");
	}

	public Parent(String name) {
		if (null == name) {
			name = "";
		} else {
			name = name.trim();
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean hasParent() {
		return validParentExpr(this.name);
	}

	public String[] getParents() {
		List<String> parents = new ArrayList<String>();
		for (String currParent : name.split("/")) {
			if (!currParent.equals("") && validParentExpr(currParent)) {
				parents.add(currParent);
			}
		}
		return parents.toArray(new String[parents.size()]);
	}

	private boolean validParentExpr(String parent) {
		if (null == parent || parent.trim().equals("")) {
			return false;
		}
		return true;
	}
}
