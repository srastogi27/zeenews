package com.zee.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zee.manager.WebDriverManager;

public class VideosPage {

	private WebDriver driver;
	private WebDriverWait driverWait;
	private static String advertiseTagOnVideo= "//div[@class='video_player']/descendant::div[@class='playkit-left-controls']/child::span[text()='Advertisement']";
	private static String skipAdvertiseOnVideo = "//div[contains(@class,'videoAdUiSkipContainer')]/descendant::button[@aria-label='Skip Ad']";
	private static String videoPlay = "//div[@class='playkit-bottom-bar']/descendant::button[contains(@class,'playkit-control-button') and @aria-label='Pause']";
	private static String videoPause = "//div[@class='playkit-bottom-bar']/descendant::button[contains(@class,'playkit-control-button') and @aria-label='Play']";
	private static String seekReverseButton = "//div[@class='playkit-tooltip']/button[@aria-label='Seek backwards']";
	private static String replayButton = "//button[@class='playkit-pre-playback-play-button']";
	private static String videoSectionFrame = "//div[@class='playkit-ads-container']/descendant::iframe[@allow='autoplay']";
	
	public VideosPage() {
		if(this.driver == null)
			this.driver = WebDriverManager.getWebDriverManager().getDriver();
	}
	
	public void handleAdvertise() {
		/*
		 * driverWait = new WebDriverWait(driver, 5);
		 * if(!driver.findElements(By.className("playkit-spinner")).isEmpty()) {
		 * driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className
		 * ("playkit-spinner"))); }
		 */
		driverWait = new WebDriverWait(driver, 10);
		if(driver.findElements(By.xpath(advertiseTagOnVideo)).isEmpty() && driver.findElements(By.xpath(replayButton)).isEmpty()) {
//		if(driver.findElements(By.xpath(advertiseTagOnVideo)).isEmpty() && driver.findElements(By.xpath(seekReverseButton)).isEmpty() && driver.findElements(By.xpath(replayButton)).isEmpty()) {
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(advertiseTagOnVideo)));
			if(!driver.findElements(By.xpath(advertiseTagOnVideo)).isEmpty()) {
				driver.switchTo().frame(driver.findElement(By.xpath(videoSectionFrame)));
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(skipAdvertiseOnVideo)));
				clickOnElement(skipAdvertiseOnVideo);
				driver.switchTo().defaultContent();
			}
		}else if(!driver.findElements(By.xpath(advertiseTagOnVideo)).isEmpty()) {
			driver.switchTo().frame(driver.findElement(By.xpath(videoSectionFrame)));
			driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(skipAdvertiseOnVideo)));
			clickOnElement(skipAdvertiseOnVideo);
			driver.switchTo().defaultContent();
		}
	}
	
	public void clickOnElement(String element) {
		if(!driver.findElements(By.xpath(element)).isEmpty())
			driver.findElement(By.xpath(element)).click();
	}
	
	public void playVideo() {
		clickOnElement(videoPause);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pauseVideo() {
		clickOnElement(videoPlay);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void resumeVideo() {
		pauseVideo();
		playVideo();
	}
}
