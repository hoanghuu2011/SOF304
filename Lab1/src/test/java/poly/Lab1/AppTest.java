package poly.Lab1;

import static org.junit.Assert.assertTrue;

import javax.annotation.processing.Generated;

import org.junit.Test;

import junit.framework.TestCase;




@Generated(value = "org.junit-tools-1.1.0")
public class AppTest extends TestCase{

	private App createTestSubject() {
		return new App();
	}
	public void testApp() {
		assertTrue(true);
	}

	@Test
	public void testIsEventNumber2() {
		App demol = new App();
		boolean result = demol.isEventNumber(2);
		assertTrue(result);
	}

	@Test
	public void testIsEventNumber4() {
		App demol = new App();
		boolean result = demol.isEventNumber(4);
		assertTrue(result);
	}
}