package poly.edu.Lab6;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;



public class LMS {
	private String url="https://www.youtube.com/watch?v=qsCk4kW5KAc";
	WebDriver driver = null;
	@Test
	public void test() {
		driver.manage().window().maximize();
		driver.get(url);
		
		String title =driver.getTitle();
		String expected="[S9] Doraemon - Tập 451 - Cơm Cuộn May Mắn - Tuyết Nóng Quá! - Lồng Tiếng Viêt - YouTube";
		assertEquals(title, expected);
	}
	@BeforeTest
	public void beforeTest() {
		WebDriverManager.edgedriver().setup();
		
		driver = new EdgeDriver();
	}
	@AfterTest
	public void affterTest() {
		driver.close();
	}
}
