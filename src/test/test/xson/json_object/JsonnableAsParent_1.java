package test.xson.json_object;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class JsonnableAsParent_1 {

	@Test
	public void test() throws Exception {
		TestClass1 t = new TestClass1();
		String expected = "{\"parentObj\" : {\"str\" : \"" + t.parentObj.str + "\", \"i1\" : " + t.i1 + "}}";
		Assert.assertEquals(Xson.toJson(t), expected);
	}

	@Jsonnable()
	public static class TestClass {

		@toJson
		public String str = "String property...";

	}

	@Jsonnable()
	public static class TestClass1 {

		@toJson(parent = "parentObj")
		public int i1 = 1234;

		@toJson
		public TestClass parentObj = new TestClass();

	}
}
