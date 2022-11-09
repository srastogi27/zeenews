package com.zee.backend;

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

public class TestWebNetworkCalls extends WebAction {
	
	@Test(priority = 1, testName = "Validate, GA and ScoreCard Research Network Calls Attribute After Open the Article on Web")
	@Parameters("URL")
	public void test01_Validate_GA_And_Scorecard_Request_AfterOpeningTheArticle_For_Web(@Optional String url) throws InterruptedException {
		HomePage homePage = new HomePage();
		SoftAssert softAssert = new SoftAssert();
		
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Top Head Story");
		homePage.openTopHeadStory();
		handleBannerAdvertise();
		String titleOfHeadStoryPage = getTitle();

		testLogger.log("STEP : Fetched GA CALLS the Network Logs");
		List<LogEntry> entries2 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		String tID = getTIDFromGAForWeb(entries, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, TID Value of GA Calls : " + tID);
		softAssert.assertEquals(tID, "UA-2069755-1", "Verified, TID Attribute value of GA Calls");

		setRequestParamForGAorScoreCard(entries2, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, T Value of GA Calls : " + googleAnalyticCalls.getT());
		softAssert.assertEquals(googleAnalyticCalls.getT(), Constants.PAGE_VIEW, "Verified, T Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfHeadStoryPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");

		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries2, Constants.SCORE_CARD_CALLS_FOR_WEB, Constants.C1, Constants.IMAGE, null);
		
		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2()); 
		softAssert.assertEquals(scoreCardCalls.getC2(), "9254297", "Verified, C2 Attribute value of SCORE-CARD Calls");
		
		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7()); 
		softAssert.assertEquals(scoreCardCalls.getC7(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");
		  
		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8()); 
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfHeadStoryPage, "Verified, C8 Attribute value of SCORE-CARD Calls");
		
		softAssert.assertAll();
	}
	
	@Test(priority = 2, testName = "Validate, GA with Incorrect TID and ScoreCard Research Network Calls Attribute After Open the Article on Web")
	@Parameters("URL")
	public void test02_Validate_GA_With_IncorrectTID_And_Scorecard_Request_AfterOpeningTheArticle_For_Web(@Optional String url) throws InterruptedException {
		HomePage homePage = new HomePage();
		SoftAssert softAssert = new SoftAssert();
		
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");

		testLogger.info("STEP : Open the Top Head Story");
		homePage.openTopHeadStory();
		handleBannerAdvertise();
		String titleOfHeadStoryPage = getTitle();

		testLogger.log("STEP : Fetched GA CALLS the Network Logs");
		List<LogEntry> entries2 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		String tID = getTIDFromGAForWeb(entries, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, TID Value of GA Calls : " + tID);
		softAssert.assertEquals(tID, "UA-2069755-123", "Verified, TID Attribute value of GA Calls");

		setRequestParamForGAorScoreCard(entries2, Constants.GA_CALLS_FOR_WEB, Constants.COLLECT_URL_ATTRIBUTE, Constants.XHR, null);
		testLogger.log("Verified, T Value of GA Calls : " + googleAnalyticCalls.getT());
		softAssert.assertEquals(googleAnalyticCalls.getT(), Constants.PAGE_VIEW, "Verified, T Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfHeadStoryPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");

		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries2, Constants.SCORE_CARD_CALLS_FOR_WEB, Constants.C1, Constants.IMAGE, null);
		
		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2()); 
		softAssert.assertEquals(scoreCardCalls.getC2(), "9254297", "Verified, C2 Attribute value of SCORE-CARD Calls");
		
		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7()); 
		softAssert.assertEquals(scoreCardCalls.getC7(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");
		  
		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8()); 
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfHeadStoryPage, "Verified, C8 Attribute value of SCORE-CARD Calls");
		
		softAssert.assertAll();
	}
	
}
