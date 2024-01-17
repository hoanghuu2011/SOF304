package poly.Lab2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SuiteTest1 {
	public String mess = "Fpoly";
	JUnitMessage junitmess = new JUnitMessage(mess);

	@Test(expected = ArithmeticException.class)
	public void testJUnitMessage() {
		System.out.println("Junit Message is printing");
		junitmess.printMessage();
	}

	@Test
	public void testJUnitHiMessage() {
		mess = "Hi!" + mess;
		System.out.println("Junit Hi Message is printing");
		assertEquals(mess,junitmess.printHiMessage());
		System.out.println("Suite Test 1 is successful: " + mess);
	}

}
