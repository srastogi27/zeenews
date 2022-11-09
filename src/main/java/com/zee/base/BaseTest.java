package com.zee.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.io.Files;
import com.zee.constants.APIConstants;
import com.zee.constants.Constants;
import com.zee.entities.GoogleAnalyticCalls;
import com.zee.entities.NetworkEntity;
import com.zee.entities.PageSpeedEntity;
import com.zee.entities.ScoreCardCalls;
import com.zee.manager.MobDriverManager;
import com.zee.manager.WebDriverManager;
import com.zee.utils.GenericUtility;
import com.zee.utils.TestLogger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

public class BaseTest {

	private static final Logger s_logger = LoggerFactory.getLogger(BaseTest.class);

	public RequestSpecification apiDriver;
	public WebDriver driver;
	public GoogleAnalyticCalls googleAnalyticCalls;
	public ScoreCardCalls scoreCardCalls;
	public TestLogger testLogger;
	public GenericUtility genericUtility;
	private static String smallBannerAdv = "//a[contains(@style,'data:image/png')]";
	private static String closeBtnOfBigBanner = "//a[@class='closeBtn']";

	@BeforeSuite(alwaysRun = true)
	public void cleanDirectories() {
		genericUtility = GenericUtility.getGenericUtility();
		testLogger = TestLogger.getInstance();
		try {
			FileUtils.cleanDirectory(new File("pageSpeedOutput"));
			FileUtils.cleanDirectory(new File(genericUtility.getUserJenkinsHome()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @BeforeClass(alwaysRun = true) public void initializeTheDriver() {
	 * genericUtility = GenericUtility.getGenericUtility(); testLogger =
	 * TestLogger.getInstance(); }
	 */

	@BeforeMethod
	@Parameters({ "APPIUM_URL", "DEVICE_TYPE", "DEVICE_NAME", "BROWSER_NAME", "USE_APIDRIVER_WITH_WEB" })
	public void initChrome(String url, String deviceType, String deviceName, String browserName,
			String useAPIDriverWithWeb) {
		if (useAPIDriverWithWeb.equalsIgnoreCase("false")) {
			apiDriver = RestAssured.given();
		} else {
			if (deviceType.equalsIgnoreCase("WEB")) {
				driver = WebDriverManager.getWebDriverManager().initDriver(browserName);
			} else {
				driver = MobDriverManager.mobDriverManager().initDriver(url, deviceType, deviceName, browserName);
			}
			apiDriver = RestAssured.given();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
		driver.quit();
	}

	public void implicitWait(long unit) {
		driver.manage().timeouts().implicitlyWait(unit, TimeUnit.SECONDS);
	}

	public void waitForPageToLoad(long unit) {
		driver.manage().timeouts().pageLoadTimeout(unit, TimeUnit.SECONDS);
	}

	public boolean validateTitle(String title) {
		return driver.getTitle().equalsIgnoreCase(title);
	}

	public void showAllNetworkCalls(List<LogEntry> entries) {
		for (LogEntry entry : entries) {
			testLogger.info("Received Network Call : " + entry.getMessage());
		}
	}

	public String getC7UrlScoreCard(String url) {
		if (url.contains("/amp"))
			return url.split("/amp")[0];
		return url;
	}

	/**
	 * Get the User Current Working Directory Path
	 * 
	 * @return
	 */
	public String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 
	 * @param jsonNode
	 * @param eventCalls   : google-analytics.com | sb.scorecardresearch.com
	 * @param urlAttribute : collect | c1
	 * @param type         : XHR | Image
	 * @return
	 */
	public boolean checkURLCondForGAorScoreCard(JsonNode jsonNode, String eventCalls, String urlAttribute,
			String type) {
		boolean flag = false;
		if (jsonNode.findValue(Constants.METHOD).asText().contains(Constants.NETWORK_REQ_SENT)
				&& !jsonNode.at(Constants.REQ_URL).asText().isEmpty()
				&& jsonNode.at(Constants.REQ_URL).asText().contains(eventCalls)
				&& jsonNode.at(Constants.REQ_URL).asText().contains(urlAttribute)
				&& jsonNode.at(Constants.TYPE_PATH).asText().contains(type)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param jsonNode
	 * @param eventCalls   : google-analytics.com | sb.scorecardresearch.com
	 * @param urlAttribute : collect | c1
	 * @return
	 */
	public boolean checkURLCondForGAorScoreCard(JsonNode jsonNode, String eventCalls, String urlAttribute) {
		boolean flag = false;
		if (jsonNode.findValue(Constants.METHOD).asText().contains(Constants.NETWORK_REQ_SENT)
				&& !jsonNode.at(Constants.REQ_URL).asText().isEmpty()
				&& jsonNode.at(Constants.REQ_URL).asText().contains(eventCalls)
				&& jsonNode.at(Constants.REQ_URL).asText().contains(urlAttribute)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param jsonNode
	 * @param eventCalls : google-analytics.com | sb.scorecardresearch.com
	 * @param action     : Play | Pause
	 * @return
	 */
	public boolean checkURLCondForGAWithEAaction(JsonNode jsonNode, String eventCalls, String urlAttribute, String type,
			String action) {
		boolean flag = false;
		if (jsonNode.findValue(Constants.METHOD).asText().contains(Constants.NETWORK_REQ_SENT)
				&& !jsonNode.at(Constants.REQ_URL).asText().isEmpty()
				&& jsonNode.at(Constants.REQ_URL).asText().contains(eventCalls)
				&& jsonNode.at(Constants.REQ_URL).asText().contains(urlAttribute)
				&& jsonNode.at(Constants.TYPE_PATH).asText().contains(type)
				&& jsonNode.at(Constants.REQ_URL).asText().contains("&ea=" + action)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param input        : tid=UA-2456718-1 | c1=2345678
	 * @param urlAttribute : tid | c1 | dl | dt
	 * @return
	 */
	public boolean isParameterExistInURL(String input, String urlAttribute) {
		boolean flag = false;
		if (input.contains(Constants.EQUALS) && !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(input).find()
				&& input.split(Constants.EQUALS)[0].equalsIgnoreCase(urlAttribute)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param input     : tid=UA-2456718-1 | c1=2345678
	 * @param urlParams : tid | c1 | dl | dt
	 * @return
	 */
	public boolean isParameterNotExistInURL(String input, String urlParams) {
		boolean flag = false;
		if (input.contains(Constants.EQUALS) && !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(input).find()
				&& !input.split(Constants.EQUALS)[0].equalsIgnoreCase(urlParams)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param input     : tid=UA-2456718-1 | c1=2345678
	 * @param urlParams : tid | c1 | dl | dt
	 * @return
	 */
	public boolean isKeyValuePresent(String input) {
		boolean flag = false;
		if (input.contains(Constants.EQUALS)
				&& !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(input).find()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Validate EA Condition
	 * 
	 * @param input
	 * @return
	 */
	public boolean validateEAcond(String input) {
		return input.split(Constants.EQUALS)[0].equalsIgnoreCase("ea");
	}

	/**
	 * Handle the Banner Advertise
	 */
	public void handleBannerAdvertise() {
		if (isPresent(smallBannerAdv))
			clickByJS(smallBannerAdv);
		pause(2);
		if (isPresent(closeBtnOfBigBanner))
			clickByJS(closeBtnOfBigBanner);
	}

	/**
	 * Validate Locator is visible at Page
	 * 
	 * @param element
	 * @return
	 */
	public boolean isPresent(String element) {
		return !driver.findElements(By.xpath(element)).isEmpty();
	}

	/**
	 * Click by Java Script
	 * 
	 * @param element
	 */
	public void clickByJS(String element) {
		WebElement webElement = driver.findElement(By.xpath(element));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
	}

	/**
	 * Click by Java Script
	 * 
	 * @param element
	 */
	public void clickByDynXpath(String xpath, String substitute) {
		if (!xpath.isEmpty() && !substitute.isEmpty()) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String updatedXpath = xpath.contains("valueTobeReplace") ? xpath.replace("valueTobeReplace", substitute)
					: xpath;
			WebElement webElement = driver.findElement(By.xpath(updatedXpath));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", webElement);
			waitForPageLoad(driver);
		}
		pause(3);
	}

	/**
	 * 
	 * @param timeInSeconds: Give the time in seconds
	 */
	public void pause(long timeInSeconds) {
		long wait = timeInSeconds * 1000;
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wait for the page gets loaded completely
	 * 
	 * @param driver
	 */
	public void waitForPageLoad(WebDriver driver) {
		try {
			new WebDriverWait(driver, Constants.PAGE_LOAD_TIMEOUT).until(new Function<WebDriver, Boolean>() {
				public Boolean apply(@Nullable WebDriver input) {
					if (input == null) {
						return false;
					}
					Object resp = ((JavascriptExecutor) input).executeScript("return document.readyState=='complete'");
					return resp instanceof Boolean && ((Boolean) resp);
				}
			});
		} catch (Exception e) {
			s_logger.info("Exception waiting for page to load: {}", e);
		}
	}

	/**
	 * Check object is not null
	 * 
	 * @param object
	 * @return
	 */
	public boolean isNullable(Object object) {
		return object != null;
	}

	/**
	 * Get the URL List of Network Calls
	 * 
	 * @param entries      - Log Entry
	 * @param eventCalls   - google-analytics.com | sb.scorecardresearch.com
	 * @param urlAttribute - collect | c1
	 * @param type         - XHR | IMAGE
	 * @return
	 */
	public List<String> getListOfNetworkURLs(List<LogEntry> entries, String eventCalls, String urlAttribute,
			String type, String action) {
		JsonNode jsonNode = null;
		ArrayList<String> list = new ArrayList();
		ObjectMapper objectMapper = genericUtility.getObjectMapper();
		for (LogEntry entry : entries) {
			try {
				jsonNode = objectMapper.readTree(entry.getMessage());
			} catch (IOException e) {
				testLogger.error(e.getMessage());
			}
			if (action != null) {
				if (checkURLCondForGAWithEAaction(jsonNode, eventCalls, urlAttribute, type, action)
						&& isNullable(jsonNode)) {
					list.add(jsonNode.at(Constants.REQ_URL).asText());
				}
			} else {
				if (checkURLCondForGAorScoreCard(jsonNode, eventCalls, urlAttribute) && isNullable(jsonNode)) {
					list.add(jsonNode.at(Constants.REQ_URL).asText());
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param list:      List of the URL
	 * @param urlParams: tid | dl | dt | t
	 * @return
	 */
	public Map<String, String> setRequestParams(List<String> list, String urlParams) {
		HashMap<String, String> params = new HashMap<>();
		String temp = null;
		String url;
		if (!list.isEmpty()) {
			url = list.get(0);
			TestLogger.getInstance().info("The URL is : " + url);
			String[] array = url.split(Constants.AMPHERCENT);
			temp = array[0].split(Constants.REGEX_QUESTION)[1];
			params.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
			for (int j = 0; j < array.length; j++) {
				if (isParameterNotExistInURL(array[j], urlParams)) {
					params.put(array[j].split(Constants.EQUALS)[0], array[j].split(Constants.EQUALS)[1]);
				}
			}
		} else {
			testLogger.error("Request URL not found in the List.");
		}
		return params;
	}

	/**
	 * 
	 * @param list:      List of the URL
	 * @param urlParams: tid | dl | dt | t
	 * @return
	 */
	public Map<String, String> setRequestParams(List<String> list) {
		HashMap<String, String> params = new HashMap<>();
		String temp = null;
		String url;
		if (!list.isEmpty()) {
			url = list.get(0);
			TestLogger.getInstance().info("The URL is : " + url);
			String[] array = url.split(Constants.AMPHERCENT);
			temp = array[0].split(Constants.REGEX_QUESTION)[1];
			params.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
			for (int j = 0; j < array.length; j++) {
				if (isKeyValuePresent(array[j])) {
					params.put(array[j].split(Constants.EQUALS)[0], array[j].split(Constants.EQUALS)[1]);
				}
			}
		} else {
			testLogger.error("Request URL not found in the List.");
		}
		return params;
	}

	/**
	 * Convert the Decimal to Percentage
	 * 
	 * @param input
	 * @return
	 */
	public String convertDecimalToPercentage(String input, boolean inSec, boolean isHighlight) {
		String color = "";
		if (isHighlight) {
			if (Double.parseDouble(input) >= 2.5 && Double.parseDouble(input) <= 4.0) {
				color = Constants.YELLOW;
			} else if (Double.parseDouble(input) > 4.0) {
				color = Constants.RED;
			}
		}
		if (inSec)
			return ((Double.parseDouble(input) * 100) + "%" + " " + color).trim();
		return ((Double.parseDouble(input) * 100) + "" + " " + color).trim();
	}
	
	/**
	 * Convert the Decimal to Percentage
	 * 
	 * @param input
	 * @return
	 */
	public String convertDecimalToPercentageForOverAllPerformance(String input, boolean inSec, boolean isHighlight) {
		String color = "";
		if (isHighlight) {
			if ((Double.parseDouble(input) * 100) < 90.0 && (Double.parseDouble(input) * 100) >= 50.0) {
				color = Constants.ORANGE;
			} else if ((Double.parseDouble(input) * 100) <= 49.0) {
				color = Constants.RED;
			}
		}
		if (inSec)
			return ((Double.parseDouble(input) * 100) + "%" + " " + color).trim();
		return ((Double.parseDouble(input) * 100) + "" + " " + color).trim();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public String convertPercentileToDecimal(String input, boolean inSec, boolean isHighlight) {
		String color = "";
		if (isHighlight) {
			if (Double.parseDouble(input) <= 0.1 && Double.parseDouble(input) >= 0.25) {
				color = Constants.YELLOW;
			} else if (Double.parseDouble(input) < 0.25) {
				color = Constants.RED;
			}
		}
		if (inSec)
			return ((Double.parseDouble(input) / 100) + "s" + " " + color).trim();
		return ((Double.parseDouble(input) / 100) + "" + " " + color).trim();
	}

	/**
	 * Convert In Second
	 * 
	 * @param input
	 * @return
	 */
	public String convertInSec(String input) {
		if (input == null || input.equalsIgnoreCase("null"))
			return "N/A";
		return input + "s";
	}

	/**
	 * Convert In Mili Second
	 * 
	 * @param input
	 * @param appendMS
	 * @return
	 */
	public String convertInMSec(String input, boolean appendMS, boolean isHighlight) {
		String color = "";
		if (isHighlight) {
			if ((Integer.parseInt(input) >= 100) && (Integer.parseInt(input) <= 300)) {
				color = Constants.YELLOW;
			} else if ((Integer.parseInt(input) > 300)) {
				color = Constants.RED;
			}
		}
		if (appendMS)
			return (input + "ms" + " " + color).trim();
		return (input + " " + color).trim();
	}

	/**
	 * Convert MS to Second
	 * 
	 * @param input
	 * @param appendSec
	 * @return
	 */
	public String convertMSToSec(String input, boolean appendSec, boolean isHighlight) {
		String color = "";
		if (isHighlight) {
			if ((Double.parseDouble(input) / 1000) <= 4.0 && (Double.parseDouble(input) / 1000) >= 2.5) {
				color = Constants.YELLOW;
			} else if ((Double.parseDouble(input) / 1000) > 4.0) {
				color = Constants.RED;
			}
		}
		if (input == null || input.equalsIgnoreCase("null")) {
			return "N/A";
		}
		if (appendSec)
			return ((Double.parseDouble(input) / 1000) + "s" + " " + color).trim();
		return ((Double.parseDouble(input) / 1000) + "" + " " + color).trim();
	}

	/**
	 * Fetch the Page Speed Attribute from Page Insight
	 * 
	 * @param url             - URL of the page
	 * @param title           - Title of the page
	 * @param strategy        - DESKTOP | MOBILE
	 * @param workbook        - XSSFWorkbook Object
	 * @param sheet           - XSSFSheet Object
	 * @param isMultiAPITest- To Initiate the APIDriver object if we are testing
	 *                        multi API's
	 * @return
	 */
	public PageSpeedEntity validatePageInsight(String url, String title, String strategy, XSSFWorkbook workbook,
			XSSFSheet sheet, boolean isMultiAPITest) {
		if (isMultiAPITest)
			apiDriver = RestAssured.given();
		PageSpeedEntity entity = new PageSpeedEntity(workbook, sheet);
		entity.setUrl(url);
		entity.setArticleName(title);
		HashMap<String, String> hashMap = new HashMap();
		hashMap.put(Constants.URL, entity.getUrl());
		hashMap.put(Constants.KEY, Constants.OAUTH_KEY);
		hashMap.put(Constants.STRATEGY, strategy);
		hashMap.put(Constants.CATEGORY, Constants.PERFORMANCE);
		testLogger.info("Query Params for the Request : " + hashMap);

		testLogger.info("STEP : Validate the Page Insight API for Article : " + entity.getUrl());
		Response response = apiDriver.baseUri(APIConstants.BASEURL_OF_PAGEINSIGHT).queryParams(hashMap).when()
				.get(APIConstants.PAGE_SPEED_URL);
		JsonPath object = response.jsonPath();

		String overAllPerformance = convertDecimalToPercentageForOverAllPerformance(
				object.getString(APIConstants.QUERY_FOR_OVERALL_PERFORMANCE), true, true);
		entity.setOverAllPerformance(overAllPerformance);
		testLogger.info("The Overall Performance for " + hashMap.get(Constants.STRATEGY) + " : " + overAllPerformance);

		String largestContentfulPaintInMS = convertMSToSec(object.getString(APIConstants.LARGEST_CONTENTFUL_PAINT_MS),
				true, true);
		entity.setLARGEST_CONTENTFUL_PAINT_MS(largestContentfulPaintInMS);
		testLogger.info("The LARGEST_CONTENTFUL_PAINT_MS for " + hashMap.get(Constants.STRATEGY) + " : "
				+ largestContentfulPaintInMS);

		String firstInputDelayInMS = convertInMSec(object.getString(APIConstants.FIRST_INPUT_DELAY_MS), true, true);
		entity.setFIRST_INPUT_DELAY_MS(firstInputDelayInMS);
		testLogger
				.info("The FIRST_INPUT_DELAY_MS for " + hashMap.get(Constants.STRATEGY) + " : " + firstInputDelayInMS);

		String firstContentfulPaintInMS = convertMSToSec(object.getString(APIConstants.FIRST_CONTENTFUL_PAINT_MS), true,
				false);
		entity.setFIRST_CONTENTFUL_PAINT_MS(firstContentfulPaintInMS);
		testLogger.info("The FIRST_CONTENTFUL_PAINT_MS for " + hashMap.get(Constants.STRATEGY) + " : "
				+ firstContentfulPaintInMS);

		String timeToFirstByte = convertMSToSec(object.getString(APIConstants.EXPERIMENTAL_TIME_TO_FIRST_BYTE), true,
				false);
		entity.setEXPERIMENTAL_TIME_TO_FIRST_BYTE(timeToFirstByte);
		testLogger.info(
				"The EXPERIMENTAL_TIME_TO_FIRST_BYTE for " + hashMap.get(Constants.STRATEGY) + " : " + timeToFirstByte);

		String interactionToNextPaint = convertInMSec(
				object.getString(APIConstants.EXPERIMENTAL_INTERACTION_TO_NEXT_PAINT), true, false);
		entity.setEXPERIMENTAL_INTERACTION_TO_NEXT_PAINT(interactionToNextPaint);
		testLogger.info("The EXPERIMENTAL_INTERACTION_TO_NEXT_PAINT for " + hashMap.get(Constants.STRATEGY) + " : "
				+ interactionToNextPaint);

		String cumulativeLayoutShiftScore = convertPercentileToDecimal(
				object.getString(APIConstants.CUMULATIVE_LAYOUT_SHIFT_SCORE), false, true);
		entity.setCumulativeLayoutShiftScore(cumulativeLayoutShiftScore);
		testLogger.info("The CUMULATIVE_LAYOUT_SHIFT_SCORE for " + hashMap.get(Constants.STRATEGY) + " : "
				+ cumulativeLayoutShiftScore);

		String largestContentfulPaint = convertMSToSec(object.getString(APIConstants.LARGEST_CONTENTFUL_PAINT_MS),
				false, false);
		String firstInputDelay = convertInMSec(object.getString(APIConstants.FIRST_INPUT_DELAY_MS), false, false);
		entity.setCoreWebVitalsAssessment(largestContentfulPaint, firstInputDelay, cumulativeLayoutShiftScore);

		return entity;
	}

}
