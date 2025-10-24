package com.qa.opencart.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;



public class OptionsManager {
	private Properties prop;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
			
		ChromeOptions co = new ChromeOptions();
		
//		co.addArguments("--disable-save-password-bubble");
//		co.addArguments("--disable-infobars");
//		Map<String, Object> prefs = new HashMap<>();
//		prefs.put("credentials_enable_service", false);
//		prefs.put("profile.password_manager_enabled", false);
//		co.setExperimentalOption("prefs", prefs);
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("----Running in headless Mode----");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("----Running in incognito Mode----");
			co.addArguments("--incognito");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		FirefoxOptions fo = new FirefoxOptions();
		
//		fo.addPreference("signon.rememberSignons", false);
//		fo.addPreference("signon.autofillForms", false);
//		fo.addPreference("signon.autologin.proxy", false);
//		fo.addPreference("network.automatic-ntlm-auth.allow-non-fqdn", false);
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("----Running in headless Mode----");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("----Running in incognito Mode----");
			fo.addArguments("--private");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		EdgeOptions eo = new EdgeOptions();
		
//		  Disable password manager and “save password” prompts
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//        eo.setExperimentalOption("prefs", prefs);
//
//         Optional: Disable infobars, notifications, etc.
//        eo.addArguments("--disable-save-password-bubble");
//        eo.addArguments("--disable-infobars");
//        eo.addArguments("--disable-notifications");

		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("----Running in headless Mode----");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("----Running in incognito Mode----");
			eo.addArguments("--inprivate");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
		}
		return eo;
	}


}
