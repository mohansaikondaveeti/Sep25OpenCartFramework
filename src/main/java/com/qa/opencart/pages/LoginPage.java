package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.MEDIUM_DEFAULT_TIMEOUT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	// 1. By Locators
	private final By email = By.id("input-email");
	private final By password = By.xpath("//input[@type='password']");
	private final By loginBtn = By.xpath("//input[@type='submit']");
	private final By forgotPassword = By.linkText("Forgotten Password");
	private final By register = By.linkText("Register");
	private static final Logger log = LogManager.getLogger(LoginPage.class); 
	// 2. public page Constructors

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	// 3. public page Actions/methods
	public String getLoginPageTitle() {
		String title=eleUtil.waitForTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
			return title;
	}

	
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
			return url;
	}

	
	public Boolean isForgotPasswordLinkExists() {
		return eleUtil.isElementDisplayed(forgotPassword);

	}
	
	
	public AccountsPage doLogin(String username, String pwd) {
		log.info("User credentials" + username+ ":" + pwd);
		eleUtil.doSendKeys(eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT), username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		return new AccountsPage(driver);
		
	}
	
	
	public RegistrationPage navigateToRegistrationPage() {
		eleUtil.clickWhenReady(register, DEFAULT_TIMEOUT);
		
		return new RegistrationPage(driver);
	}
}