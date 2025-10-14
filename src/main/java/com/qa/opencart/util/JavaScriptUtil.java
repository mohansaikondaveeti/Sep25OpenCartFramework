package com.qa.opencart.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js= (JavascriptExecutor)this.driver;
		
		}
	
	public String getTitleByJs() {
	return	js.executeScript("return document.title;").toString();
	}
	
	public String getURLByJs() {
		return	js.executeScript("return document.URL;").toString();
		}
	
	public void refreshBrowserbyJS() {
		js.executeScript("history.go(0)");
	}
	
	public void navigateToBackPage() {
		js.executeScript("history.go(-1)");
	}
	
	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}
	
	public void generateJSAlert(String message) {
		js.executeScript("alert('"+message+"'");
		try {
			Thread.sleep(2000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		driver.switchTo().alert().dismiss();
	}
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void clickElementByJs(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public void JsScrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void JsScrollPageDown(String Height) {
		js.executeScript("window.scrollTo(0, '"+Height+"');");
	}
	
	public void JsScrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}
	
	public void ScrollIntoView(WebElement element) {
		js.executeScript("arguments[0].ScrollIntoView(true);", element);
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void flash(WebElement element) {
		String bgColor = element.getCssValue("backgroundColor");
		for(int i= 0; i<5; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgColor, element);
		}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor= '"+color+"'", element);
		
		try {
			Thread.sleep(20);
		}
		catch (InterruptedException e) {
			
		}
	}
	
	/**
	 * example: "document.body.style.zoom = '400.0%'"
	 * @param zoomPercentage
	 */
	public void zoomChromeEdgeFirefox(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
		
	}
	
	/**
	 * example: "document.body.style.MozTransform = 'scale(0.5)';"
	 * @param zoomPercentage
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.zoomPercentage = 'scale("+zoomPercentage+")'";
		js.executeScript(zoom);
		
	}
	
	public void sendKeysUsingWithIdByJS(String id, String value) {
		js.executeScript("document.getElementById('"+id+"').value = '"+value+"'");
				//document.getElementById('input-email').value='tom@gmail.com'
	}
}
