package test.xson.from.object.from_json;

import java.util.ArrayList;

import com.vi.xson.Xson;

public class FromJson_2 {

	public static void main(String[] args) {
		String json = "{\"i1\":-421,\"tc\":{\"i1\":321,\"str\":\"sample string..\",\"tc2\":{\"i1\":321,\"str\":\"sample string..1\"}},"
				+ "\"tc1\":{\"i1\":-111,\"str\":\"!!dsa string..\",\"tc2\":{\"i1\":1111,\"str\":\"-|ss dsa dsa d|-\",\"_tc\":{\"i1\":-421,\"tc\":{\"i1\":321,\"str\":\"sample string..\",\"tc2\":{\"i1\":321,\"str\":\"sample string..1\"}},"
				+ "\"tc1\":{\"i1\":-111,\"str\":\"!!dsa string..\",\"tc2\":{\"i1\":1111,\"str\":\"-|ss dsa dsa d|-\"}},"
				+ "\"tc2\":{\"i1\":222,\"str\":\"sample DSAD Dsa..\",\"tc2\":{\"i1\":432.02,\"str\":\"<-->\"}}"
				+ "}}},"
				+ "\"tc2\":{\"i1\":222,\"str\":\"sample DSAD Dsa..\",\"tc2\":{\"i1\":432.02,\"str\":\"<-->\","
				+ "\"_tc\":{\"i1\":-421,\"tc\":{\"i1\":321,\"str\":\"sample string..\",\"tc2\":{\"i1\":321,\"str\":\"sample string..1\"}},"
				+ "\"tc1\":{\"i1\":-111,\"str\":\"!!dsa string..\",\"tc2\":{\"i1\":1111,\"str\":\"-|ss dsa dsa d|-\"}},"
				+ "\"tc2\":{\"i1\":222,\"str\":\"sample DSAD Dsa..\",\"tc2\":{\"i1\":432.02,\"str\":\"<-->\"}}"
				+ "}}},\"list\":[-1.543,\"lqlqlql BOOOOOOM\",31232,{}, {\"n1\":\"KYURD\"}]"

				+ "}";
		System.out.println(json);

		System.out.println(Xson.fromJson(json, TestClass.class));
	}

	public static class TestClass {
		public int i1;
		public int i2;
		public String s1;
		public String s2;
		public String s3;

		public TestClass_1 tc;
		public TestClass_1 tc1;
		public TestClass_1 tc2;

		public ArrayList<Object> list;

		@Override
		public String toString() {
			return "{\n" + "\ti1=" + i1 + ",\n" + "\ttc=" + tc + "\n" + "\ttc1=" + tc1 + "\n" + "\ttc2=" + tc2
					+ "\nlist=" + list + "\n}";
		}
	}

	public static class TestClass_1 {
		public String str;

		public short i1;

		public TestClass_2 tc2;

		@Override
		public String toString() {
			return "{\n" + "\t\ti1=" + i1 + ",\n" + "\t\tstr=" + str + ",\n" + "\t\ttc2=" + tc2 + "\n\t}";
		}
	}

	public static class TestClass_2 {
		public String str;

		public TestClass _tc;

		public double i1;

		@Override
		public String toString() {
			return "{\n" + "\t\t\ti1=" + i1 + ",\n" + "\t\t\tstr=" + str + "\n\t\t\t_tc=" + _tc + "\t\t}";
		}
	}
}
