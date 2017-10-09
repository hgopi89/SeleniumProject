package selenium.automation.page;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CheckOutPage extends BasePages {

	public CheckOutPage(WebDriver driver) {
		super(driver);
	}

	By table = By.xpath("//table[2]");
	By subTotal = By.id("subtotal");
	By taxes = By.id("taxes");
	By total = By.id("total");
	By title = By.xpath("//h1");

	
	
	public  boolean verifySubTotal(){
		verifyCheckOutPage();
		HashMap<String, List<Integer>> price = new HashMap<>();
		// Grab the table 
		WebElement tableElement = driver.findElement((By)table); 

		// Now get all the TR elements from the table 
		List<WebElement> allRows = tableElement.findElements(By.tagName("tr")); 

		//reducing 3 rows for all prices
		for(int i=1; i<allRows.size()-3; i++){
			List<WebElement> cells = allRows.get(i).findElements(By.tagName("td")); 
			int j=1;

			while(j<cells.size()){
				String animalName =cells.get(0).getText().toString();
				if(price.containsKey(animalName)){
					int priceOfAnimal = Integer.parseInt(cells.get(j++).getText().toString());
					price.get(animalName).add(priceOfAnimal);
				}else{
					price.put(animalName, new ArrayList<Integer>());
				}

			}
		}
		int sum=0;
		for(Map.Entry<String, List<Integer>> e: price.entrySet()){
			sum+=e.getValue().get(0)* e.getValue().get(1);
			
		}
		String calculatedMoney = "$"+sum+".00";
		String actualMoney = driver.findElement(subTotal).getText().toString();
		logger.info("Calculated money " + calculatedMoney);
		logger.info("ActualMoney "+ actualMoney);
		if(actualMoney.equals(calculatedMoney)){
			logger.info("Actual SubTotal and Calculated subTotal match");
		}else {
			logger.error("Actual SubTotal and Calculated subTotal do not match");

			Assert.assertTrue(false, "Actual SubTotal and Calculated subTotal do not match");
		}
		
		String taxPrice = driver.findElement(taxes).getText().toString().replaceAll("[^\\d.]+", "");
		String totalPrice = driver.findElement(total).getText().toString().replaceAll("[^\\d.]+", "");
		float taxPriceFloat = Float.parseFloat(taxPrice);
		float totalPriceFloat =Float.parseFloat(totalPrice);
		float calculatedMoneyFloat  = Float.parseFloat(calculatedMoney.replaceAll("[^\\d.]+", ""));
		
		float actualTotal = calculatedMoneyFloat+taxPriceFloat;
		
		logger.info("Calculated Total Money "+calculatedMoneyFloat );
		logger.info("Actual Total Money "+totalPriceFloat );
		logger.info("Actual Tax money "+ taxPriceFloat);

		if(actualTotal==totalPriceFloat){
			logger.info("Total Money match with the calculated money");
		}else{
			Assert.assertTrue(false, "Total Money match with the calculated money mismatch");
		}
		
		return actualMoney.equals(calculatedMoney) && actualTotal==totalPriceFloat;
		
	}
	
	public void verifyCheckOutPage(){
		Assert.assertTrue(assertText(title, "Please Confirm Your Order"));

	}

}
