package com.qa.opencart.base;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductsInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//@Listeners(ChainTestListener.class)

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected 	Properties prop;
	
	protected LoginPage loginpage;
	protected AccountsPage accpage;
	protected SearchResultsPage searchResultsPage;
	protected ProductsInfoPage productsInfoPage;
	protected RegistrationPage registrationPage;
	
	private static final Logger log = LogManager.getLogger(BaseTest.class); 
	@Parameters({"browser", "browserversion", "testname"})
	@BeforeTest	
	public void setup(String browserName, String browserVersion, String testName) {
		df= new DriverFactory();
		prop = df.initProp();
		
		//Browser name is passed from .xml file
		if(browserName!=null && !browserName.isEmpty()) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);	
			prop.setProperty("testname", testName);	
			}
		
		
	driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
	}
	
	
	@AfterMethod //will be running after each test method
	public void attachScreenshotifFailed(ITestResult result) {
		if(!result.isSuccess()) {
			log.info("screenshot is taken");
			ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
		}
	}
	
//	@AfterMethod //will be running after each test method, and takes screenshot for all TC's
//	public void attachScreenshot(ITestResult result) {
//			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
//		}
	
	
	@AfterTest
	public void tearDown() {
	
		 if (driver != null) {
	            driver.quit();
	            log.info("-----Closing the Browser-----");
	        }
		
	}
}
