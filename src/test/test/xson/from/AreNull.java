package test.xson.from;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;

public class AreNull {

	@Test
	public void test() {
		Assert.assertEquals(Xson.fromJson("null", TestClass.class), null);
		Assert.assertEquals(Xson.fromJson(null, TestClass.class), null);
		
		Assert.assertEquals(Xson.fromJsonToJsonArray(null), null);
		Assert.assertEquals(Xson.fromJsonToJsonArray("null"), null);
		
		Assert.assertEquals(Xson.fromJsonToJsonObject("null"), null);
		Assert.assertEquals(Xson.fromJsonToJsonObject(null), null);
		
		Assert.assertEquals(Xson.fromJsonToList("null"), null);
		Assert.assertEquals(Xson.fromJsonToList(null), null);
		
		Assert.assertEquals(Xson.fromJsonToMap("null"), null);
		Assert.assertEquals(Xson.fromJsonToMap(null), null);
	}

	public static class TestClass {
		
	}
}
