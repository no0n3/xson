package test.xson;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class PrimitiveProperty {
	@Test
	public void test() throws Exception {
		TestClass t = new TestClass();
		Assert.assertEquals(Xson.toJson(t), "{\"s1\" : \"" + t.s1 + "\"}");
	}

	@Jsonnable
	public static class TestClass {
		@toJson(getterName = "getS1")
		public String s1 = "xstr";

	}
}
