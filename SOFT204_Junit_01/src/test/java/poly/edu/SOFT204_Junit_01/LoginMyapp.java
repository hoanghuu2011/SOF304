package poly.edu.SOFT204_Junit_01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginMyapp{
 
    private String Url = "https://nentang.vn/app/login";
    WebDriver driver = null;
    @BeforeTest
    public void setUp() {
        // Thiết lập trình duyệt Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
    }   

    @Test
    public void testLogin() throws InterruptedException {
    	driver.manage().window().maximize();
        driver.get(Url);
        // Chờ trang web tải đầy đủ
        //Thread.sleep(5000);

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        email.sendKeys("minhtu@gmail.com");
        password.sendKeys("abc123!!!");
        
 
        password.submit();

    

        String expectedTitle = "Đăng nhập | Nền Tảng";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Title of homepage is not as expected.");
    }
  
      
    @AfterTest
    public void tearDown() {
        // Đóng trình duyệt
        driver.close();
    }
}
