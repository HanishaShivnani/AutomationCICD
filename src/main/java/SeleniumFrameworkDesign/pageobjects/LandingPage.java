package SeleniumFrameworkDesign.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

// LandingPage class handles user login and site navigation
public class LandingPage extends AbstractComponents {

	WebDriver driver;

	// Constructor to initialize the driver and page elements
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// WebElement for email input field
	@FindBy(id="userEmail")
	WebElement emailEle;

	// WebElement for password input field
	@FindBy(id="userPassword")
	WebElement passwordEle;

	// WebElement for login button
	@FindBy(id="login")
	WebElement loginEle;

	// WebElement for error message toast notification
	@FindBy(css=".toast-message")
	WebElement errorMsg;

	/**
	 * Method to perform login action on the landing page.
	 * Waits for the password field to be filled before clicking the login button.
	 * @param email The email address to login
	 * @param password The password for login
	 * @return ProductCatalogue object after successful login
	 */
	public ProductCatalogue loginInto(String email, String password) {
		emailEle.sendKeys(email);
		passwordEle.sendKeys(password);
		// Wait for password input to be filled before proceeding
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(d -> !d.findElement(By.id("userPassword"))
			.getAttribute("value").isEmpty());
		// Clicking login button
		loginEle.click();
		// Returning the ProductCatalogue page after login
		return new ProductCatalogue(driver);
	}

	/**
	 * Method to navigate to the application URL.
	 */
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	/**
	 * Method to retrieve error message displayed after failed login attempt.
	 * @return The error message text
	 */
	public String getErrorMsg() {
		// Waiting for error message element to appear
		WebElementsToAppear(errorMsg);
		return errorMsg.getText();
	}
}