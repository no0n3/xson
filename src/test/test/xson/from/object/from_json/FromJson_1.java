package test.xson.from.object.from_json;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;

public class FromJson_1 {

	@Test
	public void test() {
		TestClass o = Xson.fromJson("{\"i1\":-421,\"tc\":{\"i1\":321,\"str\":\"sample string..\"}}", TestClass.class);

		String expected = "TestClass [i1=-421, tc=TestClass_1 [i1=321, str=sample string..]]";
		Assert.assertEquals(expected, o.toString());

	}

	public static class TestClass {
		public int i1;

		public TestClass_1 tc;

		@Override
		public String toString() {
			return "TestClass [i1=" + i1 + ", tc=" + tc + "]";
		}
	}

	public static class TestClass_1 {
		public String str;

		public short i1;

		@Override
		public String toString() {
			return "TestClass_1 [i1=" + i1 + ", str=" + str + "]";
		}
	}
}
