package com.vi.xson.object;

import com.vi.xson.Util;
import com.vi.xson.Xson;

abstract public class Json {

	private static enum ProperyType {
		STRING, JSON_OBJECT, SINGLE_PROPERY
	}

	String objectToJsonString(Object i) {
		StringBuilder sb = new StringBuilder();

		ProperyType factor = getFactor(i);

		sb.append(getXbeg(factor));

		if(Util.isJsonnable(i)) {
			sb.append(Xson.toJson(i));
		} else {
			sb.append(i);
		}
		
		sb.append(getXend(factor));

		return sb.toString();
	}

	private ProperyType getFactor(Object o) {
		if (!Util.isPrimitive(o)) {
			if (o instanceof JsonArray) {
				return ProperyType.SINGLE_PROPERY;
			} else if (!JsonObject.isJsonObject(o)) {
				return ProperyType.STRING;
			}
		}
		return ProperyType.SINGLE_PROPERY;
	}

	private String getXbeg(ProperyType factor) {
		if (ProperyType.STRING == factor) {
			return "\"";
		} else if (ProperyType.JSON_OBJECT == factor && hasOneProprty()) {
			return "{";
		} else {
			return "";
		}
	}

	private String getXend(ProperyType factor) {
		if (ProperyType.STRING == factor) {
			return "\"";
		} else if (ProperyType.JSON_OBJECT == factor && hasOneProprty()) {
			return "}";
		} else {
			return "";
		}
	}

	protected boolean hasOneProprty() {
		return 1 == this.protertiesCount();
	}

	protected abstract int protertiesCount();

}
