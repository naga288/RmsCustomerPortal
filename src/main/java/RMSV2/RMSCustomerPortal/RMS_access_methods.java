package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.homePage;
import pageObjects.loginPage;
import resources.driverClass;

public class RMS_access_methods extends driverClass{
	
	//public static Logger log=LogManager.getLogger(RMS_Login.class.getName());

	public static String username="ehgt.test4";
	public static String password="Kick4Thy";

	public static homePage h_page;
	
	public void login(WebDriver driver, String username,String password) throws IOException, InterruptedException {
		loginPage log_in = new loginPage(driver);
		
System.out.println("login with Creds : Username:"+username+" & Password:" +password);
log.info("login with Creds : Username:"+username+" & Password:" +password);
		login: while (true) {
			if (log_in.loginScreen().isDisplayed()) {
				log_in.username().sendKeys(username);
				log_in.password().sendKeys(password);
				 h_page=log_in.signin();

				break login;
			} else {
				//waits.WaitForElement(driver, log_in.loginScreen());			
				Thread.sleep(1000);
				}
		}
		dashboard:while(true) {
			try {


		if (h_page.dahsboard().isDisplayed()) {
			String user_text="Welcome "+username;
			Assert.assertEquals(h_page.dahsboard().getText(),user_text);

			System.out.println("RMS customer portal Login is succesfull for user: " + h_page.dahsboard().getText());
			log.info("Login successful");
			break dashboard;
		}else {
			Thread.sleep(1000);
		}
			}
			catch(NoSuchElementException e) {
			System.out.println(e);
		}
		}
		/*
		 * else if(log_in.login_errmsg().isDisplayed()) {
		 * System.out.println(log_in.login_errmsg().getText()); }
		 */
	}
	public void logout(WebDriver driver) throws InterruptedException {
	try {	
		logoutloop:while(true) {
		if (h_page.dahsboard().isDisplayed()) {
h_page.UserIcon().click();
h_page.Logout().click();
			if(h_page.dahsboard().isDisplayed()) {
				System.out.println("Logout is successful");
				break logoutloop;
			}
		}else {
Thread.sleep(1000);
}
		}
		}
			catch(NoSuchElementException e){
				System.out.println(e);
		}
	}
}