package test.xson.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.object.JsonArray;

public class JsonArrayTest {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		List list = new ArrayList() {
			{
				add(123);
				add("some string...");

			}
		};
		JsonArray arr = new JsonArray(list);
		list.add(33);
		
		String expected = "[123, \"some string...\", 33]";
		Assert.assertEquals(arr.toString(), expected);
	}
}
