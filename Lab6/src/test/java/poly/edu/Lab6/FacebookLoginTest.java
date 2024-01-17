package poly.edu.Lab6;



import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FacebookLoginTest {
	   
    private String Url = "https://www.facebook.com/";
    WebDriver driver = null;

    @BeforeTest
    public void setUp() {
        // Thiết lập trình duyệt Chrome
    	 WebDriverManager.edgedriver().setup();
         driver = new EdgeDriver();
    }

    @Test
    public void testFacebookLogin() throws InterruptedException {
        // Điều hướng đến trang đăng nhập của Facebook
        driver.manage().window().maximize();
        driver.get(Url);

        // Nhập tên đăng nhập và mật khẩu vào các trường tương ứng
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        email.sendKeys("demp");
        password.sendKeys("demo");
        
        // Gửi yêu cầu đăng nhập
        password.submit();

        // Kiểm tra xem trang đã đăng nhập thành công hay không
        WebElement createPostButton = driver.findElement(By.xpath("//div[@aria-label='Tạo bài viết']"));
        Assert.assertTrue(createPostButton.isDisplayed(), "Đăng nhập không thành công");
    }

    @AfterTest
    public void tearDown() {
        // Đóng trình duyệt
        driver.close();
    }
}