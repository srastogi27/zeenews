package com.zee.manager;

import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import com.zee.utils.TestLogger;

public class WebDriverManager {

	private WebDriver driver;
    private static WebDriverManager webDriverManager;
    
    public static WebDriverManager getWebDriverManager(){
        if(webDriverManager == null)
        	webDriverManager = new WebDriverManager();
        return webDriverManager;
    }
    
    private ChromeOptions getChromeOptions(String browserName) {
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--disable-notifications");
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		options.setCapability("goog:loggingPrefs", logPrefs);
        return options;
    }
    
    public WebDriver initDriver(String browserName){
//    	System.setProperty("webdriver.chrome.driver", GenericUtility.getGenericUtility().setChromeExePathForWindow());
    	io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
    	try {
    		if(browserName.equalsIgnoreCase("CHROME"))
    			driver = new ChromeDriver(getChromeOptions(browserName));
		} catch (Exception e) {
			TestLogger.getInstance().error(e.getMessage());
		}
    	driver.manage().window().maximize();
        return driver;
    }
    
    public WebDriver getDriver(){
        return driver;
    }
}
