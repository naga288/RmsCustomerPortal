package RMSV2.RMSCustomerPortal;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.homePage;
import pageObjects.loginPage;
import resources.driverClass;

public class RMS_access_methods extends driverClass {

	public static Logger app_logs = Logger.getLogger("RMSLogin");

	public static homePage h_page;
	public static loginPage log_in;

	public void login(WebDriver driver, String Env, String UserName, String Password)
			throws IOException, InterruptedException {
		log_in = new loginPage(driver);
		h_page = new homePage(driver);
		app_logs.info("login with Creds : Username:" + UserName + " & Password:" + Password);
		driver.get(Env);

		try {
			login: while (true) {
				if (log_in.loginScreen().isDisplayed()) {
					log_in.username().sendKeys(UserName);
					log_in.password().sendKeys(Password);
					log_in.signin().click();

					break login;
				} else {
					// waits.WaitForElement(driver, log_in.loginScreen());
					Thread.sleep(1000);
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dashboard: while (true) {
			try {

				if (h_page.RecordsTabPage().isDisplayed()) {
					String user_text = "Welcome " + UserName;
					Assert.assertTrue(h_page.username().getText().equalsIgnoreCase(user_text));

					app_logs.info("RMS customer portal Login is succesfull for user: " + h_page.username().getText());
					break dashboard;
				} else {
					Thread.sleep(1000);
				}
			} catch (NoSuchElementException e) {
				app_logs.info(e);
			}
		}
	}

	public void logout(WebDriver driver) throws InterruptedException {
		try {
			logoutloop: while (true) {
				if (h_page.RecordsTabPage().isDisplayed()) {
					h_page.UserIcon().click();
					h_page.Logout().click();
					if (log_in.loginScreen().isDisplayed()) {
						System.out.println("Logout is successful");
						break logoutloop;
					}
				} else {
					Thread.sleep(1000);
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}
	}
}