package poly.Lab3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArithmeticTest {
	public String message = "Fpoly exception";
	JUnitMessage junitMessage = new JUnitMessage(message);

	
	@Test(expected = ArithmeticException.class)
	public void testJUnitMessage() {
		System.out.println("Fpoly Junit Message exception is printing");
		junitMessage.printMessage();
	}

	@Test
	public void testJUnitHiMessage() {
		message = "Hi!" + message;
		System.out.println("\nFpoly Junit Message is printing");
		assertEquals(message, junitMessage.printHiMessage());
	}

}
