package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.IndexRequestExcelDataProvider;
import resources.driverClass;

public class indexreportrequestExcelData extends driverClass {
	public static WebDriver driver;
	RMS_request_methods2 request = new RMS_request_methods2();

	@BeforeTest
	public void browser() throws Exception {
		driver = intializedriver();
		
	}

	@Test(priority = 1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin = new RMS_access_methods();
		signin.login(driver);

	}

	@Test(dataProvider = "ExcelData", dataProviderClass = IndexRequestExcelDataProvider.class, dependsOnMethods = { "login" })
	public void PatientDetails(String firstName, String SSN, String dob, String streetAdd, String city,
			String postalCode, String phnum,String NeedByDate, String RecordsNeededFor, String AuthorizingPhysician,
			String PurposeOfRequest,String filetype) throws InterruptedException, IOException {
		request.patientdemographics(driver, firstName, SSN, dob, streetAdd, city, postalCode, phnum);
		request.chooseRetrievalOptions(driver, NeedByDate, RecordsNeededFor, AuthorizingPhysician, PurposeOfRequest);
		request.uploadfiles(driver, filetype);
		request.indexOnlyrequest(driver);
		request.searchCreatedRequest(driver);

	}

	@AfterTest
	public void browserclose() {
		driver.close();
	}

}
