package RMSV2.RMSCustomerPortal;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pageObjects.homePage;
import pageObjects.previewPage;
import pageObjects.providersPage;
import pageObjects.templatePage;

public class ProviderRequestMethods {
	
	public static providersPage provider_page;
	public static previewPage preview_page;
	public static templatePage temp_page;
	public static homePage home_page;
	
	public void singleLocationProvider(WebDriver driver, String facilityName, String state, String city, String rec_template, String img_template,String  path_template, String LocationType)
			throws InterruptedException {
		temp_page=new templatePage(driver);
		preview_page = new previewPage(driver);
		home_page = new homePage(driver);
		provider_page =new providersPage(driver);
		provider_page.getRecentProviders().click();
		RecentProvidersList: while (true) {
			if (provider_page.getRecentProvidersList().isDisplayed()) {
				System.out.println("Waiting fro the recent providers");
				break RecentProvidersList;
			} else {
				Thread.sleep(1000);
			}
		}

		List<WebElement> providers_list = provider_page.getRecentProvidersName();
		//int listcount = providers_list.size();
			facilityloop:while(true) {	
				for(WebElement option:providers_list) {
					if(option.getText().equalsIgnoreCase(facilityName)) {
						option.click();
						templateloop:while(true) {
							if(provider_page.ManageTemplates().isDisplayed()) {
								if(LocationType=="RecordsLocation") {
									recordtemplate(driver, rec_template);
								}else if(LocationType=="ImageLocation"){
									System.out.println("test");
									imagetemplate(driver,img_template);
								}else if(LocationType=="PathologyLocation"){
									pathologytemplate(driver,path_template);	
								}
								break templateloop;
							}else {
								Thread.sleep(1000);
							}
					}	
					 break	facilityloop;	
			}

			}
				
			}
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    WebElement Element = temp_page.footer();

		    js.executeScript("arguments[0].scrollIntoView();", Element);
		
		
		String note="test notes";
		temp_page.notesarea().click();
		temp_page.notesarea().sendKeys(note);
		temp_page.NextButton().click();

		retrievalproviders:while(true) {
			if(provider_page.getSelectedProviders().size()>0) {
				provider_page.NextButton().click();
				break retrievalproviders;
			}else {
				Thread.sleep(1000);
			}
		}

		js.executeScript("arguments[0].scrollIntoView();", preview_page.getFooter());

		submitrequest: while (true) {
			if (preview_page.getSubmitRequest().isDisplayed()) {

				preview_page.getSubmitRequest().click();
				System.out.println("Reports For Indexing request is submitted");
				break submitrequest;
			} else {
				Thread.sleep(1000);
			}
		}

		donerequest: while (true) {
			if (preview_page.getDone().isDisplayed()) {
				preview_page.getDone().click();
				break donerequest;
			} else {
				Thread.sleep(1000);
			}
		}

		dashboard: while (true) {
			if (home_page.RecordsTabPage().isDisplayed()) {
				break dashboard;
			} else {
				Thread.sleep(1000);
			}
		}

	}
	
	public void recordtemplate(WebDriver driver, String rec_template) throws InterruptedException {
	Select rectemplate=new Select(temp_page.RecTemplateOptions());
	rectemplate.selectByVisibleText(rec_template);
	temp_page.ApplyRecordsTemlate().click();
	templateload:while(true) {
		if(temp_page.getRecordItems().size()>0) {
			System.out.println("Records template items loaded");

			break templateload;
		}else {
			Thread.sleep(2000);
		}
	}	
	}
	
	public void imagetemplate(WebDriver driver, String img_template) throws InterruptedException {
		
	
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement Element = temp_page.ApplyImagesTemlate();
    js.executeScript("arguments[0].scrollIntoView();", Element);
    Select imgtemplate=new Select(temp_page.ImgTemplateOptions());
	imgtemplate.selectByVisibleText(img_template);
	temp_page.ApplyImagesTemlate().click();
	templateload:while(true) {
		if(temp_page.getImageItems().size()>0) {
			System.out.println("image template items loaded");

			break templateload;
		}else {
			Thread.sleep(2000);
		}
	}
	}
	
	public void pathologytemplate(WebDriver driver, String path_template) throws InterruptedException {

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebElement Element = temp_page.footer();

	    js.executeScript("arguments[0].scrollIntoView();", Element);

	Select rectemplate=new Select(temp_page.PathTemplateOptions());
	rectemplate.selectByVisibleText(path_template);
	temp_page.ApplyPathologyTemlate().click();
	templateload:while(true) {
		if(temp_page.getPathologyItems().size()>0) {
			System.out.println("Pathology template items loaded");

			break templateload;
		}else {
			Thread.sleep(2000);
		}
	}
	

	
	}


}
