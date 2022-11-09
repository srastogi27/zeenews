package com.zee.mobpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndiaPage {

	public static final String ampLocatorForAticle = "//head/link[@rel='amphtml']";
	
	public String findAMPUrlForMob(WebDriver driver) {
		return driver.findElement(By.xpath(ampLocatorForAticle)).getAttribute("href");
	}
}
