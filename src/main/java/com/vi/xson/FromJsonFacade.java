package com.vi.xson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vi.xson.exceptions.JsonException;

class FromJsonFacade {

	private static enum Operation {
		ADDED_NEW, TO_BE_ADDED, NONE
	}

	private char[] c;

	private FromJsonFacade(String json) {
		if (null != json) {
			json = json.trim();
			this.c = json.toCharArray();
		}
	}

	private static final char CURLY_BRACKET_OPEN = '{';
	private static final char CURLY_BRACKET_CLOSE = '}';

	private static final char BRACKET_OPEN = '[';
	private static final char BRACKET_CLOSE = ']';

	private void unexpectedSymbol(int currentPosition) {
		String line = "";
		for (int i = currentPosition, x = 0; x < 15 && i < c.length; i++, x++) {
			line += c[i];
		}
		String msg = "Unexpected symbol '" + c[currentPosition] + "' at line " + currentPosition + " '" + line + "'";
		throw new RuntimeException(msg);
	}

	private boolean startsWithCurlyBracket() {
		return !isJsonNull() && CURLY_BRACKET_OPEN == c[0];
	}

	private boolean startsWithBracket() {
		return !isJsonNull() && BRACKET_OPEN == c[0];
	}

	private boolean isJsonNull() {
		return (null == this.c || (4 <= c.length && 'n' == c[0] && 'u' == c[1] && 'l' == c[2] && 'l' == c[3]));
	}

	static <T> T FROM_JSON(String json, Class<T> cls) {
		return new FromJsonFacade(json).fromJson(cls);
	}

	private <T> T fromJson(Class<T> cls) {
		T instance = null;

		if (isJsonNull()) {
			return null;
		}
		try {
			instance = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		if (!startsWithCurlyBracket()) {
			throw new JsonException("Json object must start with '" + CURLY_BRACKET_OPEN + "'");
		}

		int beg = 1, end = c.length - 1;
		try {
			objectCase(beg, end, instance);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
				| InstantiationException e) {
			throw new RuntimeException(e);
		}

		return instance;
	}

	static Map<String, Object> JSON_TO_MAP(String json) {
		return new FromJsonFacade(json).jsonToMap();
	}

	private Map<String, Object> jsonToMap() {
		if (isJsonNull()) {
			return null;
		}
		if (!startsWithCurlyBracket()) {
			throw new JsonException("Json object must start with '" + CURLY_BRACKET_OPEN + "'");
		}

		int beg = 1, end = c.length - 1;

		return jsonToMap(beg, end);
	}

	private void objectCase(int beg, int end, Object objectInstance) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException {
		Operation oper = Operation.NONE;

		String name = null;

		for (; beg <= end; beg++) {
			switch (c[beg]) {
			case ' ':
				break;
			case ',': {
				if (Operation.ADDED_NEW != oper) {
					unexpectedSymbol(beg);
				}
				oper = Operation.NONE;
				break;
			}
			case '\"': {
				if (Operation.NONE != oper) {
					unexpectedSymbol(beg);
				}
				beg++;
				List<Character> nameChars = new ArrayList<Character>();
				while (c[beg] != '\"') {
					nameChars.add(c[beg++]);
				}
				name = charListToString(nameChars);
				oper = Operation.TO_BE_ADDED;
				break;
			}
			case ':': {
				if (Operation.TO_BE_ADDED != oper) {
					unexpectedSymbol(beg);
				}
				beg++;
				while (c[beg] == ' ') {
					beg++;
				}
				Class<?> cl = objectInstance.getClass();
				Field field = cl.getField(name);
				field.setAccessible(true);

				String value;

				switch (c[beg]) {
				case CURLY_BRACKET_OPEN: {
					int i = tilClosingBracket(beg, true);

					Object fieldInstance = field.getType().newInstance();
					objectCase(beg + 1, i - 1, fieldInstance);
					beg = i;
					field.set(objectInstance, fieldInstance);
					oper = Operation.ADDED_NEW;
					break;
				}
				case BRACKET_OPEN: {
					int i = tilClosingBracket(beg, false);

					Object collectionInst;

					// TODO fix cases of primitive arrays.
					if (field.getType().isArray()) {
						collectionInst = Array.newInstance(field.getType(), 1);
					} else {
						// TODO check if Collection. If it is collection
						// interface instanciate it with HashSet or arrayList.
						collectionInst = field.getType().newInstance();
					}

					arrayCase(beg + 1, i - 1, collectionInst);
					beg = i;
					field.set(objectInstance, collectionInst);
					oper = Operation.ADDED_NEW;
					break;
				}
				default: {
					if (c[beg] == '\"') {
						beg++;
						List<Character> valueL = new ArrayList<Character>();
						while (c[beg] != '\"') {
							valueL.add(c[beg++]);
						}
						value = charListToString(valueL);
						oper = Operation.ADDED_NEW;
					} else {
						if (isStringType(field)) {
							throw new RuntimeException("String must be surounded by '\"'.");
						}
						List<Character> valueL = new ArrayList<Character>();
						while (beg < c.length && (c[beg] != ',' && c[beg] != CURLY_BRACKET_CLOSE)) {
							valueL.add(c[beg++]);
						}
						value = charListToString(valueL);
						oper = Operation.NONE;
					}

					setFieldValue(field, objectInstance, value);
				}
				}
				break;
			}
			case CURLY_BRACKET_CLOSE:
				if (beg == end) {
					break;
				}
			default:
				unexpectedSymbol(beg);
			}
		}
	}

	private static boolean isStringType(Field field) {
		return field.getType().equals(String.class);
	}

	private void setFieldValue(Field field, Object objectInstance, String value) throws NumberFormatException,
			IllegalArgumentException, IllegalAccessException {
		if (field.getType().equals(byte.class)) {
			field.set(objectInstance, Byte.parseByte(value));
		} else if (field.getType().equals(Byte.class)) {
			field.set(objectInstance, new Byte(value));
		}

		else if (field.getType().equals(short.class)) {
			field.set(objectInstance, Short.parseShort(value));
		} else if (field.getType().equals(Short.class)) {
			field.set(objectInstance, new Short(value));
		}

		else if (field.getType().equals(int.class)) {
			field.set(objectInstance, Integer.parseInt(value));
		} else if (field.getType().equals(Integer.class)) {
			field.set(objectInstance, new Integer(value));
		}

		else if (field.getType().equals(long.class)) {
			field.set(objectInstance, Long.parseLong(value));
		} else if (field.getType().equals(Long.class)) {
			field.set(objectInstance, new Long(value));
		}

		else if (field.getType().equals(float.class)) {
			field.set(objectInstance, Float.parseFloat(value));
		} else if (field.getType().equals(Float.class)) {
			field.set(objectInstance, new Float(value));
		}

		else if (field.getType().equals(double.class)) {
			field.set(objectInstance, Double.parseDouble(value));
		} else if (field.getType().equals(Double.class)) {
			field.set(objectInstance, new Double(value));
		}

		else if (field.getType().equals(Number.class)) {
			field.set(objectInstance, new Integer(value));
		}

		else {
			field.set(objectInstance, value);
		}
	}

	private Map<String, Object> jsonToMap(int beg, int end) {

		Operation oper = Operation.NONE;

		Map<String, Object> map = new HashMap<String, Object>();

		String name = null;

		for (; beg <= end; beg++) {
			switch (c[beg]) {
			case ' ':
				break;
			case ',':
				if (Operation.ADDED_NEW != oper) {
					unexpectedSymbol(beg);
				}
				oper = Operation.NONE;
				break;
			case '\"':
				if (Operation.NONE != oper) {
					unexpectedSymbol(beg);
				}
				beg++;
				List<Character> nameL = new ArrayList<Character>();
				while (c[beg] != '\"') {
					nameL.add(c[beg++]);
				}
				name = charListToString(nameL);
				oper = Operation.TO_BE_ADDED;
				break;
			case ':':
				if (Operation.TO_BE_ADDED != oper) {
					unexpectedSymbol(beg);
				}
				beg++;
				while (c[beg] == ' ') {
					beg++;
				}

				String value;

				switch (c[beg]) {
				case CURLY_BRACKET_OPEN: {
					int i = tilClosingBracket(beg, true);

					Map<String, Object> nestedMap = jsonToMap(beg + 1, i - 1);
					map.put(name, nestedMap);
					beg = i;
					oper = Operation.ADDED_NEW;
					break;
				}
				case BRACKET_OPEN: {
					int i = tilClosingBracket(beg, false);
					List<Object> collectionInst = new ArrayList<Object>();

					map.put(name, arrayCase(beg + 1, i - 1, collectionInst));
					beg = i;
					oper = Operation.ADDED_NEW;
					break;
				}
				default:
					boolean b;
					if (c[beg] == '\"') {
						b = true;
						beg++;
						List<Character> valueL = new ArrayList<Character>();
						while (c[beg] != '\"') {
							valueL.add(c[beg++]);
						}
						value = charListToString(valueL);
						oper = Operation.ADDED_NEW;
					} else {
						b = false;
						List<Character> valueL = new ArrayList<Character>();
						while (beg < c.length && (c[beg] != ',' && c[beg] != CURLY_BRACKET_CLOSE)) {
							valueL.add(c[beg++]);
						}
						beg--;
						value = charListToString(valueL);
						// oper = Operation.NONE;
						oper = Operation.ADDED_NEW;
					}

					try {
						map.put(name, Double.parseDouble(value));
					} catch (Exception e) {
						if (b) {
							map.put(name, value);
						} else {
							throw new RuntimeException(e);
						}
					}
				}
				break;
			case CURLY_BRACKET_CLOSE:
				if (beg == end) {
					break;
				}
			default:
				unexpectedSymbol(beg);
			}
		}
		return map;
	}

	static List<Object> JSON_TO_LIST(String json) {
		return new FromJsonFacade(json).jsonToList();
	}

	private List<Object> jsonToList() {
		if (isJsonNull()) {
			return null;
		}
		if (!startsWithBracket()) {
			throw new JsonException("Json array must start with '" + BRACKET_OPEN + "'");
		}

		int beg = 1, end = c.length - 1;

		return (List<Object>) arrayCase(beg, end, new ArrayList());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object arrayCase(int beg, int end, Object o) {

		Operation status = Operation.TO_BE_ADDED;

		String value = null;

		List list = null;
		// TODO fix issues.
		// Issues:
		// currently works only with a Collection of Objects.

		// Determines the type of the array. Currently not implemented.
		// JsonArray.ArrayType arrayType = ArrayType.NOT_ARRAY;

		boolean isArray = o.getClass().isArray();
		if (isArray) {
			list = new ArrayList();
		} else if (o instanceof List) {
			list = (List) o;
		} else if (o instanceof Set) {
			list = new ArrayList((Set) o);
		} else {
			list = new ArrayList();
		}

		for (; beg <= end; beg++) {
			switch (c[beg]) {
			case ' ':
				break;
			case ',':
				if (Operation.ADDED_NEW != status) {
					unexpectedSymbol(beg);
				} else {
					status = Operation.TO_BE_ADDED;
				}
				break;
			case CURLY_BRACKET_OPEN: {
				if (Operation.TO_BE_ADDED != status) {
					unexpectedSymbol(beg);
				}
				int i = tilClosingBracket(beg, true);

				list.add(jsonToMap(beg + 1, i - 1));
				beg = i;
				status = Operation.ADDED_NEW;
				break;
			}
			case BRACKET_OPEN: {
				if (Operation.TO_BE_ADDED != status) {
					unexpectedSymbol(beg);
				}
				int i = tilClosingBracket(beg, false);

				List nestedList = new ArrayList();
				list.add(arrayCase(beg + 1, i - 1, nestedList));
				beg = i;
				status = Operation.ADDED_NEW;
				break;
			}
			case BRACKET_CLOSE:
				if (beg != end) {
					unexpectedSymbol(beg);
				}
				break;
			default: {
				if (Operation.TO_BE_ADDED != status) {
					unexpectedSymbol(beg);
				}
				boolean b;
				if (c[beg] == '\"') {
					b = true;
					beg++;
					List<Character> valueL = new ArrayList<Character>();
					while (c[beg] != '\"') {
						valueL.add(c[beg++]);
					}
					value = charListToString(valueL);
					status = Operation.ADDED_NEW;
				} else {
					b = false;
					List<Character> valueL = new ArrayList<Character>();
					while (beg < c.length && (c[beg] != ',' && c[beg] != BRACKET_CLOSE)) {
						valueL.add(c[beg++]);
					}
					beg--;
					value = charListToString(valueL);
					status = Operation.ADDED_NEW;
				}

				try {
					list.add(Double.parseDouble(value));
				} catch (Exception e) {
					if (b) {
						list.add(value);
					} else {
						throw new RuntimeException(e);
					}
				}
			}
			}
		}
		if (isArray) {
			return list.toArray();
		} else {
			return list;
		}
	}

	private String charListToString(List<Character> list) {
		return new String(charListToArray(list));
	}

	private char[] charListToArray(List<Character> list) {
		char[] c = new char[list.size()];
		for (int i = 0; i < list.size(); i++) {
			c[i] = list.get(i);
		}
		return c;
	}

	/**
	 * Gets the position of the closing bracket.
	 * 
	 * @param position
	 *            - current position.
	 * @param tilCurlyBracket
	 *            - if true return the closing curly bracket '}' of the current
	 *            json object, otherwise returns the position of the closing
	 *            bracket ']'.
	 * @return position of the closing bracket or curly bracket.
	 */
	private int tilClosingBracket(int position, boolean tilCurlyBracket) {
		final char openningBraket;
		final char closingBraket;

		if (tilCurlyBracket) {
			openningBraket = CURLY_BRACKET_OPEN;
			closingBraket = CURLY_BRACKET_CLOSE;
		} else {
			openningBraket = BRACKET_OPEN;
			closingBraket = BRACKET_CLOSE;
		}

		int i;
		int openBrackets = 1;
		for (i = position + 1;; i++) {
			if (i >= c.length) {
				throw new RuntimeException();
			}
			if (c[i] == openningBraket) {
				openBrackets++;
			} else if (c[i] == closingBraket) {
				openBrackets--;
				if (0 == openBrackets) {
					break;
				}
			}
		}
		return i;
	}

}