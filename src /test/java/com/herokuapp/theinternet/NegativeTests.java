package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {

	@Parameters({ "username", "password", "expectedMessage"})
	@Test(priority = 1,  groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMassege) {
		System.out.println("incorrectUsernameTest");
		// Create driver

		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
		WebDriver driver = new FirefoxDriver();
		

		// maximize browser window
		driver.manage().window().maximize();

		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// enter negative username
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys("incorrect");

		// enter passwords
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys("SuperSecretPassword!");

		// click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		// verifications:

		WebElement errorMessage = driver.findElement(By.id("flash"));
		String expectedErrorMessage = "Your username is invalid!";
		String actualErrorMessage = errorMessage.getText();
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected error message.\nActual Message " + actualErrorMessage
						+ "\nExpected Message: " + expectedErrorMessage);

		// close browser
		driver.quit();
	}

}	
/*
 * @Test(priority = 2, groups = { "negativeTests" }) public void
 * incorrectPasswordTest() { System.out.println("incorrectPasswordTest"); //
 * Create driver
 * 
 * System.setProperty("webdriver.chrome.driver",
 * "src/main/resources/chromedriver"); WebDriver driver = new ChromeDriver();
 * 
 * // maximize browser window driver.manage().window().maximize();
 * 
 * String url = "http://the-internet.herokuapp.com/login"; driver.get(url);
 * System.out.println("Page is opened.");
 * 
 * // enter username WebElement username =
 * driver.findElement(By.id("username")); username.sendKeys("tomsmith");
 * 
 * // enter negative passwords WebElement password =
 * driver.findElement(By.name("password")); password.sendKeys("Super!");
 * 
 * // click login button WebElement logInButton =
 * driver.findElement(By.tagName("button")); logInButton.click();
 * 
 * // verifications:
 * 
 * WebElement errorMessage = driver.findElement(By.id("flash")); String
 * expectedErrorMessage = "Your password is invalid!"; String actualErrorMessage
 * = errorMessage.getText();
 * Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
 * "Actual error message does not contain expected error message.\nActual Message "
 * + actualErrorMessage + "\nExpected Message: " + expectedErrorMessage);
 * 
 * // close browser driver.quit(); }
 * 
 * 
 * }
 */
