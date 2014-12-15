package com.vi.xson.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vi.xson.Xson;
import com.vi.xson.exceptions.IncopatibleJsonException;
import com.vi.xson.exceptions.NotJsonnableException;

public class JsonArray extends Json {

	public static enum ArrayType {
		BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, OBJECT, LIST, SET, NOT_ARRAY
	}

	private Object arr;
	private ArrayType arrayType;

	public JsonArray(Object arr) {
		this.arr = arr;
		arrayType = getArrayTypeOf(arr);
		if (ArrayType.NOT_ARRAY == arrayType) {
			throw new IncopatibleJsonException(IncopatibleJsonException.NOT_ARRAY_TYPE);
		}
	}

	public static ArrayType getArrayTypeOf(Object obj) {
		if (null == obj) {
			return ArrayType.NOT_ARRAY;
		}
		if (obj.getClass().isArray()) {
			// case for primitive type array
			if (obj instanceof double[]) {
				return ArrayType.DOUBLE;
			} else if (obj instanceof byte[]) {
				return ArrayType.BYTE;
			} else if (obj instanceof short[]) {
				return ArrayType.SHORT;
			} else if (obj instanceof int[]) {
				return ArrayType.INT;
			} else if (obj instanceof long[]) {
				return ArrayType.LONG;
			} else if (obj instanceof float[]) {
				return ArrayType.FLOAT;
			} else {
				return ArrayType.OBJECT;
			}
		} else if (obj instanceof List) {
			return ArrayType.LIST;
		} else if (obj instanceof Set) {
			return ArrayType.SET;
		} else {
			return ArrayType.NOT_ARRAY;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (null == arr) {
			sb.append("null");
		} else {
			sb.append("[");

			if (arrayType == ArrayType.DOUBLE) {
				f(sb, (double[]) arr);
			} else if (arrayType == ArrayType.BYTE) {
				f(sb, (byte[]) arr);
			} else if (arrayType == ArrayType.SHORT) {
				f(sb, (short[]) arr);
			} else if (arrayType == ArrayType.INT) {
				f(sb, (int[]) arr);
			} else if (arrayType == ArrayType.LONG) {
				f(sb, (long[]) arr);
			} else if (arrayType == ArrayType.FLOAT) {
				f(sb, (float[]) arr);
			} else if (arrayType == ArrayType.OBJECT) {
				f(sb, (Object[]) arr);
			} else if (arrayType == ArrayType.LIST) {
				@SuppressWarnings({ "rawtypes" })
				List a = (List) arr;
				listCase(sb, a);
			} else if (arrayType == ArrayType.SET) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				List a = new ArrayList((Set) arr);
				listCase(sb, a);
			} else {
				throw new RuntimeException("Property is not an array or collection.");
			}

			sb.append("]");
		}
		return sb.toString();
	}

	private void listCase(StringBuilder sb, @SuppressWarnings("rawtypes") List a) {
		for (int i = 0; i < a.size(); i++) {
			Object currProperty = getCurrProperty(a.get(i));
			sb.append(objectToJsonString(currProperty));
			if (i < a.size() - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, Object[] a) {
		for (int i = 0; i < a.length; i++) {
			Object currProperty = getCurrProperty(a[i]);
			sb.append(objectToJsonString(currProperty));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, byte[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, short[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, int[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, long[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, float[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	private void f(StringBuilder sb, double[] a) {
		for (int i = 0; i < a.length; i++) {
			sb.append(objectToJsonString(a[i]));
			if (i < a.length - 1) {
				sb.append(", ");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private Object getCurrProperty(Object currProperty) {
		try {
			currProperty = Xson.toJsonObject(currProperty);
		} catch (IncopatibleJsonException e) {
			if (e.getMessage().equals(IncopatibleJsonException.ARRAY_TYPE)) {
				switch (getArrayTypeOf(currProperty)) {
				case BYTE:
					currProperty = Xson.toJsonArray((byte[]) currProperty);
				case SHORT:
					currProperty = Xson.toJsonArray((short[]) currProperty);
				case INT:
					currProperty = Xson.toJsonArray((int[]) currProperty);
				case LONG:
					currProperty = Xson.toJsonArray((long[]) currProperty);
				case FLOAT:
					currProperty = Xson.toJsonArray((float[]) currProperty);
				case DOUBLE:
					currProperty = Xson.toJsonArray((double[]) currProperty);
				case LIST:
					currProperty = Xson.toJsonArray((List) currProperty);
				case SET:
					currProperty = Xson.toJsonArray((Set) currProperty);
				default:
					currProperty = Xson.toJsonArray((Object[]) currProperty);
				}
			}
		} catch (NotJsonnableException e) {
			// do nothing
		}
		return currProperty;
	}

	public static boolean isJsonArrayRepresentitave(Object value) {
		return value.getClass().isArray() || value instanceof List || value instanceof Set;
	}

	@Override
	protected int protertiesCount() {
		return 1;
	}

	public static JsonArray requireJsonArray(Object object) {
		if (!(isJsonArray(object))) {
			throw new IncopatibleJsonException(IncopatibleJsonException.NOT_ARRAY_TYPE);
		}
		return (JsonArray) object;
	}

	public static boolean isJsonArray(Object object) {
		return object instanceof JsonArray;
	}

}
