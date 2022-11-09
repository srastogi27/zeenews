package com.zee.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.openqa.selenium.logging.LogEntry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.base.BaseTest;
import com.zee.constants.Constants;
import com.zee.entities.GoogleAnalyticCalls;
import com.zee.entities.NetworkEntity;
import com.zee.entities.ScoreCardCalls;
import com.zee.utils.TestLogger;

public class WebAction extends BaseTest {

	private static WebAction webUIAction;

	public static WebAction getInstance() {
		if (webUIAction == null)
			webUIAction = new WebAction();
		return webUIAction;
	}

	public void navigateToPage(String url) {
		driver.get(url);
		waitForPageLoad(driver);
		handleBannerAdvertise();
		pause(4);
	}

	public boolean validateTitleOfPage(String expectedTitle) {
		return driver.getTitle().equalsIgnoreCase(expectedTitle);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getCurrentPageURL() {
		return driver.getCurrentUrl();
	}

	public String getTID(List<LogEntry> entries, String eventCalls) {
		String tID = "null";
		JsonNode jsonNode = null;
		ArrayList<String> list = new ArrayList<>();
		ObjectMapper objectMapper = genericUtility.getObjectMapper();
		for (LogEntry entry : entries) {
			try {
				jsonNode = objectMapper.readTree(entry.getMessage());
			} catch (IOException e) {
				testLogger.error(e.getMessage());
			}
			if (jsonNode.findValue(Constants.METHOD).asText().contains(Constants.NETWORK_REQ_SENT)
					&& !jsonNode.at(Constants.REQ_URL).asText().isEmpty()
					&& jsonNode.at(Constants.REQ_URL).asText().contains(eventCalls)
					&& jsonNode.at(Constants.REQ_URL).asText().contains("/collect?")
					&& jsonNode.at(Constants.XHR_PATH).asText().contains(Constants.XHR)) {

				list.add(jsonNode.at(Constants.REQ_URL).asText());
			}
		}
		if (!list.isEmpty()) {
			String url = list.get(0);
			TestLogger.getInstance().info("The URL is : " + url);
			String[] array = url.split(Constants.AMPHERCENT);
			for (int j = 0; j < array.length; j++) {
				if (array[j].contains(Constants.EQUALS)
						&& !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[j]).find()
						&& array[j].split(Constants.EQUALS)[0].equalsIgnoreCase(Constants.TID)) {
					tID = array[j].split(Constants.EQUALS)[1];
				}
			}
		}
		return tID;
	}

	public String getTIDFromGAForWeb(List<LogEntry> entries, String eventCalls, String urlAttribute, String type, String action) {
		String tid = null;
		List<String> list = getListOfNetworkURLs(entries, eventCalls, urlAttribute, type, action);
		if (!list.isEmpty()) {
			String url = list.get(0);
			testLogger.info("The Request URL is : " + url);
			String[] array = url.split(Constants.AMPHERCENT);
			for (String variable : array) {
				if (isParameterExistInURL(variable, Constants.TID)) {
					tid = variable.split(Constants.EQUALS)[1];
				}
			}

		} else {
			testLogger.error(
					"Request URL not found with Query : " + eventCalls + " | | " + urlAttribute + " | | " + type);
		}
		return tid;
	}

	/**
	 * 
	 * @param entries:      LogEntry List
	 * @param eventCalls:   google-analytic | scorecard
	 * @param urlAttribute: collect | c1
	 * @param type:         XHR | IMAGE
	 * @return
	 */
	public void setRequestParamForGAorScoreCard(List<LogEntry> entries, String eventCalls, String urlAttribute, String type, String action) {
		List<String> list = getListOfNetworkURLs(entries, eventCalls, urlAttribute, type, action);
		Map<String, String> map = setRequestParams(list, Constants.TID);
		if(eventCalls.contains(Constants.GA_EVENT_NAME)) {
			googleAnalyticCalls = new GoogleAnalyticCalls(map.get(Constants.T),	map.get(Constants.DT), map.get(Constants.DL), map.get(Constants.EA));
		}else if(eventCalls.contains(Constants.SCORECARD_EVENT_NAME)) {
			scoreCardCalls = new ScoreCardCalls(map.get(Constants.C2), map.get(Constants.C7), map.get(Constants.C8));
		}
	}
}
