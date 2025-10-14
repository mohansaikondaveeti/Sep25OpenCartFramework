package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

import org.testng.annotations.Test;
import static com.qa.opencart.constants.AppConstants.*;

public class RegisterPageTest extends BaseTest{
	
	
	@BeforeClass
	
	public void registerSetUp() {
		registrationPage = loginpage.navigateToRegistrationPage();
	}
	
	
	@DataProvider()
	public Object[][] getUserRegistrationData() {
		return new Object[][] {
			{"MASK", "Sharma",  "9848022338", "Pass@12345", "Yes"},
				{"MASK1", "Sharma", "9848022338", "Pass@12345", "Yes"}
			};
			
}
	@DataProvider()
	public Object[][] getUserRegData() {
		Object regData [][] = ExcelUtil.getTestData(PRODUCT_SHEET_NAME);
		return regData;

	}
	
	@Test(dataProvider = "getUserRegistrationData")
public void userRegisterTest(String firstName, String LastName, String telephone, String password, String subscribe) {
	Assert.assertTrue(
			registrationPage.userRegistration(firstName, LastName, telephone, password, subscribe));
}
}
