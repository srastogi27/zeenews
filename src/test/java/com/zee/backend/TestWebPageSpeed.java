package com.zee.backend;

import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.zee.actions.WebAction;
import com.zee.base.Assertions;
import com.zee.constants.Constants;
import com.zee.entities.PageSpeedEntity;
import com.zee.utils.GenericUtility;
import com.zee.webpages.HomePage;
import com.zee.webpages.PhotoPage;
import com.zee.webpages.VideosPage;

public class TestWebPageSpeed extends WebAction{
	
	XSSFWorkbook workbook = GenericUtility.getGenericUtility().getWorkbook();
	XSSFSheet sheet = GenericUtility.getGenericUtility().getXSSFSheet(workbook, "PageSpeed");
	ArrayList<PageSpeedEntity> arrayList = new ArrayList<PageSpeedEntity>();

	@Test(priority = 1, testName = "Validate, Page Speed of an Article on Web", enabled = true)
	@Parameters("URL")
	public void test01_ValidatePageSpeedOfArticleForWeb(@Optional String url) {
		HomePage homePage = new HomePage();
		SoftAssert softAssert = new SoftAssert();
		
		testLogger.log(new Exception().getStackTrace()[0].getMethodName());
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Top Head Story");
		homePage.openTopHeadStory();
		handleBannerAdvertise();
		
		PageSpeedEntity entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, false);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		softAssert.assertAll();
	}
	
	@Test(priority = 2, testName = "Validate, Page Speed of a Video on Web", enabled = false)
	@Parameters("URL")
	public void test02_ValidatePageSpeedOfVideoForWeb(@Optional String url) {
		HomePage homePage = new HomePage();
		VideosPage videosPage = new VideosPage();
		SoftAssert softAssert = new SoftAssert();

		testLogger.log(new Exception().getStackTrace()[0].getMethodName());
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Videos Section");
		homePage.clickAndopenVideos();
		handleBannerAdvertise();
		
		testLogger.info("STEP : Skip the Advertise");
		videosPage.handleAdvertise();
		pause(3);
		
		PageSpeedEntity entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, false);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);

		softAssert.assertAll();
	}
	
	@Test(priority = 3, testName = "Validate, Page Speed of a Photo Gallery on Web", enabled = false)
	@Parameters("URL")
	public void test03_ValidatePageSpeedOfPhotosForWeb(@Optional String url) {
		HomePage homePage = new HomePage();
		PhotoPage photoPage = new PhotoPage();
		SoftAssert softAssert = new SoftAssert();
		
		testLogger.log(new Exception().getStackTrace()[0].getMethodName());
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Photos Section");
		homePage.clickOnPhotoSection();
		photoPage.waitForImageToLoad();
		handleBannerAdvertise();
		
		testLogger.info("STEP: Select and Open the First Image.");
		photoPage.selectTheFirstPictureAppearOnPhotoSectionPage();
		
		pause(3);
		PageSpeedEntity entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, false);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		softAssert.assertAll();
	}
	
	@Test(priority = 4, testName = "Validate, Page Speed of Crawler Menus on Web", enabled = true)
	@Parameters("URL")
	public void test04_ValidatePageSpeedOfCrawlerMenus(@Optional String url) {
		HomePage homePage = new HomePage();
		Assertions softAssert = new Assertions();
		PageSpeedEntity entity = new PageSpeedEntity(workbook, sheet);
		
		testLogger.log(new Exception().getStackTrace()[0].getMethodName());
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");
		
		testLogger.info("STEP : Move To Crawler Menu : LIVE TV and Validate Page Insight of the Page");
		clickByDynXpath(homePage.getCrawlerMenu(), Constants.LIVE_TV);
		String currentPageURL = driver.getCurrentUrl();
		softAssert.assertTrue(softAssert.assertContains("live-tv", currentPageURL), "Validate, User has been Landed at : " + currentPageURL);
		
		entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, true);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		testLogger.info("STEP : Move To Crawler Menu : LATEST NEWS and Validate Page Insight of the Page");
		clickByDynXpath(homePage.getCrawlerMenu(), Constants.LATEST_NEWS);
		currentPageURL = driver.getCurrentUrl();
		softAssert.assertTrue(softAssert.assertContains("latest-news", currentPageURL), "Validate, User has been Landed at : " + currentPageURL);
		
		entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, true);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		testLogger.info("STEP : Move To Crawler Menu : INDIA and Validate Page Insight of the Page");
		clickByDynXpath(homePage.getCrawlerMenu(), Constants.INDIA);
		currentPageURL = driver.getCurrentUrl();
		softAssert.assertTrue(softAssert.assertContains("india", currentPageURL), "Validate, User has been Landed at : " + currentPageURL);
		
		entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, true);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		testLogger.info("STEP : Move To Crawler Menu : ENTERTAINMENT and Validate Page Insight of the Page");
		clickByDynXpath(homePage.getCrawlerMenu(), Constants.ENTERTAINMENT);
		currentPageURL = driver.getCurrentUrl();
		softAssert.assertTrue(softAssert.assertContains("entertainment", currentPageURL), "Validate, User has been Landed at : " + currentPageURL);
		
		entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, true);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		testLogger.info("STEP : Move To Crawler Menu : SPORTS and Validate Page Insight of the Page");
		clickByDynXpath(homePage.getCrawlerMenu(), Constants.SPORTS);
		currentPageURL = driver.getCurrentUrl();
		softAssert.assertTrue(softAssert.assertContains("sports", currentPageURL), "Validate, User has been Landed at : " + currentPageURL);
		
		entity = validatePageInsight(driver.getCurrentUrl(), driver.getTitle(), Constants.DESKTOP, workbook, sheet, true);
		softAssert.assertEquals(entity.getCoreWebVitalsAssessment(), Constants.PASSED, "Validated, The Core Vitals Assessment has been passed or failed.");
		arrayList.add(entity);
		
		softAssert.assertAll();
	}

	@Test(priority = 99, alwaysRun = true, enabled = true)
	public void writeDataInExcel() {
		genericUtility.writeDataInWorkbook(workbook, sheet, arrayList, Constants.EXCEL_SHEET_NAME);
	}
}
