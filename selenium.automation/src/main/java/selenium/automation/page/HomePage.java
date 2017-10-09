package selenium.automation.page;
/**
 * @Class description : HomePage methods
 * @author  Gopinath Hariharan
 * @version 1.0
 * 
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class HomePage extends BasePages {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	By pageTitle = By.xpath("//h1");
	By pageDescription = By.xpath("//p");
	By table = By.xpath("//table");
	By quantityText_zebra = By.id("line_item_quantity_zebra");
	By quantityText_lion = By.id("line_item_quantity_lion");
	By quantityText_elephant = By.id("line_item_quantity_elephant");
	By quantityText_giraffe = By.id("line_item_quantity_giraffe");
	By maxQuantityText_zebra = By.xpath("//tr[2]/td[3]");

	By stateDropDown = By.name("state");
	By checkOutButton = By.name("commit");

	public void verifyHomePage(){
		Assert.assertTrue(assertText(pageTitle, "Welcome To Jungle Socks!"));
		Assert.assertTrue(assertText(pageDescription, "Please enter the quantiy of each kind of sock and then click the checkout button"));

	}
	public void enterValuesAndCheckOut(String q1,String q2, String q3, String q4, String state){
		verifyHomePage();
		typeElement(quantityText_zebra, q1);
		typeElement(quantityText_lion, q2);
		typeElement(quantityText_elephant, q3);
		typeElement(quantityText_giraffe, q4);
		selectElement(stateDropDown, state);

		clickElement(checkOutButton);
		waitForPageLoad("HomePage> CheckoutPage");

	}
	
	public int getMaxQuantityCount(){
		return Integer.parseInt(driver.findElement(maxQuantityText_zebra).getText().toString());
		
	}

	public void enterValuesAndPressEnter(String q1,String q2, String q3, String q4, String state){
		verifyHomePage();
		typeElement(quantityText_zebra, q1);
		typeElement(quantityText_lion, q2);
		typeElement(quantityText_elephant, q3);
		typeElement(quantityText_giraffe, q4);
		selectElement(stateDropDown, state);
		pressEnterButton(checkOutButton);
		waitForPageLoad("HomePage> CheckoutPage");

	}
	
	
	



}
