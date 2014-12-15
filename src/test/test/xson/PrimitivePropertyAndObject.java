package test.xson;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class PrimitivePropertyAndObject {
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		TestClass t = new TestClass();
		Assert.assertEquals(Xson.toJson(t), "{\"t\" : {\"s1\" : \"" + t.t.s1 + "\"}, \"s1\" : \"" + t.s1 + "\"}");
	}

	@Jsonnable
	public class TestClass {
		@toJson
		public String s1 = "xstr1";

		@toJson
		public PrimitiveProperty.TestClass t = new PrimitiveProperty.TestClass();
	}
}
