package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;


public class AccountsPage {

	private final By headers = By.cssSelector("div#content>h2");
	private final By search = By.name("search");
	private final By searchIcon = By.xpath("//button[@class='btn btn-default btn-lg']");
	
	private static final Logger log = LogManager.getLogger(AccountsPage.class); 

	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	public String getAccPageTitle() {

		String title = eleUtil.waitForTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
		return title;
	}

	
	public String getHomePageURL() {
		String url = eleUtil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		log.info("Home Page URL " + url);
		return url;
	}

	
	public List<String> getAccpageHeaders() {
		List<WebElement> headerList = eleUtil.getElements(headers);
		List<String> headerListval = new ArrayList<String>();

		for (WebElement e : headerList) {
			String text = e.getText();
			headerListval.add(text);
		}
		log.info("Acc page Headers " + headerListval);
		return headerListval;

	}
	
	
	public SearchResultsPage doSearch(String searchKey) {
		//System.out.println("SearchKey: "+ searchKey);
		log.info("SearchKey: "+ searchKey);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
		
	}
	



}
