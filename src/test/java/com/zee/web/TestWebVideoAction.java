package com.zee.web;

import java.util.List;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.zee.actions.WebAction;
import com.zee.constants.Constants;
import com.zee.webpages.HomePage;
import com.zee.webpages.VideosPage;

public class TestWebVideoAction extends WebAction {
	
	@Test(priority = 1, testName = "Validate, Video Play Action and GA and ScoreCard Research Network Calls Attribute.")
	@Parameters("URL")
	public void test01_Fetch_GAorScoreCard_Request_After_PerformThePlayActionAtVideo(@Optional String url) throws InterruptedException {
		HomePage homePage = new HomePage();
		VideosPage videosPage = new VideosPage();
		SoftAssert softAssert = new SoftAssert();

		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Videos Section");
		homePage.clickAndopenVideos();
		String titleOfPage = getTitle();

		testLogger.info("STEP : Skip The Advertise");
		handleBannerAdvertise();
		videosPage.handleAdvertise();

		String tID = getTIDFromGAForWeb(entries, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, TID Value of GA Calls : " + tID);
		softAssert.assertEquals(tID, "UA-2069755-1", "Verified, TID Attribute value of GA Calls");

		testLogger.log("STEP : Fetched Google-Analytics CALLS the Network Logs");		
		List<LogEntry> entries2 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		setRequestParamForGAorScoreCard(entries2, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.IMAGE, Constants.PLAY);
		
		testLogger.log("Verified, EN Value of GA Calls : " + googleAnalyticCalls.getEn());
		softAssert.assertEquals(googleAnalyticCalls.getEn(), Constants.PLAY, "Verified, EN Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");

		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries2, Constants.SCORE_CARD_CALLS_FOR_WEB, Constants.C1, Constants.IMAGE, null);

		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2());
		softAssert.assertEquals(scoreCardCalls.getC2(), "9254297", "Verified, C2 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7());
		softAssert.assertEquals(scoreCardCalls.getC7(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8());
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfPage, "Verified, C8 Attribute value of SCORE-CARD Calls");

		softAssert.assertAll();
	}
	
	@Test(priority = 2, testName = "Validate, Video Pause Action and GA and ScoreCard Research Network Calls Attribute.")
	@Parameters("URL")
	public void test02_Fetch_GAorScoreCard_Request_After_PerformThePauseActionAtVideo(@Optional String url) throws InterruptedException {
		HomePage homePage = new HomePage();
		VideosPage videosPage = new VideosPage();
		SoftAssert softAssert = new SoftAssert();

		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Videos Section");
		homePage.clickAndopenVideos();
		String titleOfPage = getTitle();

		testLogger.info("STEP : Skip The Advertise");
		handleBannerAdvertise();
		videosPage.handleAdvertise();
		videosPage.pauseVideo();

		String tID = getTIDFromGAForWeb(entries, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, TID Value of GA Calls : " + tID);
		softAssert.assertEquals(tID, "UA-2069755-1", "Verified, TID Attribute value of GA Calls");

		testLogger.log("STEP : Fetched Google-Analytics CALLS the Network Logs");		
		List<LogEntry> entries2 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		setRequestParamForGAorScoreCard(entries2, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.IMAGE, Constants.PAUSE);
		
		testLogger.log("Verified, EN Value of GA Calls : " + googleAnalyticCalls.getEn());
		softAssert.assertEquals(googleAnalyticCalls.getEn(), Constants.PAUSE, "Verified, EN Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");

		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries2, Constants.SCORE_CARD_CALLS_FOR_WEB, Constants.C1, Constants.IMAGE, null);

		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2());
		softAssert.assertEquals(scoreCardCalls.getC2(), "9254297", "Verified, C2 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7());
		softAssert.assertEquals(scoreCardCalls.getC7(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8());
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfPage, "Verified, C8 Attribute value of SCORE-CARD Calls");

		softAssert.assertAll();
	}

	@Test(priority = 3, testName = "Validate, Video Resume Action and GA and ScoreCard Research Network Calls Attribute.")
	@Parameters("URL")
	public void test03_Fetch_GAorScoreCard_Request_After_PerformTheResumeActionAtVideo(@Optional String url) throws InterruptedException {
		HomePage homePage = new HomePage();
		VideosPage videosPage = new VideosPage();
		SoftAssert softAssert = new SoftAssert();

		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Videos Section");
		homePage.clickAndopenVideos();
		String titleOfPage = getTitle();

		testLogger.info("STEP : Skip The Advertise");
		handleBannerAdvertise();
		videosPage.handleAdvertise();
		videosPage.resumeVideo();

		String tID = getTIDFromGAForWeb(entries, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, TID Value of GA Calls : " + tID);
		softAssert.assertEquals(tID, "UA-2069755-1", "Verified, TID Attribute value of GA Calls");

		testLogger.log("STEP : Fetched Google-Analytics CALLS the Network Logs");		
		List<LogEntry> entries2 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		setRequestParamForGAorScoreCard(entries2, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.IMAGE, Constants.RESUME);
		
		testLogger.log("Verified, EN Value of GA Calls : " + googleAnalyticCalls.getEn());
		softAssert.assertEquals(googleAnalyticCalls.getEn(), Constants.RESUME, "Verified, EN Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");

		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries2, Constants.SCORE_CARD_CALLS_FOR_WEB, Constants.C1, Constants.IMAGE, null);

		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2());
		softAssert.assertEquals(scoreCardCalls.getC2(), "9254297", "Verified, C2 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7());
		softAssert.assertEquals(scoreCardCalls.getC7(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");

		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8());
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfPage, "Verified, C8 Attribute value of SCORE-CARD Calls");

		softAssert.assertAll();
	}
}
