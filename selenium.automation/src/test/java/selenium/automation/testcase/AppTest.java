package selenium.automation.testcase;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bsh.org.objectweb.asm.Constants;
import selenium.automation.common.AutomationConstants;
import selenium.automation.common.BaseTests;
import selenium.automation.page.CheckOutPage;
import selenium.automation.page.HomePage;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTests {
	
	@Test
	public void TC1_verifyCheckOutQuantity_allValidInputs(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));
		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();
	}
	
	@Test
	public void TC2_verifyCheckOutQuantity_oneValidInput(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC3_verifyCheckOutQuantity_zeroQuantity(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC4_verifyCheckOutQuantity_negativeInput(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));
		Assert.assertTrue(false);
	}
		
	@Test
	public void TC5_verifyCheckOutQuantity_emptyStateInput(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	
	@Test
	public void TC6_verifyCheckOutQuantity_emptyQuantityInput(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		Assert.assertTrue(false);		

	}
	
	@Test
	public void TC7_verifyCheckOutQuantity_allEmptyInput(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndCheckOut(dataMap.get("Quantity1"), dataMap.get("Quantity2"), dataMap.get("Quantity3"), dataMap.get("Quantity4"), dataMap.get("State"));		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		

	}
	@Test
	public void TC8_enterExcessQuantity(){
		HomePage page = new HomePage(driver);
		int maxCount =page.getMaxQuantityCount();
		String str_MaxCount = String.valueOf((maxCount+1));
		page.enterValuesAndCheckOut(str_MaxCount,"","","", AutomationConstants.STATE);		
		Assert.assertTrue(false);		

	}
	
	@Test
	public void TC9_checkOutUsingEnterKeyAction(){
		HomePage page = new HomePage(driver);
		page.enterValuesAndPressEnter(AutomationConstants.QUANTITY,"","","" , AutomationConstants.STATE);		
		CheckOutPage chPage =new CheckOutPage(driver);
		chPage.verifySubTotal();		
		
	}
	
	
	
	
}
