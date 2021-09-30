package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.driverClass;

public class RMS_Login_out extends driverClass {
	public static WebDriver driver;
	public static String lastname;
	public static String username="ehgt.test4";
	public static String password="Kick4Thy";
	
	@BeforeTest
	public WebDriver initialise() throws IOException {
		driver=intializedriver();
		return driver;
	}
	@Test(priority=1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin=new RMS_access_methods();
		signin.login(driver, username,  password);

	}
	
	@Test(priority=2)
	public void logout() throws InterruptedException {
		RMS_access_methods signout=new RMS_access_methods();
		signout.logout(driver);
		
	}
	
	@AfterClass
	public void terminate() {
		driver.close();
	}

}
