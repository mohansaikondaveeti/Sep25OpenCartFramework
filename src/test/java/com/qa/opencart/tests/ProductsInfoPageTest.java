package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.util.CSVUtil;
import com.qa.opencart.util.ExcelUtil;

import org.testng.annotations.Test;

public class ProductsInfoPageTest extends BaseTest{
	
	@BeforeClass	
	public void searchSetup() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String productName) {
	searchResultsPage = accpage.doSearch(searchKey);
	productsInfoPage = searchResultsPage.selectProduct(productName);
	String actheader = productsInfoPage.getProductHeader();
	Assert.assertEquals(actheader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung Galaxy Tab 10.1", 7}
		};
	}
	
	@DataProvider()
	public Object[][] getUserRegData() {
		Object regData [][] = ExcelUtil.getTestData(PRODUCT_SHEET_NAME);
		return regData;

	}
	
	@DataProvider()
	public Object[][] getProductCSVData() {
		return  CSVUtil.csvData("product");
	}

	
	@Test(dataProvider = "getProductCSVData")
	public void productImageCountTest(String searchKey, String productName, String count) {
	searchResultsPage = accpage.doSearch(searchKey);
	productsInfoPage = searchResultsPage.selectProduct(productName);
	int actImgCount = productsInfoPage.getImageCount(); 	
	Assert.assertEquals(String.valueOf(actImgCount), count);
	}
	
	
	
	@Test
	public void getPriceMetadata() {
		searchResultsPage =	accpage.doSearch("macbook");
		productsInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		
		
		Map<String, String> actualProductDetailsMap = productsInfoPage.getProductDetailsMap();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(actualProductDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualProductDetailsMap.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(actualProductDetailsMap.get("extxPrice"), "$2,000.00");
		
		softAssert.assertAll();
			
	}

}
