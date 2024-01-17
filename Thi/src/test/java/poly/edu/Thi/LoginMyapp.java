package poly.edu.Thi;

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
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        
    }   

    @Test
    public void testLogin() throws InterruptedException {
    	driver.manage().window().maximize();
        driver.get(Url);
        // Chờ trang web tải đầy đủ
        //Thread.sleep(5000);

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        email.sendKeys("demo123@gmailcom");
        password.sendKeys("vvhh2003");
        password.submit();

    

        String expectedTitle = "Trang chủ | Nền Tảng";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Title of homepage is not as expected.");
    }
  
    @Test
    public void testInvalidUsername() {
        // Load trang đăng nhập
        driver.get(Url);

        WebElement username = driver.findElement(By.id("email"));
        username.sendKeys("invalidusername");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("vvhh2003");

        // Click nút đăng nhập
        password.submit();

        // Chờ đến khi xuất hiện thông báo lỗi
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));

        // Kiểm tra thông báo lỗi có hiển thị đúng không
        Assert.assertEquals(errorMessage.getText(), "Thông tin đăng nhập không chính xác. Vui lòng kiểm tra lại tên đăng nhập hoặc mật khẩu.");
    }

      
    @AfterTest
    public void tearDown() {
        // Đóng trình duyệt
        driver.close();
    }
}
