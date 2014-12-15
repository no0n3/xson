package test.xson.tags;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class TagTest2 {
	@Test
	public void test() {
		TestClass tc = new TestClass();

		String expedtedWitoutTags = "{\"p3\" : \"property3's value..\"}";
		String expedtedWithTagT1 = "{\"p3\" : \"property3's value..\", \"p1\" : \"property1's value..'s form with tag 't1'\"}";
		String expedtedWithTagT2 = "{\"p3\" : \"property3's value..\", \"p2\" : \"property2's value..\", \"p1\" : \"property1's value..'s form with tag 't2'\"}";

		Assert.assertEquals(expedtedWitoutTags, Xson.toJson(tc));
		Assert.assertEquals(expedtedWithTagT1, Xson.toJson(tc, "t1"));
		Assert.assertEquals(expedtedWithTagT2, Xson.toJson(tc, "t2"));
	}

	@Jsonnable
	private static class TestClass {
		private String p1 = "property1's value..";

		@toJson(tags = { "t2" })
		private String p2 = "property2's value..";

		// will always be transformed to json.
		@toJson
		private String p3 = "property3's value..";

		@toJson(name = "p1", tags = { "t1" })
		public String p1_T1() {
			return p1 + "'s form with tag 't1'";
		}

		@toJson(name = "p1", tags = { "t2" })
		public String p1_T2() {
			return p1 + "'s form with tag 't2'";
		}
	}
}
