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

// ConfirmationPage class handles order confirmation after checkout
public class ConfirmationPage extends AbstractComponents{

	WebDriver driver;

	// Constructor to initialize the driver and page elements
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// WebElement for the confirmation message displayed after order submission
	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;

	/**
	 * Method to get the order confirmation message.
	 * Waits for the confirmation message to appear before retrieving the text.
	 * @return The text of the confirmation message
	 * @throws InterruptedException
	 */
	public String getConfirmationMessage() throws InterruptedException {
		// Creating an explicit wait to ensure the confirmation message is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hero-primary")));
		// Returning the text content of the confirmation message
		return confirmationMessage.getText();
	}
}