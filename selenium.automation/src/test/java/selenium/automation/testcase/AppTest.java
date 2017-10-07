package selenium.automation.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import selenium.automation.common.BaseTests;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTests {
	WebDriver driver ;
   
	@Test
	public void sampleTest1(){
	    driver= BaseTests.getDriver();
		driver.get("https://jungle-socks.herokuapp.com");
		
	}
}
