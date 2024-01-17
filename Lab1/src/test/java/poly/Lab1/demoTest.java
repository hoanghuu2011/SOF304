package poly.Lab1;

import static org.junit.Assert.assertEquals;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import poly.Lab1.demo;



@Generated(value = "org.junit-tools-1.1.0")
public class demoTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	private demo createTestSubject() {
		return new demo();
	}

	
	@Test
	public void testAdd() throws Exception {
		demo testSubject;
		int a = 4;
		int b = 5;
		int result;
        int expected =9;
		// default test 1
		testSubject = createTestSubject();
		result = testSubject.add(a, b);
		assertEquals(expected,result);
	}

	
	
	@Test
	public void testSub() throws Exception {
		demo testSubject;
		int a = 4;
		int b = 5;
		int result;
		int expected =-1;
		// default test
		testSubject = createTestSubject();
		result = testSubject.sub(a, b);
		assertEquals(expected,result);
	}

	
	@Test
	public void testMul() throws Exception {
		demo testSubject;
		int a = 5;
		int b = 4;
		int result;
		int expected =20;
		// default test
		testSubject = createTestSubject();
		result = testSubject.mul(a, b);
		assertEquals(expected,result);
	}

	
	@Test
	public void testDiv() throws Exception {
		demo testSubject;
		int a = 10;
		int b = 2;
		int result;
		int expected =5;
		// default test
		testSubject = createTestSubject();
		result = testSubject.div(a, b);
		assertEquals(expected,result);
	}
}