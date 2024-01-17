package fpoly;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestClass {
	@Test
	public void a1() {

	}

	@Parameters("param")
	@Test
	public void a2(String name) {

	}

	@Test(enabled = false)
	public void a3() {

	}
}
