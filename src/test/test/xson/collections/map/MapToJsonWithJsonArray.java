package test.xson.collections.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.object.JsonArray;

public class MapToJsonWithJsonArray {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		final List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add("_3_");

		Map map = new HashMap() {
			{
				put("n1", "some string...");
				put("n2", new JsonArray(list));
			}
		};
		list.add("123__");

		Assert.assertEquals(Xson.mapToJsonObject(map).toString(),
				"{\"n1\" : \"some string...\", \"n2\" : [1, 2, \"_3_\", \"123__\"]}");
	}
}
