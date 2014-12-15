package test.xson.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;
import com.vi.xson.object.JsonArray;

public class JsonArrayWidthJsonArray {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		List list = new ArrayList() {
			{
				add(123);
				add("some string...");
				add(new JsonArray(new ArrayList(){
					{
						add(1);
						add(new TestClass());
						add("_3!");
					}
				}));
			}
		};
		JsonArray arr = new JsonArray(list);
		String expected = "[123, \"some string...\", [1, {\"str\" : \"_123 ..\"}, \"_3!\"]]";
		Assert.assertEquals(arr.toString(), expected);
	}

	@Jsonnable
	public static class TestClass {
		@toJson
		public String str = "_123 ..";
	}
}
