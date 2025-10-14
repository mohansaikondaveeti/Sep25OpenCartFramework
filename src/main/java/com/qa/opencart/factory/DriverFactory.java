package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;


import com.qa.opencart.exceptios.BrowserException;
import com.qa.opencart.exceptios.FrameworkException;



public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static String highlight;
	
	private static final Logger log = LogManager.getLogger(DriverFactory.class); 
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to init the driver on the basis of given Browser Name
	 * 
	 * @param browserName
	 */
	
	

	public WebDriver initDriver(Properties prop) {
		
		

		String browserName = prop.getProperty("browser");

		log.info("Browser Name: " + browserName);


		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {

		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			
			break;

		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
		
			break;

		default:
			log.error("please pass the valid driver name: " + browserName);
			throw new BrowserException("===INVALID BROWSER===");

		}

		
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();

	}
	
	/**
	 * getDriver: get the local thread copy of the driver
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
		
	}
	

	// mvn clean install -Denv="qa"
	public Properties initProp() {

		String envName = System.getProperty("env");

		FileInputStream ip = null;
		prop = new Properties();

		try {

			if (envName == null) {
				log.warn("Environment is null, hence running in QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");

			} else {
				log.info("Running the test in: " + envName);

				switch (envName) {

				case "qa":
					ip = new FileInputStream("../src/test/resources/config/qa.config.properties");
					break;

				case "dev":
					ip = new FileInputStream("../src/test/resources/config/dev.config.properties");
					break;

				case "stage":
					ip = new FileInputStream("../src/test/resources/config/stage.config.properties");
					break;

				case "uat":
					ip = new FileInputStream("../src/test/resources/config/uat.config.properties");
					break;

				case "prod":
					ip = new FileInputStream("../src/test/resources/config/prod.config.properties");
					break;

				default:
					log.error("-----invalid env name-----");
					throw new FrameworkException("====Invalid Environment Name: "+ envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}
/**
 * Take Screenshot
 */

//public static File getScreenshotFile() {
//	TakeScreenshot ts= (TakeScreenshot) driver;
//	File file = ts.getScreenshotAs(OutputType.FILE);
	
//	File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE); //very heavy, stores in temp dir
//	return srcFile;
//}
//	

public static byte[] getScreenshotByte() {
	return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}


//public static String getScreenshotBase64() {
//	return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64); //very light
//	}

}
