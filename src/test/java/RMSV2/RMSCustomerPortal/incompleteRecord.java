package RMSV2.RMSCustomerPortal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.homePage;
import pageObjects.patientProfilePage;
import pageObjects.retreivalOptionsPage;
import resources.driverClass;

public class incompleteRecord extends driverClass{
	public WebDriver driver;
	public static String lastname;
//public static Logger log=LogManager.getLogger("INdexOnlyRequest");
	
	
	@BeforeTest
	public void browser() throws Exception {
		driver=intializedriver();
		
	}
	
	@Test()
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin=new RMS_access_methods();
		signin.login(driver);
		
	}
	
	@Test(dataProvider="getdata",dependsOnMethods= {"login"} )
	public void indexOnlyRequest(String firstName, String SSN, String dob,String streetAdd,String city,String postalCode, String phnum  ) throws InterruptedException, IOException {
		homePage home_page = new homePage(driver);
		retreivalOptionsPage ret_page = new retreivalOptionsPage(driver);
		patientProfilePage profile_page = new patientProfilePage(driver);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

		// get current date time with Date()
		Date date = new Date();

		// Now format the date
		String date1 = dateFormat.format(date);
		String lastName = date1;
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
				if (ret_page.CHOOSERETRIEVALOPTIONS().isDisplayed()) {
					System.out.println(ret_page.CHOOSERETRIEVALOPTIONS().getText() + " Screen is displayed");
					break screen2;
				} else {
//waits.WaitForElement(driver, retrievalOptions);
					Thread.sleep(1000);
				}
			}
		}

		
		}
	

}
