package poly.edu.Lab5;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class fpoly {
@Test
public void test2() {
	WebDriverManager.chromedriver().setup();
	WebDriver driver = new ChromeDriver();
	
	
	String url="https://lms.poly.edu.vn/";
	String title_website="Hệ thông quản lý hoc tập fpoly";
	
	String title_expected="";
	
	title_expected=driver.getTitle();
	
//	String url ="https://www.google.com/";
	driver.manage().window().maximize();
	
	driver.get(url);
	
	if(title_expected.contentEquals(title_expected)) {
		System.out.println("test pass");
	}else {
		System.out.println("test fail");
	}


}
}
