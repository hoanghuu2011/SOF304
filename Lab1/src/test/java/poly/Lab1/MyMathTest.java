package poly.Lab1;

import static org.junit.Assert.assertEquals;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import poly.Lab1.MyMath;

@Generated(value = "org.junit-tools-1.1.0")
public class MyMathTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	private MyMath createTestSubject() {
		return new MyMath();
	}

	
	@Test
	public void testSubtraction() throws Exception {
		MyMath testSubject;
		int a = 9;
		int b = 9;
		int result;
        int expected=0;
		// default test
		testSubject = createTestSubject();
		result = testSubject.subtraction(a, b);
		assertEquals(expected,result);
	}

	
	@Test
	public void testAddition() throws Exception {
		MyMath testSubject;
		int a = 9;
		int b = 9;
		int result;
        int expected=18;
		// default test
		testSubject = createTestSubject();
		result = testSubject.addition(a, b);
		assertEquals(expected,result);
	}

	
	@Test
	public void testDivision() throws Exception {
		MyMath testSubject;
		double a = 4.0;
		double b = 5.0;
		double result;
         
		// test 1
		testSubject = createTestSubject();
		b = 5;
		result = testSubject.division(a, b);
		Assert.assertEquals(0.0, result, 0);

		// test 2
		testSubject = createTestSubject();
		b = 4;
		result = testSubject.division(a, b);
		Assert.assertEquals(0.0, result, 0);
	}
}