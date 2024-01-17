package poly.edu.Lab4;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class NewTest {

  @Test
	public void test1() {
		System.out.println("demo001");
	}
  @Test
	public void testtong() {
		assertEquals(tinhtong(5, 6), 11);
	}

	public int tinhtong(int a, int b) {
		int tong;
		tong = a + b;
		return tong;
	}

}
