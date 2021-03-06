package selenium.automation.page;
/**
 * @Class description : Common base page methods
 * @author  Gopinath Hariharan
 * @version 1.0
 * 
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;

import selenium.automation.common.BaseTests;

public class BasePages extends BaseTests {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected final static long DEFAULT_PAGE_WAIT= Integer.parseInt(System.getProperty("defaultPageWaitTime")); 
	protected final static long DEFAULT_OBJECT_WAIT= Integer.parseInt(System.getProperty("defaultObjectWaitTime")); 
	public WebDriver driver;
	public WebDriverWait waitDriver;

	public BasePages(WebDriver driver){
		this.driver= driver;

		this.waitDriver = new WebDriverWait(driver,DEFAULT_PAGE_WAIT );
	}

	public void waitForElement(By element){

		waitDriver = new WebDriverWait(driver,DEFAULT_OBJECT_WAIT);
		waitDriver.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
		logger.info("Waited for element ="+ element + " and found");
	}

	public void waitForPageLoad(String pageName){

		waitDriver = new WebDriverWait(driver,DEFAULT_OBJECT_WAIT);
		long startTime=System.currentTimeMillis();
		waitDriver.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				logger.info("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String
						.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
		long endTime = System.currentTimeMillis();
		long timeTakenInMil=endTime-startTime;
		logger.info("Time taken to Load "+pageName+" in MiliSeconds "+timeTakenInMil);
	}

	public void clickElement(By ele){

		waitForElement(ele);
		driver.findElement(ele).click();
		logger.info("Element clicked "+ ele);

	}

	public void typeElement(By ele, String input){
		waitForElement(ele);
		driver.findElement(ele).sendKeys(input);
		logger.info("Element typed "+ ele+ " value is "+ input);

	}

	public void selectElement(By ele, String input){
		waitForElement(ele);
		Select elementSelect = new Select(driver.findElement(ele));
		elementSelect.selectByVisibleText(input);
		logger.info("Element selected "+ ele+ " value is "+ input);
	}

	public boolean assertText(By by, String expected){
		waitForElement(by);
		String actuals = driver.findElement(by).getText();
		logger.info("String searched "+ expected + " found "+ actuals);
		return expected.equals(actuals);
	}

	public boolean assertWebPage(String[] expected){
		String bodyText = driver.findElement(By.tagName("body")).getText();
		for(String s: expected){
			logger.info("Expected String "+ s);
			logger.info(bodyText.contains(s));
			Assert.assertTrue(bodyText.contains(s));
		}
		return true;

	}

	public void pressEnterButton(By by){
		waitForElement(by);
		WebElement element = driver.findElement(by);
		try {
			robotKeyEnter();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.sendKeys(Keys.RETURN);

	}

	public void robotKeyEnter() throws AWTException{
		if(System.getProperty("browser").toLowerCase().contains("firefox")){


			Robot robot = new Robot();


			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.setAutoDelay(200);

			robot = null;


		}
	}

}
