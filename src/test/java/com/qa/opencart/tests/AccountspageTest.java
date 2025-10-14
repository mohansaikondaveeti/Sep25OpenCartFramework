package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import org.testng.annotations.Test;


public class AccountspageTest extends BaseTest {
	
	//BT - > BC
	
	@BeforeClass
	
	public void accPageSetUp() {
		
	accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	
	}

	
	@Test
	public void accPageTest() {
	
		Assert.assertEquals(accpage.getAccPageTitle(), HOME_PAGE_TITLE);
		
		
	}
	

	@Test
	public void accPageURL() {
		
		Assert.assertTrue(accpage.getHomePageURL().contains(HOME_PAGE_FRACTION_URL));
		
		
	}
	
	
	@Test
	public void accPageHeadersTest() {
		List<String> accHeaderList = accpage.getAccpageHeaders();
		Assert.assertEquals(accHeaderList, AppConstants.expectedAccPageHeaderList);
		
		
	}

}
