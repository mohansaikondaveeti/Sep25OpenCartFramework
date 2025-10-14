package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.qa.opencart.base.BaseTest;

import org.testng.annotations.Test;

public class SearchTest extends BaseTest{
	
	@BeforeClass	
	public void searchSetup() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	

	@Test
	public void searchTest() {
		searchResultsPage = accpage.doSearch("macbook");
		int actResultCount = searchResultsPage.getProductResultCount();
		Assert.assertEquals(actResultCount, 3);
	}
	

}
