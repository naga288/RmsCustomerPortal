package RMSV2.RMSCustomerPortal;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.ProviderRequestExcelDataProvider;
import resources.driverClass;


public class singleProviderRequestExceldata extends driverClass {
	public static WebDriver driver;
	RMS_request_methods2 request = new RMS_request_methods2();
	ProviderRequestMethods2 Provider_request = new ProviderRequestMethods2();

//public static Logger log=LogManager.getLogger("INdexOnlyRequest");

	@BeforeTest
	public void browser() throws Exception {
		driver = intializedriver();
	
	}

	@Test(priority = 1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin = new RMS_access_methods();
		signin.login(driver);

	}

	@Test(dataProvider = "ExcelData", dataProviderClass = ProviderRequestExcelDataProvider.class, dependsOnMethods = { "login" })
	public void ProviderRequest(String firstName, String SSN, String dob, String streetAdd, String city,
			String postalCode, String phnum,String NeedByDate, String RecordsNeededFor, String AuthorizingPhysician,
			String PurposeOfRequest,String filetype,String facilityName,String provider_state, String Provider_city, String rec_template,String img_template, String path_template) throws InterruptedException, IOException {
		request.patientdemographics(driver, firstName, SSN, dob, streetAdd, city, postalCode, phnum);
		request.chooseRetrievalOptions(driver, NeedByDate, RecordsNeededFor, AuthorizingPhysician, PurposeOfRequest);
		request.uploadfilesNext(driver);
		Provider_request.singleLocationProvider(driver, facilityName, provider_state, Provider_city, rec_template, img_template, path_template);
		request.searchCreatedRequest(driver);

	}

	
@AfterTest
	public void browserclose() {
		driver.close();
	}

}
