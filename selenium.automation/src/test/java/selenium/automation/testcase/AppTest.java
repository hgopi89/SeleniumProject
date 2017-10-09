package selenium.automation.testcase;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenium.automation.common.BaseTests;
import selenium.automation.page.CheckOutPage;
import selenium.automation.page.HomePage;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTests {
	
	@Test
	public void TC_verifyCheckOutQuantity_1(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));
		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();
	}
	
	@Test
	public void TC_verifyCheckOutQuantity_2(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC_verifyCheckOutQuantity_3(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC_verifyCheckOutQuantity_4(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));
		Assert.assertTrue(false);
	}
		
	@Test
	public void TC_verifyCheckOutQuantity_5(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	
	@Test
	public void TC_verifyCheckOutQuantity_6(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		Assert.assertTrue(false);		

	}
	
	@Test
	public void TC_verifyCheckOutQuantity_7(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC_enterExcessQuantity(){
		HomePage page = new HomePage(driver);
		int maxCount =page.getMaxQuantityCount();
		String str_MaxCount = String.valueOf((maxCount+1));
		page.enterValuesAndCheckOut(str_MaxCount,"","","", "Alaska");		
		Assert.assertTrue(false);		

	}
	
	
}
