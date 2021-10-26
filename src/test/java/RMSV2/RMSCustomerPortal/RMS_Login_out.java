package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.driverClass;

public class RMS_Login_out extends driverClass {
	public static WebDriver driver;
	
	@BeforeTest
	public void initialise() throws IOException {
		driver=intializedriver();
		
	}
	@Test(priority=1)
	public void login() throws IOException, InterruptedException {
		RMS_access_methods signin=new RMS_access_methods();
		signin.login(driver);

	}
	
	@Test(priority=2)
	public void logout() throws InterruptedException {
		RMS_access_methods signout=new RMS_access_methods();
		signout.logout(driver);
		
	}
	
	@AfterTest
	public void browserclose() {
		driver.close();
	}

}
