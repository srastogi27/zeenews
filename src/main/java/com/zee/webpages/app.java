package com.zee.webpages;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.constants.Constants;
import com.zee.utils.TestLogger;

public class app {

	public static void main(String[] args) throws IOException {
		FileWriter fw;
		StringBuilder builder = new StringBuilder();
		JsonNode jsonNode;
		ObjectMapper objectMapper = new ObjectMapper();
		app calls = new app();
		String exe = "C:\\Users\\shivamrastogi01\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", exe);

		ChromeOptions options = new ChromeOptions();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		options.setCapability("goog:loggingPrefs", logPrefs);

		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get("https://zeenews.india.com");

		try {
			List<LogEntry> list = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
			for (LogEntry entry : list) {
				jsonNode = objectMapper.readTree(entry.getMessage());
				if (jsonNode.findValue(Constants.METHOD).asText().contains(Constants.NETWORK_REQ_SENT)) {
					if (!jsonNode.at(Constants.REQ_URL).asText().isEmpty()) {
						if (jsonNode.at(Constants.REQ_URL).asText().contains("https://www.google-analytics.com")
								&& jsonNode.at(Constants.REQ_URL).asText().contains("/collect?") || jsonNode.at(Constants.REQ_URL).asText().contains("c1")) {
							builder.append(entry.getMessage());
							builder.append("\n");
							System.out.println(entry.getMessage());
						}
					}
				}
			}
		} finally {
			fw = new FileWriter("file.txt");
			fw.write(builder.toString());
			fw.close();
		}

		driver.quit();

	}

	public void setQueryParamsForWeb(ArrayList<String> list) {
		HashMap<String, String> queryParams = new HashMap<String, String>();
		String temp = null;
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				TestLogger.getInstance().info("The URL is : " + list.get(i));
				String[] array = list.get(i).split(Constants.AMPHERCENT);
				if (i == 0) {
					temp = array[i].split(Constants.REGEX_QUESTION)[1];
					queryParams.put(temp.split(Constants.EQUALS)[0], temp.split(Constants.EQUALS)[1]);
				}
				for (int j = 0; j < array.length; j++) {
					if (array[j].contains(Constants.EQUALS)
							&& !Pattern.compile(Constants.REGEX_ENDS_WITH_EQUAL).matcher(array[j]).find()) {
						if (!array[j].split(Constants.EQUALS)[0].equalsIgnoreCase(Constants.TID)) {
							queryParams.put(array[j].split(Constants.EQUALS)[0], array[j].split(Constants.EQUALS)[1]);
						}
					}
				}
			}
		}
	}

}
