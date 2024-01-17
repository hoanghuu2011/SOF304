package poly.edu.Lab6;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


public class Testcase {
  @Test
  @BeforeTest
  public void beforeTest() {
      System.out.println("Before Test");
  }

  @Test
  public void test1() {
      System.out.println("Test1");
  }

  @Test
  public void test2() {
      System.out.println("Test2");
  }

  @BeforeMethod
  public void beforeMethod() {
      System.out.println("Before Method");
  }

 @org.testng.annotations.Parameters({ "param" })
  @BeforeMethod
  public void beforeMethod(String p) {
      System.out.println("Before Method With Param : " + p);
  }

  @AfterMethod
  public void afterMethod() {
      System.out.println("After Method");
  }

  @BeforeClass
  public void beforeClass() {
      System.out.println("Before Class");
  }

  @AfterClass
  public void afterClass() {
      System.out.println("After Class");
  }

  @AfterTest
  public void afterTest() {
      System.out.println("After Test");
  }

}
