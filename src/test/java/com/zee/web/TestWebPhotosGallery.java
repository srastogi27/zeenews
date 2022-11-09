package com.zee.web;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.zee.actions.WebAction;
import com.zee.webpages.HomePage;
import com.zee.webpages.PhotoPage;

public class TestWebPhotosGallery extends WebAction {
	
	@Test(priority = 1, testName = "Validate, the total count of images are showing correct, after open the collection of Images.")
	@Parameters("URL")
	public void test01_ValidateTotalCountOfImageAtPhotoPage(@Optional String url) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		HomePage homePage = new HomePage();
		PhotoPage photoPage = new PhotoPage();
		
		testLogger.info("STEP : Navigate to the Page");
		navigateToPage(url);
		
		testLogger.info("STEP: Click on Photo Menu.");
		homePage.clickOnPhotoSection();
		photoPage.waitForImageToLoad();
		handleBannerAdvertise();
		
		testLogger.log("Validate, Title of the Image : " + photoPage.getTitleOfImage());
		softAssert.assertTrue(photoPage.getTitleOfImage().equals("Photos"), "Failed: Page title is not as expected +" + photoPage.getTitleOfImage() + "");
		
		int totalCountFromOutside = photoPage.getCountOfImagesPresentInsidePhotoPageImage();
		
		testLogger.info("STEP: Select and Open the First Image.");
		photoPage.selectTheFirstPictureAppearOnPhotoSectionPage();
		
		int totalCountFromInside = photoPage.getTotalNumberOfImagesPresentAfterClickOnImageFromPhotoSectionPage();
		
		testLogger.info("STEP: Validate, Total count of Outside Image are : " + totalCountFromOutside + " and Inside Total Images are : " + totalCountFromInside);		
		softAssert.assertEquals(totalCountFromOutside, totalCountFromInside, "Validated, the total count of outsize Images are equal to Total Count of Inside Images.");

		softAssert.assertAll();
	}
}
