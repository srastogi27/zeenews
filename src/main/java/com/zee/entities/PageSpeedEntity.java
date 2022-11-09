package com.zee.entities;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zee.utils.GenericUtility;

public class PageSpeedEntity {

	private String url;
	private String articleName;
	private String overAllPerformance;
	private String largestContentfulPaintMS;
	private String firstInputDelayMS;
	private String firstContentfulPaintMS;
	private String experimentalTimeToFirstByte;
	private String experimentalInteractionToNextPaint;
	private String cumulativeLayoutShiftScore;
	private String coreWebVitalsAssessment;

	public PageSpeedEntity(XSSFWorkbook workbook, XSSFSheet sheet){
		GenericUtility.getGenericUtility().createXSLHeadersForPageSpeed(workbook, sheet);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getOverAllPerformance() {
		return overAllPerformance;
	}

	public void setOverAllPerformance(String overAllPerformance) {
		this.overAllPerformance = overAllPerformance;
	}

	public String getLARGEST_CONTENTFUL_PAINT_MS() {
		return largestContentfulPaintMS;
	}

	public void setLARGEST_CONTENTFUL_PAINT_MS(String lARGEST_CONTENTFUL_PAINT_MS) {
		largestContentfulPaintMS = lARGEST_CONTENTFUL_PAINT_MS;
	}

	public String getFIRST_INPUT_DELAY_MS() {
		return firstInputDelayMS;
	}

	public void setFIRST_INPUT_DELAY_MS(String fIRST_INPUT_DELAY_MS) {
		firstInputDelayMS = fIRST_INPUT_DELAY_MS;
	}

	public String getFIRST_CONTENTFUL_PAINT_MS() {
		return firstContentfulPaintMS;
	}

	public void setFIRST_CONTENTFUL_PAINT_MS(String fIRST_CONTENTFUL_PAINT_MS) {
		firstContentfulPaintMS = fIRST_CONTENTFUL_PAINT_MS;
	}

	public String getEXPERIMENTAL_TIME_TO_FIRST_BYTE() {
		return experimentalTimeToFirstByte;
	}

	public void setEXPERIMENTAL_TIME_TO_FIRST_BYTE(String eXPERIMENTAL_TIME_TO_FIRST_BYTE) {
		experimentalTimeToFirstByte = eXPERIMENTAL_TIME_TO_FIRST_BYTE;
	}

	public String getEXPERIMENTAL_INTERACTION_TO_NEXT_PAINT() {
		return experimentalInteractionToNextPaint;
	}

	public void setEXPERIMENTAL_INTERACTION_TO_NEXT_PAINT(String eXPERIMENTAL_INTERACTION_TO_NEXT_PAINT) {
		experimentalInteractionToNextPaint = eXPERIMENTAL_INTERACTION_TO_NEXT_PAINT;
	}

	public String getCUMULATIVE_LAYOUT_SHIFT_SCORE() {
		return cumulativeLayoutShiftScore;
	}

	public void setCumulativeLayoutShiftScore(String cUMULATIVE_LAYOUT_SHIFT_SCORE) {
		cumulativeLayoutShiftScore = cUMULATIVE_LAYOUT_SHIFT_SCORE;
	}

	public String getCoreWebVitalsAssessment() {
		return coreWebVitalsAssessment;
	}

	public String setCoreWebVitalsAssessment(String largestContentfulPaint, String firstInputDelay, String cumulativeLayoutShiftScore) {
		coreWebVitalsAssessment = "Failed";
		/*if((Integer.parseInt(firstInputDelay) <= 100) && Double.parseDouble(largestContentfulPaint) <= 2.5 && Double.parseDouble(cumulativeLayoutShiftScore) <= 0.1) {
			coreWebVitalsAssessment = "Passed";
		}*/
		if((Integer.parseInt(firstInputDelay) <= 100) && (Double.parseDouble(largestContentfulPaint) <= 2.5)) {
			coreWebVitalsAssessment = "Passed";
		}
		return coreWebVitalsAssessment;
	}
	
	
}
