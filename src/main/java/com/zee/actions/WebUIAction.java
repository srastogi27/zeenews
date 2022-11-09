package com.zee.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class WebUIAction extends BaseTest {

	private static WebUIAction webUIAction;
	
	public static WebUIAction getInstance() {
		if(webUIAction == null)
			webUIAction = new WebUIAction();
		return webUIAction;
	}
	
	public void navigateToPage(String url) {
		waitForPageToLoad(30);
		implicitWait(30);
		driver.get(url);
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

	public void setGACallsForWeb(List<LogEntry> entries, String eventCalls) {
		JsonNode jsonNode = null;
		ArrayList<String> list = new ArrayList();
		NetworkEntity entity = new NetworkEntity();
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
					&& jsonNode.at(Constants.REQ_URL).asText().contains("/collect?") && jsonNode.at(Constants.XHR_PATH).asText().contains("XHR")) {
				
				list.add(jsonNode.at(Constants.REQ_URL).asText());
			}
		}
		entity.setQueryParamsForWeb(list);
		if(eventCalls.contains(Constants.GA_EVENT_NAME)){
            googleAnalyticCalls = new GoogleAnalyticCalls(entity.getQueryParams(Constants.T), entity.getQueryParams(Constants.DT), entity.getQueryParams(Constants.DL), entity.getQueryParams(Constants.TID), entity.getQueryParams(Constants.EA));
        }else if(eventCalls.contains(Constants.SCORECARD_EVENT_NAME)){
            scoreCardCalls = new ScoreCardCalls(entity.getQueryParams(Constants.C2), entity.getQueryParams(Constants.C7), entity.getQueryParams(Constants.C8));
        }
	}
	
	
	public void setScoreCardCallsForWeb(List<LogEntry> entries, String eventCalls) {
		JsonNode jsonNode = null;
		ArrayList<String> list = new ArrayList();
		NetworkEntity entity = new NetworkEntity();
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
					&& jsonNode.at(Constants.REQ_URL).asText().contains("c1") && jsonNode.at(Constants.IMAGE_PATH).asText().contains(Constants.IMAGE)) {
				list.add(jsonNode.at(Constants.REQ_URL).asText());
			}
		}
		entity.setQueryParamsForWeb(list);
		if(eventCalls.contains(Constants.GA_EVENT_NAME)){
            googleAnalyticCalls = new GoogleAnalyticCalls(entity.getQueryParams(Constants.T), entity.getQueryParams(Constants.DT), entity.getQueryParams(Constants.DL), entity.getQueryParams(Constants.TID), entity.getQueryParams(Constants.EA));
        }else if(eventCalls.contains(Constants.SCORECARD_EVENT_NAME)){
            scoreCardCalls = new ScoreCardCalls(entity.getQueryParams(Constants.C2), entity.getQueryParams(Constants.C7), entity.getQueryParams(Constants.C8));
        }
	}
	
	public String getTID(List<LogEntry> entries, String eventCalls) {
		String tID = "null";
		JsonNode jsonNode = null;
		ArrayList<String> list = new ArrayList();
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
					&& jsonNode.at(Constants.REQ_URL).asText().contains("/collect?") && jsonNode.at(Constants.XHR_PATH).asText().contains(Constants.XHR)) {
				
				list.add(jsonNode.at(Constants.REQ_URL).asText());
			}
		}
		if(!list.isEmpty()) {
			String url = list.get(0);
			TestLogger.getInstance().info("The URL is : " + url);
			String[] array = url.split(Constants.AMPHERCENT);
			for (int j = 0; j < array.length; j++) {
				if (array[j].contains(Constants.EQUALS) && !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[j]).find() && array[j].split(Constants.EQUALS)[0].equalsIgnoreCase(Constants.TID)) {
					tID = array[j].split(Constants.EQUALS)[1];
				}
			}
		}
		return tID;
	}
}
