package poly.edu.Lab6;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLoginPage {
    private WebDriver driver;
    private String url = "https://ap.poly.edu.vn/login";
    
    @BeforeMethod
    public void setup() {
    	WebDriverManager.edgedriver().setup();
    	driver = new EdgeDriver();
       
        driver.get(url);
    }
    
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testLogin() {
        // Enter email
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("");
        
        // Enter password
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("");
        
        // Click login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        
        // Assert that login was successful
        WebElement logoutButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng xuất')]"));
        Assert.assertTrue(logoutButton.isDisplayed());
    }
}
