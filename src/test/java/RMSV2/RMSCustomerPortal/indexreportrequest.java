package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.dataproviders;
import resources.driverClass;

public class indexreportrequest extends driverClass {
	public static WebDriver driver;
public static Logger log=LogManager.getLogger("INdexOnlyRequest");

	@BeforeTest
	public void browser() throws IOException {
		driver = intializedriver();
		log.info("Driver is initialised");

	}

	@Test(priority = 1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin = new RMS_access_methods();
		signin.login(driver, RMS_access_methods.username, RMS_access_methods.password);

	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = { "login" })
	public void PatientDetails(String firstName, String SSN, String dob, String streetAdd, String city,
			String postalCode, String phnum) throws InterruptedException, IOException {
		log.info("Logged in to RMS portal successfully");
		RMS_request_methods request = new RMS_request_methods();

		request.patientdemographics(driver, firstName, SSN, dob, streetAdd, city, postalCode, phnum);

	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = { "PatientDetails" })
	public void ChooseRetrievalOptions(String NeedByDate, String RecordsNeededFor, String AuthorizingPhysician,
			String PurposeOfRequest) throws InterruptedException, IOException {
		log.info("Choosing the retrieval options");
		RMS_request_methods request = new RMS_request_methods();
		request.chooseRetrievalOptions(driver, NeedByDate, RecordsNeededFor, AuthorizingPhysician, PurposeOfRequest);
	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = {
			"ChooseRetrievalOptions" })
	public void uploadfiles(String filetype) throws InterruptedException, IOException {
		log.info("Uploading the files");
		RMS_request_methods request = new RMS_request_methods();
		request.uploadfiles(driver, filetype);
	}

	@Test(dependsOnMethods = { "uploadfiles" })
	public void indexOnlyrequest() throws InterruptedException, IOException {
		log.info("index Only request");
		RMS_request_methods request = new RMS_request_methods();
		request.indexOnlyrequest(driver);
	}

	@Test(dependsOnMethods = { "indexOnlyrequest" })
	public void searchrequest() throws InterruptedException, IOException {
		log.info("Search created request");
		RMS_request_methods request = new RMS_request_methods();
		String caseno = request.searchCreatedRequest(driver);
		System.out.println("Case no : " + caseno);
	}

	@AfterTest
	public void browserclose() {
		driver.close();
	}

}
