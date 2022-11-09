package com.zee.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.constants.Constants;
import com.zee.entities.PageSpeedEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.stringtemplate.v4.compiler.CodeGenerator.conditional_return;

public class GenericUtility {

    Properties properties;
    FileReader fileReader;
    private String chromeExecutableForMac;
    private String chromeExecutableForWindow;
    private String userJenkinsHome;
    private static GenericUtility genericUtility;
    private ObjectMapper objectMapper;
    XSSFWorkbook workbook;

    private GenericUtility(){
        readProperties();
        objectMapper = new ObjectMapper();
    }

    public static GenericUtility getGenericUtility(){
        if(genericUtility == null)
            genericUtility = new GenericUtility();
        return genericUtility;
    }

    public void readProperties(){
        try {
            String path = System.getProperty("user.dir") + Constants.CONFIG_FOLDER + Constants.PROP;
            TestLogger.getInstance().info("The Configuration file found at : " + path);
            fileReader = new FileReader(new File(path));
        } catch (FileNotFoundException e) {
        	TestLogger.getInstance().error("File not found, " + e.getMessage());
        }
        properties = new Properties();
        try {
            properties.load(fileReader);
        } catch (IOException e) {
        	TestLogger.getInstance().error(e.getMessage());
        }
        setChromeExePathForMac(System.getProperty("user.dir") + properties.getProperty("CHROMEDRIVER_EXE_MAC"));
        setChromeExePathForWindow(System.getProperty("user.dir") + properties.getProperty("CHROMEDRIVER_EXE_WINDOW"));
        setUserJenkinsHome(properties.getProperty("USER_JENKINS_WORKSPACE"));
    }
    
    public void setUserJenkinsHome(String path) {
    	this.userJenkinsHome = path;
    }

    public String getUserJenkinsHome() {
    	return this.userJenkinsHome;
    }
    
    public void setChromeExePathForMac(String executablePath){
        this.chromeExecutableForMac = executablePath;
    }
    
    public void setChromeExePathForWindow(String executablePath){
        this.chromeExecutableForWindow = executablePath;
    }

    public String getChromeExecutableMac(){
       return this.chromeExecutableForMac;
    }
    
    public String getChromeExePathForWindow(){
        return this.chromeExecutableForWindow;
     }

    public ObjectMapper getObjectMapper(){
        return objectMapper;
    }
    
    public XSSFWorkbook getWorkbook() {
    	return new XSSFWorkbook();
    }
    
    public XSSFSheet getXSSFSheet(XSSFWorkbook workbook, String sheetName) {
    	return workbook.createSheet(sheetName);
    }
    
    public XSSFCellStyle setCellStyle(XSSFWorkbook workbook, short fg) {
    	XSSFCellStyle cellStyle = workbook.createCellStyle();
    	cellStyle.setBorderTop((short) 6);
    	cellStyle.setBorderBottom((short) 1);
    	cellStyle.setFillForegroundColor(fg);  
    	cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	XSSFFont font = workbook.createFont();
    	font.setFontHeightInPoints((short) 13);
    	font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    	cellStyle.setFont(font);
    	return cellStyle;
    }
    
    public XSSFCellStyle setCellStyleForRow(XSSFWorkbook workbook, short fg) {
    	XSSFCellStyle cellStyle = workbook.createCellStyle();
    	cellStyle.setFillForegroundColor(fg);  
    	cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	XSSFFont font = workbook.createFont();
    	font.setFontHeightInPoints((short) 12);
    	font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    	cellStyle.setFont(font);
    	return cellStyle;
    }
    
    public void createXSLHeadersForPageSpeed(XSSFWorkbook workbook, XSSFSheet sheet) {
    	Row row = sheet.createRow(0);
    	setCellStyleAndData(0, "Serial No", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(1, "Article URL", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(2, "Article Name", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(3, "OVERALL_PERFORMANCE", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(4, "Core Web Vitals Assessment", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(5, "LARGEST_CONTENTFUL_PAINT", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(6, "FIRST_INPUT_DELAY", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(7, "CUMULATIVE_LAYOUT_SHIFT_SCORE", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(8, "FIRST_CONTENTFUL_PAINT", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(9, "EXPERIMENTAL_TIME_TO_FIRST_BYTE", row, workbook, IndexedColors.YELLOW.getIndex());
    	setCellStyleAndData(10, "EXPERIMENTAL_INTERACTION_TO_NEXT_PAINT", row, workbook, IndexedColors.YELLOW.getIndex());
    }
    
    private void setCellStyleAndData(int cellNo, String cellName, Row row, XSSFWorkbook workbook, short fg) {
    	Cell cell = row.createCell(cellNo);
    	cell.setCellValue(cellName);
    	cell.setCellStyle(setCellStyle(workbook, fg));
    }
    
    public void writeDataInWorkbook(XSSFWorkbook workbook, XSSFSheet sheet, List<PageSpeedEntity> entity, String excelName) {
    	Row row;
    	for(int i = 0; i < entity.size(); i ++) {
    		row = sheet.createRow(i + 1);
    		Cell cell = row.createCell(0);
    		cell.setCellValue(i + 1 + "");
    		
    		Cell cell1 = row.createCell(1);
    		cell1.setCellValue(entity.get(i).getUrl());
    		
    		Cell cell2 = row.createCell(2);
    		cell2.setCellValue(entity.get(i).getArticleName());
    		
    		Cell cell3 = row.createCell(3);
    		cell3.setCellValue(entity.get(i).getOverAllPerformance());
    		setCellValueWithHighter(cell3, entity.get(i).getOverAllPerformance(), workbook);
    		
    		Cell cell4 = row.createCell(4);
    		if(entity.get(i).getCoreWebVitalsAssessment().equalsIgnoreCase(Constants.PASSED)) {
    			cell4.setCellValue(entity.get(i).getCoreWebVitalsAssessment());
    			cell4.setCellStyle(setCellStyleForRow(workbook, IndexedColors.GREEN.getIndex()));
    		}else {
    			cell4.setCellValue(entity.get(i).getCoreWebVitalsAssessment());
    			cell4.setCellStyle(setCellStyleForRow(workbook, IndexedColors.RED.getIndex()));
    		}
    		
    		Cell cell5 = row.createCell(5);
    		cell5.setCellValue(entity.get(i).getLARGEST_CONTENTFUL_PAINT_MS());
    		setCellValueWithHighter(cell5, entity.get(i).getLARGEST_CONTENTFUL_PAINT_MS(), workbook);
    		
    		Cell cell6 = row.createCell(6);
    		cell6.setCellValue(entity.get(i).getFIRST_INPUT_DELAY_MS());
    		setCellValueWithHighter(cell6, entity.get(i).getFIRST_INPUT_DELAY_MS(), workbook);
    		
    		Cell cell7 = row.createCell(7);
    		cell7.setCellValue(entity.get(i).getCUMULATIVE_LAYOUT_SHIFT_SCORE());
    		setCellValueWithHighter(cell7, entity.get(i).getCUMULATIVE_LAYOUT_SHIFT_SCORE(), workbook);
    		
    		Cell cell8 = row.createCell(8);
    		cell8.setCellValue(entity.get(i).getFIRST_CONTENTFUL_PAINT_MS());
    		
    		Cell cell9 = row.createCell(9);
    		cell9.setCellValue(entity.get(i).getEXPERIMENTAL_TIME_TO_FIRST_BYTE());
    		
    		Cell cell10 = row.createCell(10);
    		cell10.setCellValue(entity.get(i).getEXPERIMENTAL_INTERACTION_TO_NEXT_PAINT());
    	}
    	FileOutputStream out = null;
    	FileOutputStream jenkinOut = null;
		try {
			out = new FileOutputStream(new File("pageSpeedOutput/" + getFileNameWithTimeStamp(excelName)));
			jenkinOut = new FileOutputStream(new File(getUserJenkinsHome() + "\\" + getFileNameWithTimeStamp(excelName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook.write(out);
			workbook.write(jenkinOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
			jenkinOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(excelName + " written successfully on disk.");
    }
    
    private void setCellValueWithHighter(Cell cell, String input, XSSFWorkbook workbook) {
    	if(input.split(" ").length == 2) {
    		cell.setCellValue(input.split(" ")[0]);
    		if(input.split(" ")[1].equalsIgnoreCase(Constants.YELLOW)) {
    			cell.setCellStyle(setCellStyleForRow(workbook, IndexedColors.YELLOW.getIndex()));
    		}else if(input.split(" ")[1].equalsIgnoreCase(Constants.ORANGE)){
    			cell.setCellStyle(setCellStyleForRow(workbook, IndexedColors.LIGHT_ORANGE.getIndex()));
    		}else {
    			cell.setCellStyle(setCellStyleForRow(workbook, IndexedColors.RED.getIndex()));
    		}
    	}else {
    		cell.setCellValue(input.split(" ")[0]);
    	}
    }
    
    private String getFileNameWithTimeStamp(String fileName) {
    	StringBuilder builder = new StringBuilder();
    	if(fileName.contains(Constants.DOT)) {
    		builder.append(fileName.split("[.]")[0] + " - ");
//    		builder.append(new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date()));
    		builder.append("." + fileName.split("[.]")[1]);
    	}
    	return builder.toString();
    }
}
