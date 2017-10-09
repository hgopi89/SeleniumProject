package selenium.automation.common;
/**
 * @Class description :  Base tests to be extended across all classes
 * @author  Gopinath Hariharan
 * @version 1.0
 * 
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;



public class BaseTests {

	protected static WebDriver driver=null;

	protected Logger logger = Logger.getLogger(this.getClass());
	public static String webDriverURL;
	public static String MAC_CHROMEDRIVER= "/chromedriver";
	public static String WIN_CHROMEDRIVER= "/chromedriver.exe";
	public static String MAC_FIREFOXDRIVER= "/geckodriver";
	public static String WIN_FIREFOXDRIVER= "/geckodriver.exe";
	public static HashMap<String, String> dataMap = new HashMap<String, String>();


	//Method to do Setup before test suite

	@Parameters({"browser", "platform"})
	@BeforeClass(alwaysRun=true)
	public void setupBeforeTestSuite(String browser, String platform) throws Exception{

		logger.info("Setup Before Test Suite- BaseTest");
		logger.info("Started Execution Test Suite-----");
		logger.info("Initialization code before test suite starts----");

		ConfigReader.loadConfigProperties("src/test/resources/Config.properties");

		String webDriverType = getWebDriverType();
		logger.info("WebDriver type: " + webDriverType);
		Platform testPlatform;
		switch(platform.toLowerCase()){
		case "windows 10" : testPlatform= Platform.WIN10; break;
		case "mac" 		  :testPlatform = Platform.MAC; break;
		default 	      : testPlatform = Platform.MAC; break;
		}

		if(webDriverType== "Remote"){
			setupRemoteDriver(browser, testPlatform);
			logger.info("Setup Remote Driver" + driver);
		}else{
			setupLocal(browser);
			logger.info("Setup Local Driver" + driver);
		}


	}



	//Method to setup Remote driver for remote execution
	private void setupRemoteDriver(String browser, Platform testPlatform) throws Exception {
		logger.info("setupRemoteDriver");
		DesiredCapabilities dc = null;
		LoggingPreferences logPrefs = null;
		webDriverURL = System.getProperty("webdriver.hostURL");
		logger.info("Remote   :: " + webDriverURL);
		logger.info("Platform :: " + testPlatform);
		logger.info("Browser  :: " + browser);
		logger.info("setting browser options & profile");

		//sample switch case for multiple browsers
		switch (browser.toLowerCase()){

		case "safari":
			SafariOptions safariOptions = new SafariOptions();
			dc = DesiredCapabilities.safari();
			dc.setBrowserName("safari");
			dc.setCapability(SafariOptions.CAPABILITY,  safariOptions);
			break;

		case "chrome":
			ChromeOptions chrome = new ChromeOptions();
			dc = DesiredCapabilities.chrome();
			dc.setBrowserName("chrome");
			dc.setCapability(SafariOptions.CAPABILITY,  chrome);
			break;

		case "firefox":
			dc = DesiredCapabilities.firefox();
			dc.setBrowserName("firefox");
			break;

		default:
			ChromeOptions chrome1 = new ChromeOptions();
			dc = DesiredCapabilities.chrome();
			dc.setBrowserName("chrome");
			dc.setCapability(SafariOptions.CAPABILITY,  chrome1);
			break;

		}	

		logger.info("Trying to create a Remote Web driver: " + webDriverURL);
		try{
			driver = new RemoteWebDriver(new URL(webDriverURL), dc);

		}catch(Exception e){
			logger.error ("setupremoteDriver Exception ", e);
		}


	}

	// Setup Local Driver
	private void setupLocal(String browser) throws Exception{
		DesiredCapabilities dc = null;

		logger.info("Browser is "+ browser);

		switch(browser.toLowerCase()){

		case "chrome":
			if(System.getProperty("os.name").toLowerCase().contains("mac")){
				logger.info("OS is mac");
				File cDriver = new File(BaseTests.class.getResource(MAC_CHROMEDRIVER).getFile());

				if(!cDriver.canExecute()){
					cDriver.setExecutable(true);
				}
				System.setProperty("webdriver.chrome.driver", BaseTests.class.getResource(MAC_CHROMEDRIVER).getFile());
			}else if(System.getProperty("os.name").toLowerCase().contains("win")){
				logger.info("OS is Windows");

				System.setProperty("webdriver.chrome.driver", BaseTests.class.getResource(WIN_CHROMEDRIVER).getFile());

			}
			ChromeOptions options = new ChromeOptions();
			options.addArguments("allow-running-insecure-content");

			DesiredCapabilities caps = DesiredCapabilities.chrome();
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability("chromeOptions", options);
			driver = new ChromeDriver(caps);
			break;

			

		case "firefox":
			if(System.getProperty("os.name").toLowerCase().contains("mac")){
				logger.info("OS is mac");
				File cDriver = new File(BaseTests.class.getResource(MAC_FIREFOXDRIVER).getFile());

				if(!cDriver.canExecute()){
					cDriver.setExecutable(true);
				}
				System.setProperty("webdriver.gecko.driver", BaseTests.class.getResource(MAC_FIREFOXDRIVER).getFile());
			}else if(System.getProperty("os.name").toLowerCase().contains("win")){
				logger.info("OS is Windows");

				System.setProperty("webdriver.gecko.driver", BaseTests.class.getResource(WIN_FIREFOXDRIVER).getFile());

			}
			driver = new FirefoxDriver();
			break;
		}
	}

	
	@BeforeMethod(alwaysRun=true)
	public void setupBeforeMethod(java.lang.reflect.Method method){
		driver.get(System.getProperty("urlToTest"));
			String testName = method.getName().toString();
			try {
				dataMap=ExcelUtil.getRowAsMap(System.getProperty("inputExcel"),"TestData", testName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e);
			}
			
			/*
			 * 			String groupName = t.groups()[0];
			 * Test t = method.getAnnotation(Test.class);
			 ExtentReports extent = ExtentObject.getExtent();
			 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			Date date = new Date();
			//dateFormat.format(date),
			this.ParamApp = ParamApp;
			test=extent.startTest(testName);

			ExtentObject.setTest(test); //very important
			if(ParamApp.equalsIgnoreCase("Mobile")){
				test.assignCategory("Mobile",groupName);
			}else if (ParamApp.equalsIgnoreCase("STB")){
				test.assignCategory("STB", groupName);
			}else if (ParamApp.equalsIgnoreCase("Website")){
				test.assignCategory("Website", groupName);
			}
			 */
			
			
	
	}
	
	@AfterMethod(alwaysRun=true)
	public void setupAfterTestCase( ITestResult testResult) throws Exception{
		logExceptionMessageOnFailure(testResult);
		logger.info("setupAfterTestCase-BaseTest");
		takeScreenShotOnFailure(testResult);
		//driver.close();

	}
	
	@AfterClass(alwaysRun=true)
	public void setupAfterTestSuite(){
		try{
			logger.info("Setup after Test- BaseTest");
			if(driver!=null){
				try{
					driver.close();
					logger.info("Driver closed properly");

				}catch(Exception e){
					logger.info("Exception "+ e);

				}
				try{
					driver.quit();
					logger.info("Driver quit properly");

				}catch(Exception e){
					logger.info("Exception "+ e);

				}
			}
		}catch(Exception e){
			logger.error("setupAfterTestSuiteException " + e);
		}
	}
	public void logExceptionMessageOnFailure(ITestResult testResult) throws IOException{
		if(testResult.getStatus()==ITestResult.FAILURE){
			logger.info(testResult.getThrowable());
			StackTraceElement st[] = testResult.getThrowable().getStackTrace();
			for(StackTraceElement item:st) {
				logger.info(item);
			}
		}
	}
	
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException{
		logger.info("Test Result is: "+testResult.getStatus());
		if(testResult.getStatus()==ITestResult.FAILURE){
			takeScreenshot(testResult);
		}
		else if(testResult.getStatus()==ITestResult.SKIP){
		}
	}
	public void takeScreenshot(ITestResult testResult) {
		try {
			logger.info("TakeScreenshot.. ");
			File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			long systemCurrentTime=System.currentTimeMillis();
			logger.info("ScreenShot success.. Now try copy the captured Screenshot to the folder.");
			File outPut= new File("./target/surefire-reports/"+systemCurrentTime+ testResult.getName()+".jpg"); 
			FileUtils.copyFile(srcFile, outPut);
		}
		catch(Exception exp) {
			logger.error("TakeScreenshot.. failed", exp );
		}
	}
	public static String getWebDriverType(){
		return System.getProperty("webdriver.type");
	}
	
	public static WebDriver getDriver(){
		return driver;
		
	}
}
