package com.zee.msite;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.webdriver.AutomationChromeBase;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class BrowserSample extends AutomationChromeBase {

	public static void main(String[] args) throws MalformedURLException {

		// 1
		AndroidDriver<AndroidElement> driver = setupCapabilities();

		// 2
		driver.get("https://zeenews.india.com/video/news/green-transport-please-pave-way-for-worlds-first-hydrogen-train-fleet-2502928.html");

		// 3
//		driver.findElementByXPath("//*[@id='m_login_email']").sendKeys("your_email@gmail.com");
//		driver.findElementByXPath("//*[@id='m_login_password']").sendKeys("your_password");
//		driver.findElementByXPath("//*[@id=\"u_0_6\"]").click();
//		String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
//		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
//		System.out.println("******************************************************\n"+netData);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		for (LogEntry entry : entries) {
//		    System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			System.out.println(entry.getMessage());

		}
	}
}
