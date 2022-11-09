package com.zee.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.dockerjava.api.model.Config;
import com.google.common.collect.Table.Cell;
import com.google.common.io.Files;
import com.zee.constants.APIConstants;
import com.zee.constants.Constants;
import com.zee.entities.PageSpeedEntity;
import com.zee.utils.GenericUtility;
import com.zee.utils.TestLogger;

import io.restassured.RestAssured;
import io.restassured.config.ParamConfig;
import io.restassured.config.ParamConfig.UpdateStrategy;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Appli {

	public static void main(String[] args) throws IOException {
		String color = "";
		System.out.println(color.isEmpty());

		RequestSpecification apiDriver = RestAssured.given();

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(Constants.URL, "https://zeenews.india.com/latest-news");
		hashMap.put(Constants.KEY, Constants.OAUTH_KEY);
		hashMap.put(Constants.STRATEGY, "DESKTOP");
		hashMap.put(Constants.CATEGORY, Constants.PERFORMANCE);
		TestLogger.getInstance().info("Query Params for the Request : " + hashMap);

		TestLogger.getInstance()
				.info("STEP : Validate the Page Insight API for Article : " + "https://zeenews.india.com/latest-news");
		Response response = apiDriver.log().uri().baseUri(APIConstants.BASEURL_OF_PAGEINSIGHT).queryParams(hashMap)
				.when().get(APIConstants.PAGE_SPEED_URL);
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());

//		apiDriver = RestAssured.given();
		hashMap = new HashMap<String, String>();
		hashMap.put(Constants.URL, "https://zeenews.india.com/latest-news");
		hashMap.put(Constants.KEY, Constants.OAUTH_KEY);
		hashMap.put(Constants.STRATEGY, "DESKTOP");
		hashMap.put(Constants.CATEGORY, Constants.PERFORMANCE);
		TestLogger.getInstance().info("Query Params for the Request : " + hashMap);

		TestLogger.getInstance()
				.info("STEP : Validate the Page Insight API for Article : " + "https://zeenews.india.com/latest-news");
		response = apiDriver
				.config(RestAssuredConfig.config()
						.paramConfig(ParamConfig.paramConfig().queryParamsUpdateStrategy(UpdateStrategy.REPLACE)))
				.queryParam("url", "https://zeenews.india.com").log().uri().baseUri(APIConstants.BASEURL_OF_PAGEINSIGHT)
				.when().get(APIConstants.PAGE_SPEED_URL);
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());

	}

}
