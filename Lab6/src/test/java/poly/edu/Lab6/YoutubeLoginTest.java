package poly.edu.Lab6;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/*
 * public class YoutubeLoginTest {
 * 
 * @Test public void testYoutubeLogin() throws InterruptedException { // Tạo một
 * đối tượng WebDriver để mở trình duyệt Chrome WebDriver driver = new
 * ChromeDriver();
 * 
 * // Mở trang web Youtube driver.get("https://www.youtube.com/signin");
 * 
 * // Nhập tên đăng nhập và mật khẩu vào trang web
 * driver.findElement(By.id("identifierId")).sendKeys("hoanghuunhom3@gmail.com")
 * ; driver.findElement(By.id("identifierNext")).click(); Thread.sleep(2000);
 * driver.findElement(By.name("password")).sendKeys("vvhh2003");
 * driver.findElement(By.id("passwordNext")).click(); Thread.sleep(2000);
 * 
 * // Kiểm tra xem đăng nhập đã thành công bằng cách kiểm tra tiêu đề của trang
 * web sau khi đăng nhập String title = driver.getTitle(); assert
 * title.equals("YouTube");
 * 
 * // Đóng trình duyệt web driver.quit(); } }
 */

public class YoutubeLoginTest {
    
    WebDriver driver = new ChromeDriver();
    
    @Test
    public void loginTest() {
        // Truy cập vào trang đăng nhập của YouTube
        driver.get("https://www.youtube.com/signin");
        
        // Điền thông tin tài khoản vào form đăng nhập
        driver.findElement(By.id("identifierId")).sendKeys("your_email_address");
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys("your_password");
        driver.findElement(By.id("passwordNext")).click();
        
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

