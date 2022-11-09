package com.zee.actions;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.logging.LogEntry;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.zee.base.BaseTest;
import com.zee.constants.Constants;
import com.zee.entities.GoogleAnalyticCalls;
import com.zee.entities.ScoreCardCalls;

public class MobUIAction extends BaseTest{
	
	private static final String smallBannerAdvForMob = "(//a[contains(@href,'adclick.g')])[1]/parent::*/following-sibling::a[@href='#']";
	private static MobUIAction action;
	
	public static MobUIAction getInstance() {
		if(action == null)
			action = new MobUIAction();
		return action;
	}
	
	public void navigateToPage(String url) {
		driver.get(url);
		waitForPageLoad(driver);
		handleBannerAdvertise();
		pause(4);
	}

	@Override
	public void handleBannerAdvertise() {
		if (isPresent(smallBannerAdvForMob))
			clickByJS(smallBannerAdvForMob);
		pause(6);
	}
	
	public boolean validateTitleOfPage(String expectedTitle) {
		return driver.getTitle().equalsIgnoreCase(expectedTitle);
	}
	
    public void UserPlayTheVideoOnMSite() throws FindFailed, InterruptedException {
        implicitWait(10);
        Screen sc = new Screen();
        Pattern zeenewsIconForPlay = new Pattern(getUserDir() + Constants.IMAGES + "useForPlayButton.png");
        sc.wait(zeenewsIconForPlay, 200);
        sc.click();
        Thread.sleep(1000);
    }

    public void UserPauseTheVideoOnMSite() throws FindFailed, InterruptedException {
        implicitWait(10);
        Screen sc = new Screen();
        Pattern zeenewsIconForPlay = new Pattern(getUserDir() + Constants.IMAGES + "useForPlayButton.png");
        sc.wait(zeenewsIconForPlay, 2);
        Thread.sleep(1000);
        sc.click();
        Thread.sleep(1000);
    }

    public void UserResumeTheVideoOnMSite() throws FindFailed, InterruptedException {
        Screen sc = new Screen();
        Pattern zeenewsIconForPlay = new Pattern(getUserDir() + Constants.IMAGES + "useForPlayButton.png");
        sc.wait(zeenewsIconForPlay, 200);
        Thread.sleep(1000);
        sc.click();
        Thread.sleep(1000);
    }

    public void userHandleNotificationPopup() {
        try {
            Screen sc = new Screen();
            Thread.sleep(1000);
            implicitWait(10);
            Pattern allowNotification = new Pattern(getUserDir() + Constants.IMAGES + "allow_Notification_popUp.png");
            if (sc.exists(allowNotification) != null) {
                sc.wait(allowNotification, 200);
                sc.click();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in allow on popup");
        }
    }

    public void userHandleskipAddOnVideo() {
        try {
            Screen sc = new Screen();
            implicitWait(10);
            Pattern skipButton = new Pattern(getUserDir() + Constants.IMAGES + "skipADD.png");
            if (sc.exists(skipButton) != null) {
                sc.wait(skipButton, 2);
                Thread.sleep(1000);
                sc.click();
            } else
                Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in skip button");
        }
    }

    public void userHandleAddOnVideo() {
        try {
            Screen sc = new Screen();
            Pattern closeAdd = new Pattern(getUserDir() + Constants.IMAGES + "closeAddOnVideoPlayer.png");
            if (sc.exists(closeAdd) != null) {
                sc.wait(closeAdd, 2);
                Thread.sleep(1000);
                sc.click();
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in Handle Add On Video");
        }
    }
    
    /**
	 * 
	 * @param entries:      LogEntry List
	 * @param eventCalls:   google-analytic | scorecard
	 * @param urlAttribute: collect | c1
	 * @param type:         XHR | IMAGE
	 * @return
	 */
	public void setRequestParamForGAorScoreCard(List<LogEntry> entries, String eventCalls, String urlAttribute, String type, String action, boolean isAMP) {
		List<String> list = getListOfNetworkURLs(entries, eventCalls, urlAttribute, type, action);
		Map<String, String> map = setRequestParams(list);
		if(eventCalls.contains(Constants.GA_EVENT_NAME)) {
			googleAnalyticCalls = new GoogleAnalyticCalls(map.get(Constants.T), map.get(Constants.DT), map.get(Constants.DL), map.get(Constants.TID), null);
		}else if(eventCalls.contains(Constants.SCORECARD_EVENT_NAME) && isAMP) {
			scoreCardCalls = new ScoreCardCalls(map.get(Constants.C2), map.get(Constants.C7), map.get(Constants.C8), map.get(Constants.C7_AMP));
		}else {
			scoreCardCalls = new ScoreCardCalls(map.get(Constants.C2), map.get(Constants.C7), map.get(Constants.C8), null);
		}
	}
	
	public String getCurrentPageURL() {
		return driver.getCurrentUrl();
	}
}
