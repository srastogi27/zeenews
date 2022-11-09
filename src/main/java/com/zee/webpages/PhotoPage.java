package com.zee.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.zee.manager.WebDriverManager;

public class PhotoPage {

	private WebDriver driver;
	private static String firstImage = ".row.morephotosnav1 .photo-thumb-img1 a";
	private static String sizeOfImages = "//div[@class='photo-thumb-img1']/..//span[@class='video_duration']"; 
	private static String totalImages = ".pcount>.ptotal";
	
	public PhotoPage() {
		if(this.driver == null)
			this.driver = WebDriverManager.getWebDriverManager().getDriver();
	}
	
	public void waitForImageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(firstImage)));
	}
	
	public String getTitleOfImage() {
		return driver.findElement(By.tagName("h1")).getText();
	}
	
	public int getCountOfImagesPresentInsidePhotoPageImage() {
		return Integer.parseInt(driver.findElement(By.xpath(sizeOfImages)).getText());
	}
	
	public void selectTheFirstPictureAppearOnPhotoSectionPage() {
		WebElement firstPhotoInSection = driver.findElements(By.cssSelector(firstImage)).get(0);
		firstPhotoInSection.click();
	}
	
	public int getTotalNumberOfImagesPresentAfterClickOnImageFromPhotoSectionPage() {
		return (driver.findElements(By.cssSelector(totalImages)).size());
	}
}
