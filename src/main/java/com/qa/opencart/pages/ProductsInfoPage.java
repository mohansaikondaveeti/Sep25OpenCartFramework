package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductsInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private final By productHeader = By.xpath("//h1");
	private final By productImages = By.cssSelector("ul.thumbnails li");
	private final By productMetadata = By.xpath("(//div[@class='col-sm-4']/ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@class='col-sm-4']/ul[@class='list-unstyled'])[2]/li");
	private static final Logger log = LogManager.getLogger(ProductsInfoPage.class); 
	
	private Map<String, String> productMap;

	public ProductsInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeader() {
		String header = eleUtil.waitForElementPresence(productHeader, MEDIUM_DEFAULT_TIMEOUT).getText();
		return header;
	}

	public int getImageCount() {
		int imgCount = eleUtil.waitForAllElementsVisible(productImages, MEDIUM_DEFAULT_TIMEOUT).size();
		log.info("Total number of images: " + imgCount);
		return imgCount;
	}

	public Map<String, String> getProductDetailsMap() {
		// productMap = new HashMap<String, String>();
		productMap = new LinkedHashMap<String, String>();
		productMap.put("ProductHeader", getProductHeader());
		productMap.put("ProductImages", String.valueOf(getImageCount()));
		getProductMetadata();
		getPriceMetadata();
		System.out.println("Full Product Details: " + productMap);
		return productMap;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock

	private void getProductMetadata() {

		List<WebElement> MetaList = eleUtil.waitForAllElementsVisible(productMetadata, MEDIUM_DEFAULT_TIMEOUT);
		for (WebElement e : MetaList) {
			String metaData = e.getText();
			String meta[] = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);

		}

	}

//	$2,000.00
//	Ex Tax: $2,000.00

	private void getPriceMetadata() {

		List<WebElement> priceList = eleUtil.waitForAllElementsVisible(productPriceData, MEDIUM_DEFAULT_TIMEOUT);
		String prdPrice = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", prdPrice);
		productMap.put("extxPrice", exTaxPrice);

	}
}
