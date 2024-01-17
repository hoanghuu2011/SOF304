package poly.edu.Lab6;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;


public class NewTest {
	
	 private String Url = "https://www.youtube.com/signin";
		WebDriver driver = null;

		@BeforeTest
	    public void setUp() {
	        // Thiết lập trình duyệt Chrome
	    	WebDriverManager.edgedriver().setup();
	    	driver = new EdgeDriver();	       
	        
	    }
	   
	    
	    @Test
	    public void loginTest() throws InterruptedException {
	        // Truy cập vào trang đăng nhập của YouTube
	        driver.get(Url);
	        
	        // Điền thông tin tài khoản vào form đăng nhập
	        driver.findElement(By.id("identifierId")).sendKeys("");
	        driver.findElement(By.id("identifierNext")).click();
	        Thread.sleep(5000);
	        WebElement password = driver.findElement(By.id("pass"));
	        password.sendKeys("");
	        driver.findElement(By.id("passwordNext")).click();
	        Thread.sleep(2000);
	        
	        // Kiểm tra xem bạn đã đăng nhập thành công hay chưa
	        String expectedUrl = "https://www.youtube.com/";
	        String actualUrl = driver.getCurrentUrl();
	        Assert.assertEquals(actualUrl, expectedUrl);
	    }
	    
	    @AfterTest
	    public void closeBrowser() {
	        driver.quit();
	    }
	}

 

