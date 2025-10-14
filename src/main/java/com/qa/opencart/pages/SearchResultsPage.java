package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private final By productResults = By.cssSelector("div.product-thumb");
	private static final Logger log = LogManager.getLogger(SearchResultsPage.class);

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	
	public int getProductResultCount() {
		int searchCount = eleUtil.waitForAllElementsVisible(productResults, DEFAULT_TIMEOUT).size();
		log.info("Total number of Search products: "+ searchCount);
		return searchCount;
	}
	
	
	public ProductsInfoPage selectProduct(String ProductName) {
		log.info("ProductName: "+ ProductName);
		eleUtil.doClick(By.linkText(ProductName));
		return new ProductsInfoPage(driver);
		
	}
	
	
}
