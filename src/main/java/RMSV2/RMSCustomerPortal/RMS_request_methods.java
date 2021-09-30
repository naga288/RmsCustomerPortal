package RMSV2.RMSCustomerPortal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pageObjects.homePage;
import pageObjects.patientDigestPage;
import pageObjects.patientProfilePage;
import pageObjects.previewPage;
import pageObjects.providersPage;
import pageObjects.retreivalOptionsPage;
import pageObjects.uploadFilesPage;

public class RMS_request_methods {
	public static String firstName;
	public static String lastName;
	public static String PatientName;
	public static homePage home_page;
	public static patientProfilePage profile_page;
	public static retreivalOptionsPage retrieval_page;
	public static uploadFilesPage upload_files;
	public static providersPage provider_page;
	public static previewPage preview_page;
	public static patientDigestPage patient_digest;
	public static String userdir = System.getProperty("user.dir");

	public String patientdemographics(WebDriver driver, String firstName, String SSN, String dob, String streetAdd,
			String city, String postalCode, String phnum) throws InterruptedException, IOException {
		RMS_request_methods.firstName = firstName;
		home_page = new homePage(driver);
		retrieval_page = new retreivalOptionsPage(driver);
		profile_page = new patientProfilePage(driver);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

		// get current date time with Date()
		Date date = new Date();

		// Now format the date
		String date1 = dateFormat.format(date);
		lastName = date1;
		System.out.println(home_page.dahsboard().isDisplayed());
		home_page.addpatient().click();
		newpatientblock: while (true) {
			if (profile_page.demographics().isDisplayed()) {
				System.out.println("New patient request tab is opened");
				break newpatientblock;
			} else {
//waits.WaitForElement(driver, element);
				Thread.sleep(1000);
			}
		}

		System.out.println("Checking for existing  Patient profiles");
		profile_page.firstname().sendKeys(firstName);
		profile_page.lastname().sendKeys(lastName);
		profile_page.checkextPatient().click();

		Patientdetailsblock: while (true) {
			// String Reconciliation="//div/h3[contains(text(),'Patient Reconciliation')]";
			if (profile_page.PatientReconciliation().isDisplayed()) {
				System.out.println("patient profiles tab is opened");
				break Patientdetailsblock;
			} else {
				// waits.WaitForElement(driver, Reconciliation);
				Thread.sleep(1000);
			}
		}
		profile_page.createpatient().click();
		requestblock: while (true) {
			if (profile_page.newpatient().isDisplayed()) {
				System.out.println("Creating a new patient profile");
				break requestblock;
			} else {
				Thread.sleep(1000);
			}
		}

		Thread.sleep(3000);
		profile_page.SSN().click();
		profile_page.SSN().sendKeys(SSN);
		profile_page.DOB().sendKeys(dob);
		profile_page.Street1().sendKeys(streetAdd);
		profile_page.city().sendKeys(city);
		Select patcity = new Select(profile_page.state());
		patcity.selectByVisibleText(city);
		profile_page.postalcode().sendKeys(postalCode);

		profile_page.phonenumber().click();
		profile_page.phonenumber().sendKeys(phnum);

		Select dept = new Select(profile_page.department());
		dept.selectByVisibleText("Dept A");
		profile_page.next().click();
		List<WebElement> errelements = profile_page.errors();

		for (int i = 0; i < errelements.size(); i++) {
			if (errelements.get(i).isDisplayed()) {
				System.out.println(errelements.get(i).getText());
			}
		}

		if (errelements.isEmpty()) {
			screen2: while (true) {
				// String retrievalOptions="//*[@id='retrievalOptions']";
				if (retrieval_page.CHOOSERETRIEVALOPTIONS().isDisplayed()) {
					System.out.println(retrieval_page.CHOOSERETRIEVALOPTIONS().getText() + " Screen is displayed");
					break screen2;
				} else {
//waits.WaitForElement(driver, retrievalOptions);
					Thread.sleep(1000);
				}
			}
		}
		return lastName;
	}

	public void chooseRetrievalOptions(WebDriver driver, String NeedByDate, String RecordsNeededFor,
			String AuthorizingPhysician, String PurposeOfRequest) throws InterruptedException {

		retrieval_page.getNeedByDate().sendKeys(NeedByDate);

		Select rec_need = new Select(retrieval_page.NeedFor());
		rec_need.selectByVisibleText(RecordsNeededFor);

		retrieval_page.AuthPhy_txt().sendKeys(AuthorizingPhysician);

		Select Purpose_Of_Request = new Select(retrieval_page.PurposeOfRequest());
		Purpose_Of_Request.selectByVisibleText(PurposeOfRequest);

		upload_files = retrieval_page.Next();
		List<WebElement> errelements = retrieval_page.Errelements();

		for (int i = 0; i < errelements.size(); i++) {
			if (errelements.get(i).isDisplayed()) {
				System.out.println(errelements.get(i).getText());
			}
		}

		if (errelements.isEmpty()) {
			uploadFiles: while (true) {
				if (upload_files.UploadFilesScreen().isDisplayed()) {
					System.out.println("Retreival options are set and user can upload the files in next screen ");
					break uploadFiles;
				} else {
					Thread.sleep(1000);
				}
			}

		}
	}

	public void uploadfiles(WebDriver driver, String filetype) throws InterruptedException, IOException {
		upload_files.getDropdown().click();
		List<WebElement> options = upload_files.getSelectOptions();
		for (WebElement option : options) {
			// System.out.println(option.getText());
			if (option.getText().equals(filetype)) {
				option.click(); // click the desired option
				// String loc = option.toString();
				break;
			} else {
				Thread.sleep(1000);
				// waits.WaitForElement(driver, loc);
				// WebDriverWait wait = new WebDriverWait(driver,2);
				// wait.until(ExpectedConditions.visibilityOfElementLocated(option));
			}
		}

		Thread.sleep(3000);

		Assert.assertFalse(upload_files.getIndexCheckbox().isSelected());

		upload_files.getUpoadFile().click();
		Thread.sleep(5000);
		Runtime.getRuntime().exec(userdir + "\\src\\main\\java\\Resources\\Autoit\\fileuploadscript.exe");

		Fileupload: while (true) {
			int count = upload_files.getUploadedReports().size();
			if (count > 0) {

				List<WebElement> rprts = upload_files.getUploadedReports();

				System.out.println("No.of reports uploaded : " + rprts.size());
				break Fileupload;
			} else {
				Thread.sleep(1000);
			}
		}

		provider_page = upload_files.getNext();
		providerscreen: while (true) {
			if (provider_page.getRecentProviders().isDisplayed()) {
				System.out.println("Provider screen is displayed");
				break providerscreen;
			} else {
				Thread.sleep(1000);
			}
		}

	}

	public void indexOnlyrequest(WebDriver driver) throws InterruptedException {

		System.out.println("Request indexing toggle is off? : " + provider_page.getIndexToggleOff().isDisplayed());

		provider_page.getIndexOnlySwitch().click();

		switchloop: while (true) {

			if (provider_page.getIndexToggleOn().isDisplayed()) {

				System.out.println(provider_page.getIndexOnlySwitchLabel().getText() + " toggle is enabled");
				break switchloop;
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		preview_page = provider_page.getSubmit_Next();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;
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

	public String searchCreatedRequest(WebDriver driver) throws InterruptedException {
		PatientName = firstName + lastName;
		dashboard: while (true) {
			home_page.dahsboard().click();
			if (home_page.dahsboard().isDisplayed()) {
				System.out.println("Dashboard page is displayed");
				break dashboard;
			} else {
				// waits.WaitForElement(driver, dashboard);
				Thread.sleep(1000);
			}
		}

		home_page.PatientSearch().sendKeys(PatientName);

		home_page.Search().click();

		List<WebElement> results = home_page.SearchResults();
		System.out.println("Seacrh completed. No.of patient records found: " + results.size());

		for (WebElement res : results) {
			if (res.getText().contains(lastName)) {
				res.click();
			}
		}
		patient_digest = new patientDigestPage(driver);
		patdigest: while (true) {
			if (patient_digest.getintakerecords().isDisplayed()) {
				System.out.println("patient digest page is displayed");

				break patdigest;
			} else {
//	waits.WaitForElement(driver, digest);
				Thread.sleep(1000);
			}
		}
		String casetxt = patient_digest.getcasetext().getText();
		getcase:while(true) {
			if(casetxt.contains("Case")) {
				break getcase;
			}else {
				Thread.sleep(1000);
			}
		}
		String[] arrSplit = casetxt.split("\\s");
		String casetxt3 = arrSplit[2];
		StringBuffer cno = new StringBuffer(casetxt3);
		// invoking the method
		cno.deleteCharAt(cno.length() - 1);
		// prints the string after deleting the character
		String caseno = cno.toString();
		return caseno;
	}

	public void singleProvider(WebDriver driver, String facilityName, String state, String city)
			throws InterruptedException {

		provider_page.getRecentProviders().click();
		RecentProvidersList: while (true) {
			if (driver.findElement(By
					.xpath("//div[contains(@id,'RecentProvidersList')]//tbody/tr[contains(@id,'RecentProvidersList')]"))
					.isDisplayed()) {
				System.out.println("Waiting fro the recent providers");
				break RecentProvidersList;
			} else {
				Thread.sleep(1000);
			}
		}

		List<WebElement> providers_list = provider_page.getRecentProvidersName();
		int listcount = providers_list.size();
		
		/*ArrayList<WebElement> a = new ArrayList<WebElement>();
		a.addAll(provider_page.getRecentProvidersName());
		
		int i=0;
		while(i<=a.size()) {
			if(a.get(i).getText().equalsIgnoreCase(facilityName)) {
				a.get(i).click();
			}else {
				i++;
			}
		}*/
		if(listcount>0) {
			System.out.println("Going through the recent providers");
				for(WebElement option:providers_list) {
					if(option.getText().equalsIgnoreCase(facilityName)) {
						System.out.println("if provider");
						option.click();
						templateloop:while(true) {
							if(provider_page.ManageTemplates().isDisplayed()) {
								break templateloop;
							}else {
								Thread.sleep(1000);
							}
					}	
				
			}
			}
		}else {
			System.out.println("else loop");
				provider_page.getclose();
				
			}
		try {
			System.out.println("try catch loop loop");
			
			Thread.sleep(1000);
			provider_page.getclose();
			
		}catch(NoSuchElementException e) {
			
		}

		providerSearch(driver, facilityName,  state,  city);
		
		
	}
	
	public void providerSearch(WebDriver driver,String facilityName, String state, String city) throws InterruptedException {
		System.out.println("searhcing the name");
		provider_page.getProviderNameField().sendKeys(facilityName);

		Select state_name = new Select(provider_page.getStateDropDown());
		state_name.selectByVisibleText(state);

		provider_page.getcity().sendKeys(city);
		provider_page.getSearch().click();

		providerresults: while (true) {
			if (provider_page.getresults().isDisplayed()) {
				List<WebElement> providers = provider_page.getproviderResults();
				for (WebElement provider : providers) {
					if (provider.getText().equalsIgnoreCase(facilityName)) {
						provider.click();
						break providerresults;
					} else {
						Thread.sleep(1000);
					}
				}
			}

		}
	}

		
	
}