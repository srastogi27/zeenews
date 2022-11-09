package com.zee.webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.zee.actions.WebAction;
import com.zee.manager.WebDriverManager;
import com.zee.mobpages.IndiaPage;
import com.zee.utils.TestLogger;

public class HomePage {

	private WebDriver driver;
	public final String TITLE_OF_HOME_PAGE = "Zee News: Latest News, Live Breaking News, Today News, India Political News Updates";
	private static String topStoryAtHomePage = "(//div[@id='homepagea']/descendant::div[contains(@class,'top-story-desktop')]/descendant::a)[1]";
	private static String topStoryAtHomePageForMob = "(//div[@id='homepagea']/descendant::div[contains(@class,'single_news')]/descendant::a)[1]";
	private static String videosMenu = "//div[@id='sticky_header']/descendant::ul[@class='submenu']/li/descendant::a[@class='watch_menu']";
	private static String photoMenu = ".submenu .photos_menu";
	private static String MenuIconNewsPage = "[class*='category-slider'] [class*='category_item'] a";
	private static String crawlerMenus = "(//div[@class='menucaterory_container']/descendant::a[contains(text(),'valueTobeReplace')])[1]";
	
	public String getCrawlerMenu() {
		return crawlerMenus;
	}
	
	public HomePage() {
		if(this.driver == null)
			this.driver = WebDriverManager.getWebDriverManager().getDriver();
	}
	
	public void openTopHeadStory() {
		clickOnElement(topStoryAtHomePage);
		try {
			WebAction.getInstance().waitForPageToLoad(30);
		}catch (Exception e) {
			TestLogger.getInstance().error(e.getMessage());
		}
		
	}
	
	public IndiaPage openTopHeadStoryForMob() {
		clickOnElement(topStoryAtHomePageForMob);
		try {
			WebAction.getInstance().waitForPageToLoad(30);
		}catch (Exception e) {
			TestLogger.getInstance().error(e.getMessage());
		}
		return new IndiaPage();
	}
	
	public void clickAndopenVideos() {
		clickOnElement(videosMenu);
	}
	
	public void clickOnElement(String element) {
		System.out.println(driver.findElements(By.xpath(element)).isEmpty());
		if(!driver.findElements(By.xpath(element)).isEmpty()) {
			System.out.println("------------------>>>>>>>>>>>>>>>>>");
			driver.findElement(By.xpath(element)).click();
		}
		System.out.println("Not ENtereddddddd");
			
	}
	
	public void clickOnPhotoSection() {
		driver.findElement(By.cssSelector(photoMenu)).click();
	}
	
}
