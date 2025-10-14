package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.testng.Assert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import org.testng.annotations.Test;




public class LoginPageTest extends BaseTest {
	
	
	
	@Test(description = "Checking Login page Title")
	public void loginPageTitleTest() {
		String actTitle = loginpage.getLoginPageTitle();
		ChainTestListener.log("Checking Login Page Title: "+actTitle);
		Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
	}
  
	
	
	@Test(description = "Checking Login page URL")
	public void loginPageURLTest() {
		String actURL = loginpage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
	}

	
	
	@Test(description="Checking Forgotten Password link...")
	public void forgotPwdLinkExistsLTest() {
		Assert.assertTrue(loginpage.isForgotPasswordLinkExists());

	}
	
	
	
	@Test(priority = Short.MAX_VALUE)
	public void loginTest() {
	accpage =	loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	Assert.assertTrue(accpage.getHomePageURL().contains(HOME_PAGE_FRACTION_URL));
		
	}
	
	@Test(enabled =false, description = "Work in progress forgotpwd")
	public void forgtpwd() {
	accpage =	loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	Assert.assertTrue(accpage.getHomePageURL().contains(HOME_PAGE_FRACTION_URL));
		
	}
	
}
