package com.qa.opencart.util;

public class StringUtils {
	
	public static String getRandonEmailId() {
		return "testAutomation"+System.currentTimeMillis()+"@open.com";
	}

	//delete from user where email like %testAutomation%
	
}
