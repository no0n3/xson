package test.xson.collections.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;

public class MapToJson {
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	@Test
	public void test() throws Exception {
		Map map = new HashMap() {
			{
				put("n1", 123);
				put("n2", "some string...");

			}
		};
		String expected = "{\"n1\" : " + map.get("n1") + ", \"n2\" : \"" + map.get("n2") + "\"}";
		Assert.assertEquals(Xson.mapToJsonObject(map).toString(), expected);
	}
}
