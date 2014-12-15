package test.xson.collections.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class MapToJsonWithJsonnable {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		Map map = new HashMap() {
			{
				put("n1", "some string...");
				put("n2", new TestClass());
			}
		};
		Assert.assertEquals(Xson.toJson(map),
				"{\"n1\" : \"some string...\", \"n2\" : {\"n1\" : 1234, \"n2\" : \"some string...\"}}");
	}

	@Jsonnable()
	public static class TestClass {

		@toJson
		public int n1 = 1234;

		@toJson
		public String n2 = "some string...";

	}

}
