package test.xson;

import org.junit.Assert;
import org.junit.Test;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class TwoObjects {
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		pojo4 t = new pojo4();
		Assert.assertEquals(Xson.toJson(t), "{\"t2\" : {\"s1\" : \""+t.t2.s1+ "\"}, \"t1\" : {\"s1\" : \""+t.t1.s1+ "\"}}");
	}
	
	@Jsonnable
	public class pojo4 {
		@toJson
		public PrimitiveProperty.TestClass t2 = new PrimitiveProperty.TestClass();
		
		@toJson
		public PrimitiveProperty.TestClass t1 = new PrimitiveProperty.TestClass();
	}
}
