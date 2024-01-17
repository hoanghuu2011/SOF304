package bai3;

import org.testng.annotations.Test;

public class groupExamples {
	@Test(groups = "Regression")
	public void testCase1() {
		System.out.println("Test 1");
	}

	@Test(groups = "Regression")
	public void testCase2() {
		System.out.println("Test 2");
	}

	@Test(groups = "Regression")
	public void testCase3() {
		System.out.println("Test 3");
	}

	@Test(groups = "Regression")
	public void testCase4() {
		System.out.println("Test 4");
	}

}
