package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.StringUtils;

import static com.qa.opencart.constants.AppConstants.*;

public class RegistrationPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmPassword = By.id("input-confirm");

	private final By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type = 'radio']");
	private final By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type = 'radio']");
	private final By agreeCheckBox = By.name("agree");
	private final By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private final By successMesg = By.cssSelector("div#content h1");
	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public Boolean userRegistration(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, DEFAULT_TIMEOUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, StringUtils.getRandonEmailId());
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);

		
		if (eleUtil.waitForElementVisible(successMesg, MEDIUM_DEFAULT_TIMEOUT).getText().contains(REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);

			return true;

		}
		return false;

	}

}