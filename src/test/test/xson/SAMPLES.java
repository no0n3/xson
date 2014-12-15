package test.xson;

import com.vi.xson.Xson;
import com.vi.xson.annotation.Jsonnable;
import com.vi.xson.annotation.toJson;

public class SAMPLES {

	public static void main(String[] args) {
		System.out.println(Xson.toJson(new TestClass()));
	}

	@Jsonnable
	private static class TestClass {
		@toJson
		private String p1 = "property1's value..";

		@toJson
		private String p2 = "property2's value..";

		@toJson
		private String p3 = "property2's value..";

	}
}
