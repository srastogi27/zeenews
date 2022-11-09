package com.zee.amp;

import java.util.List;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.zee.actions.MobUIAction;
import com.zee.base.Assertions;
import com.zee.constants.Constants;
import com.zee.mobpages.HomePage;
import com.zee.mobpages.IndiaPage;

public class TestNetworkCallsForAMP extends MobUIAction{

	@Test(priority = 1)
	@Parameters("URL")
	public void test01_ValidateGAandScoreCardCallsForArticleAMPpage(String url) {
		Assertions softAssert = new Assertions();
		HomePage homePage = new HomePage();
		
		testLogger.log(new Exception().getStackTrace()[0].getMethodName());
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP : Validate the Title of the Page");
		softAssert.assertTrue(validateTitleOfPage(homePage.TITLE_OF_HOME_PAGE), "Validated, the title of Home Page");
		
		testLogger.info("STEP : Open the Top Head Story");
		IndiaPage indiaPage = homePage.openTopHeadStory();
		handleBannerAdvertise();
		
		testLogger.info("STEP : Fetch the AMP URL From Article Page");
		String ampURL = indiaPage.findAMPUrlForMob(driver);
		
		testLogger.info("STEP : Move to AMP URL : " + ampURL);
		navigateToPage(ampURL);
		
		testLogger.log("STEP : Validate, The Page has been loaded."); 
		String titleOfPage = driver.getTitle(); 
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		
		setRequestParamForGAorScoreCard(entries, Constants.GA_CALLS_FOR_MOB, Constants.COLLECT_URL_ATTRIBUTE, null, null, true);
		testLogger.log("Verified, T Value of GA Calls : " + googleAnalyticCalls.getT());
		softAssert.assertEquals(googleAnalyticCalls.getT(), Constants.PAGE_VIEW, "Verified, T Value of GA Calls");

		testLogger.log("Verified, DT Value of GA Calls : " + googleAnalyticCalls.getDt());
		softAssert.assertEquals(googleAnalyticCalls.getDt(), titleOfPage, "Verified, DT Value of GA Calls");

		testLogger.log("Verified, DL Value of GA Calls : " + googleAnalyticCalls.getDl());
		softAssert.assertEquals(googleAnalyticCalls.getDl(), getCurrentPageURL(), "Verified, DL Value of GA Calls");
		
		testLogger.log("Verified, TID Value of GA Calls : " + googleAnalyticCalls.getTid());
		softAssert.assertEquals(googleAnalyticCalls.getTid(), Constants.UA_2069755_1, "Verified, TID Value of GA Calls");
		 
		testLogger.log("STEP : Fetched SCORE-CARD CALLS the Network Logs");
		setRequestParamForGAorScoreCard(entries, Constants.SCORECARD_CALLS_FOR_MOB, Constants.C1, null, null, true);
		
		testLogger.log("Verified, C2 Value of SCORE-CARD Calls : " + scoreCardCalls.getC2()); 
		softAssert.assertEquals(scoreCardCalls.getC2(), Constants.C2_9254297, "Verified, C2 Attribute value of SCORE-CARD Calls");
		
		testLogger.log("Verified, C7 Value of SCORE-CARD Calls : " + scoreCardCalls.getC7AMP()); 
		softAssert.assertEquals(scoreCardCalls.getC7AMP(), getCurrentPageURL(), "Verified, C7 Attribute value of SCORE-CARD Calls");
		  
		testLogger.log("Verified, C8 Value of SCORE-CARD Calls : " + scoreCardCalls.getC8()); 
		softAssert.assertEquals(scoreCardCalls.getC8(), titleOfPage, "Verified, C8 Attribute value of SCORE-CARD Calls");

		softAssert.assertAll();
	}
}
