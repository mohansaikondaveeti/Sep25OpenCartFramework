package com.qa.opencart.util;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;





public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	private static final Logger log = LogManager.getLogger(AccountsPage.class); 
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	private void nullCheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("====Value can not be null====");
		}
	}

	public void doSendKeys(String locatorType, String locatorValue, String value) {
		nullCheck(value);
		getElement(locatorType, locatorValue).clear();
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	
	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
		
	}
	
	public void doSendKeys(WebElement element, String value) {
		nullCheck(value);
		element.clear();
		element.sendKeys(value);
	}

	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	
	public void doClick(By locator) {

		getElement(locator).click();

	}

	// Overloading above method for String based Locator
	public void doClick(String locatorType, String locatorValue) {

		getElement(locatorType, locatorValue).click();

	}

	
	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		log.info("element Text =>" + eleText);
		return eleText;
	}

	public String getElementDomAttributeValue(By locator, String AttrName) {
		nullCheck(AttrName);
		return getElement(locator).getAttribute(AttrName);

	}

	public String getElementDomPropertyValue(By locator, String propName) {
		nullCheck(propName);
		return getElement(locator).getAttribute(propName);

	}

	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			log.info("element is not present on the page using :" + locator);
			return false;
		} catch (StaleElementReferenceException e) {
			log.info("Element became stale: " + locator);
			return false;
		} catch (Exception e) {
			log.info("Some other error occurred: " + locator);
			// e.printStackTrace();
			return false;
		}

	}

	public By getBy(String locatorType, String locatorValue) {

		By locator = null;

		switch (locatorType.toUpperCase()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASS":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;

		default:
			log.info("Please pass the correct Locaqtor type " + locatorType);

		}
		return locator;
	}

	public WebElement getElement(String locatorType, String locatorValue) {

		return driver.findElement(getBy(locatorType, locatorValue));
	}

	
	public WebElement getElement(By locator) {
		
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}

		return element;
	}
	
	private void highlightElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}

		
	}
	

	public WebElement getElementWithWait(By locator, int timeout) {

		return waitForElementVisible(locator, timeout);

	}

	// ************************** Find Element's Utils ***********************

	public List<String> getElementTextList(By locator) {

		List<WebElement> eleList = getElements(locator);

		List<String> eleTextList = new ArrayList<String>(); // pc =0

		for (WebElement e : eleList) {
			String txt = e.getText();
			if (txt.length() != 0) {
				// log.info(txt);
				eleTextList.add(txt);
			}
		}

		return eleTextList;
	}

	public int getElementsCount(By locator) {
		int eleCount = getElements(locator).size();
		log.info("Total No. of Links ==>" + eleCount);
		return eleCount;
	}

	public void clickElement(By locator, String value) {
		List<WebElement> elelist = getElements(locator);
		log.info(elelist.size());
		for (WebElement e : elelist) {
			String text = e.getText();
			log.info(text);

			if (text.contains(value)) {
				e.click();
				break;
			}

		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// ******************************* Dropdown Utilities********************//

	public boolean doSelectDropdownbyIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByIndex(index);
			return true;
		}

		catch (org.openqa.selenium.NoSuchElementException e) {
			log.info(index + " is not present in the dropdown");

			return false;
		}

	}

	public boolean doSelectDropdownbyVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByVisibleText(visibleText);
			return true;
		}

		catch (org.openqa.selenium.NoSuchElementException e) {
			log.info(visibleText + " is not present  in the dropdown");
			return false;
		}

	}

	public boolean doSelectDropdownbyValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByValue(value);
			return true;
		}

		catch (org.openqa.selenium.NoSuchElementException e) {
			log.info(value + " is not present  in the dropdown");
			return false;
		}

	}

	public boolean selectDropdownValue(By locator, String value) {

		Select select = new Select(getElement(locator));
		List<WebElement> countryList = select.getOptions();

		log.info(countryList.size());
		boolean flag = false;
		for (WebElement e : countryList) {
			String listItem = e.getText();
			log.info(listItem);
			if (listItem.equals(value)) {

				e.click();
				flag = true;
				break;
			}

		}

		if (flag) {
			log.info(value + " is selected");
			return true;
		}

		else {
			log.info(value + " is not selected");
			return false;
		}

	}

	public List<String> getDropdownValueList(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> OptionList = select.getOptions();

		log.info(OptionList.size());

		List<String> OptionValList = new ArrayList<String>();
		for (WebElement e : OptionList) {

			String text = e.getText();
			OptionValList.add(text.trim());

		}

		return OptionValList;
	}

	public boolean getDropdownValueList(By locator, List<String> expList) {
		Select select = new Select(getElement(locator));

		List<WebElement> OptionList = select.getOptions();

		log.info(OptionList.size());

		List<String> OptionValList = new ArrayList<String>();
		for (WebElement e : OptionList) {

			String text = e.getText();
			OptionValList.add(text.trim());

		}

		if (expList.containsAll(OptionValList)) {
			return true;
		}

		else {
			return false;
		}
	}

//	****************** Dropdown util for non Select***********************//

	/**
	 * 1. Single selection - selectChoice(dropdownField, fullDropdownList, "choice
	 * 3"); 2. Multi Selection - selectChoice(dropdownField, fullDropdownList,
	 * "choice 3", "choice 2", "choice 6 2 1"); 3. All Select - But you need to send
	 * "All" - selectChoice(dropdownField, fullDropdownList, "all");
	 * 
	 * @param dropdown
	 * @param dropdownList
	 * @param DropdownValues
	 * @throws InterruptedException
	 */

	public void selectChoice(By dropdown, By dropdownList, String... DropdownValues) throws InterruptedException {

		doClick(dropdown);
		Thread.sleep(3000);

		List<WebElement> choices = getElements(dropdownList);
		log.info(choices.size());

		if (DropdownValues[0].equalsIgnoreCase("all")) {
			for (WebElement e : choices) {

				if (!e.isSelected()) {
					e.click();
				}
			}
		} else {

			for (WebElement e : choices) {
				String text = e.getText();
				log.info(text);

				for (String value : DropdownValues) {
					if (text.trim().equals(value)) {
						if (!e.isSelected()) {
							e.click();
						}
						break;
					}

				}

			}

		}

	}

// ************************** Action Utils ****************************

	public void doMoveToElement(By locator) throws InterruptedException {

		act.moveToElement(getElement(locator)).build().perform();
		Thread.sleep(3000);
	}

	public void handleParentChild(By parentElement, By subMenu) throws InterruptedException {

		act.moveToElement(driver.findElement(parentElement)).build().perform();
		Thread.sleep(5000);

		doClick(subMenu);

	}

	public void handle4LevelMenuHandle(By level1Menu, By level2Menu, By level3Menu, By level41Menu)
			throws InterruptedException {

		doClick(level1Menu);
		Thread.sleep(2000);
		doMoveToElement(level2Menu);
		Thread.sleep(2000);
		doMoveToElement(level3Menu);
		Thread.sleep(2000);
		doClick(level41Menu);

	}

	public void doActionsSendKeys(By locator, String txt) {

		act.sendKeys(getElement(locator), txt).perform();

	}

	public void doActionsClick(By locator) {

		act.click(getElement(locator)).perform();

	}

	public void doActionSendKeysWithPause(By locator, String value, long pauseTime) {

		char val[] = value.toCharArray();
		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}

	}

	// *********************WaitUtils*************************************//

	/**
	 * An Expectation for element to be present on the DOM of a page. This does not
	 * necessarily mean that element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	// *An expectation for checking that there is at least one element present on a
	// web page.

	public List<WebElement> waitForAllElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForAllElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(TimeoutException e){
			return Collections.EMPTY_LIST;
			
		}
	}

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An Expectation for element to be present on the DOM of a page and visible.
	 * Visibility means that the element is not only visible but also has height and
	 * width greater than 0
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
		
	}
	
	 /**
     * Waits for an element to become invisible
     * @param locator By locator of the element
     * @param timeout Maximum time to wait in seconds
     * @return true if element became invisible, false if timeout occurs
     */
    public boolean waitForElementInvisible(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            log.info("Element did not become invisible within " + timeout + " seconds: " + locator);
            return false;
        }
    }


	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
    
    
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void clickwithWait(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();
	}

	public void sendKeyshWait(By locator, int timeout, CharSequence... value) {
		waitForElementVisible(locator, timeout).sendKeys(value);
	}

	

	// ************Wait For Alert(JavaScript pop up)***************************
	
	public Alert waitForAlert(int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
						wait.pollingEvery(Duration.ofSeconds(2))
							.ignoring(NoAlertPresentException.class)
							.withMessage("====js Alert is not Present====");
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public void acceptAlert(int timeOut) {

		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {

		waitForAlert(timeOut).dismiss();
	}

	public String getTextAlert(int timeOut) {

		return waitForAlert(timeOut).getText();
	}

	public void sendKeysAlert(int timeOut, String value) {

		waitForAlert(timeOut).sendKeys(value);
	}

//***********Wait for Title*************************

	public String waitForTitleContains(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();

		} catch (TimeoutException e) {
			return null;

		}
	}

	public String waitForTitleIs(String Title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.titleIs(Title));
			return driver.getTitle();

		} catch (TimeoutException e) {
			return null;

		}

	}

//*********************WaitForURL*****************************

	public String waitForURLContains(String fractionURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;

		}
	}

	public String waitForURLIs(String URL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.urlToBe(URL));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;

		}

	}

//*************************WaitForFrame*****************

	public void waitForFrameandSwitchToIt(By frameLocator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameandSwitchToIt(String frameNameorID, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameorID));
	}

	public void waitForFrameandSwitchToIt(int frameIndex, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameandSwitchToIt(WebElement frameElement, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

//************WaitForWindows****************

	public boolean waitForWindow(int ExpectedWindowcount, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(ExpectedWindowcount));
		} catch (Exception e) {
			log.info("Expected Windows count is not correct");
			return false;
		}

	}
	
	/**
	 ************************FluentWait*******************
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=======Element is not Found========");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=======Element is not Found========");
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
	
	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState ==='complete'")).toString();
		return Boolean.parseBoolean(flag); //true
	}

}
