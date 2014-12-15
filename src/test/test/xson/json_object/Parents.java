package test.xson.json_object;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class Parents {

	private static String f1Value = "xx";

	// @Test
	// public void test() throws Exception {
	public static void main(String[] a) throws Exception {
		test t = new test();
		System.out.println(Xson.toJson(t));
		// Assert.assertEquals(Xson.toJson(t), "{\"f1\" : \"" + t.getF1() +
		// "\"}");
	}

	@Jsonnable()
	public static class test {

//		@toJson
//		public String p1 = "d";

		@toJson(parent = "p1/p2")
		public String f1;

		 @toJson
		 public String p11 = "d";

		public test() {
			f1 = f1Value;
		}
	}
}
