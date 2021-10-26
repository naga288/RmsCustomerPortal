package RMSV2.RMSCustomerPortal;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.dataproviders;
import resources.driverClass;


public class singleProviderRequest extends driverClass {
	public static WebDriver driver;
	RMS_request_methods request = new RMS_request_methods();
	ProviderRequestMethods Provider_request = new ProviderRequestMethods();

//public static Logger log=LogManager.getLogger("INdexOnlyRequest");

	@BeforeTest
	public void browser() throws IOException {
		driver = intializedriver();
		log.info("Driver is initialised");

	}

	@Test(priority = 1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin = new RMS_access_methods();
		signin.login(driver);

	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = { "login" })
	public void PatientDetails(String firstName, String SSN, String dob, String streetAdd, String city,
			String postalCode, String phnum) throws InterruptedException, IOException {
		log.info("Logged in to RMS portal successfully");
		request.patientdemographics(driver, firstName, SSN, dob, streetAdd, city, postalCode, phnum);

	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = { "PatientDetails" })
	public void ChooseRetrievalOptions(String NeedByDate, String RecordsNeededFor, String AuthorizingPhysician,
			String PurposeOfRequest) throws InterruptedException, IOException {
		log.info("Choosing the retrieval options");
		request.chooseRetrievalOptions(driver, NeedByDate, RecordsNeededFor, AuthorizingPhysician, PurposeOfRequest);
	}

	@Test( dependsOnMethods = {
			"ChooseRetrievalOptions" })
	public void uploadfilesNext() throws InterruptedException, IOException {
		log.info("Uploading the files");
		request.uploadfilesNext(driver);
	}

	@Test(dataProvider = "testData", dataProviderClass = dataproviders.class, dependsOnMethods = {
	"uploadfilesNext" })
	public void singleLocationProvider(String facilityName,String state, String city, String rec_template,String img_template, String path_template, String LocationType) throws InterruptedException {
		Provider_request.singleLocationProvider(driver, facilityName, state, city, rec_template, img_template, path_template,LocationType);
		
	}
	
	@Test(dependsOnMethods = {"singleLocationProvider" })
	public void searchCreatedRequest() throws InterruptedException {
		request.searchCreatedRequest(driver);
	}
	
	
	
@AfterTest
	public void browserclose() {
		driver.close();
	}

}
