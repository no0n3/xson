package test.xson.getters_test;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class GetterPublicFieldWithGetter {

	private static String f1Value = "xx";

	@Test
	public void test() throws Exception {
		test t = new test();
		Assert.assertEquals(Xson.toJson(t), "{\"f1\" : \"" + t.f1 + "\"}");
	}

	@Jsonnable()
	public static class test {

		@toJson
		private String f1;

		public String getF1() {
			return f1 + " (getter)";
		}

		public String specifiedGetter() {
			return f1 + " (specified getter)";
		}

		public test() {
			f1 = f1Value;
		}
	}
}
