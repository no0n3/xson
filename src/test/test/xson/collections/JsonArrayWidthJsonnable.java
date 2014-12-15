package test.xson.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;
import com.vi.xson.object.JsonArray;

public class JsonArrayWidthJsonnable {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		List list = new ArrayList() {
			{
				add(123);
				add("some string...");
				add(new TestClass());
			}
		};
		JsonArray arr = new JsonArray(list);

		String expected = "[123, \"some string...\", {\"str\" : \"_123 ..\"}]";
		Assert.assertEquals(arr.toString(), expected);
	}

	@Jsonnable
	public static class TestClass {
		@toJson
		public String str = "_123 ..";
	}
}
