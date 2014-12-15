package test.xson.from.object;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.object.JsonObject;

public class FromJsonToJsonObject {

	@Test
	public void test() {
		JsonObject o = Xson.fromJsonToJsonObject("{\"p1\":-3}");

		String expected = "{\"p1\" : -3.0}";
		Assert.assertEquals(expected, o.toString());

	}

}
