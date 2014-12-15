package test.xson.from.object.from_json;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;

public class FromJson {

	@Test
	public void test() {
		TestClass o = Xson.fromJson("{\"i1\":-99,\"i2\":23}", TestClass.class);

		String expected = "TestClass [i1=-99, i2=23]";
		Assert.assertEquals(expected, o.toString());

	}

	public static class TestClass {
		public int i1;

		public int i2;

		@Override
		public String toString() {
			return "TestClass [i1="+i1+", i2="+i2+"]";
		}
	}
}
