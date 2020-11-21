package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {

		// Create driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Do not know to start " + browser + ", starting  chrome instead");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		}

		// sleep for 3 seconds
		// sleep(2000);

		// maximize browser window
		driver.manage().window().maximize();
	}

	@Test(priority = 1, groups = { "postiveTests", "smokeTests" })
	public void positiveLoginTest() {
		System.out.println("Starting loginTest");

		// open test page
		// sleep(2000);
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// sleep for 2 seconds
		// sleep(2000);

		// enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

		// enter passwords
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

		// sleep(2000);
		// click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		// verifications:
		// new url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

		// logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Log out button is not visible");

		// succesful login massage
		WebElement succesMessage = driver.findElement(By.cssSelector("div#flash"));
		String expectedMessage = "You logged into a secure area!";
		String actualMessage = succesMessage.getText();
		// Assert.assertEquals(actualMessage, expectedMessage, "Actual message url is
		// not the same as expected");
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message.\nActual Message " + actualMessage
						+ "\nExpected Message: " + expectedMessage);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
		System.out.println("Starting negativeLoginTest with " + username + " and " + password);
		// Create driver

		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// enter negative username
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

		// enter passwords
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);

		// click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		// verifications:

		WebElement errorMessage = driver.findElement(By.id("flash"));

		String actualErrorMessage = errorMessage.getText();
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected error message.\nActual Message " + actualErrorMessage
						+ "\nExpected Message: " + expectedErrorMessage);

	}

	/*
	 * private void sleep(long m) { try { Thread.sleep(m); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close browser
		driver.quit();
	}
}
