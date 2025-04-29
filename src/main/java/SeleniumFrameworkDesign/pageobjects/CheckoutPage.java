package SeleniumFrameworkDesign.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

// CheckoutPage class handles the checkout process in the test framework
public class CheckoutPage extends AbstractComponents{
	
	WebDriver driver;

	// Constructor to initialize the driver and page elements
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// WebElement for the submit button on the checkout page
	@FindBy(css=".action__submit")
	WebElement submit;
	
	// WebElement for the country selection input field
	@FindBy(css="[placeholder='Select Country']")
	WebElement country;
	
	// WebElement for selecting a country from the dropdown list
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
	WebElement selectCountry;
	
	// Locator for the country dropdown results
	By results = By.cssSelector(".ta-results");
	
	/**
	 * Method to select a country from the dropdown list.
	 * @param countryName The name of the country to be selected.
	 */
	public void selectCountry(String countryName){
		Actions act = new Actions(driver);
		// Typing the country name into the input field
		act.sendKeys(country, countryName).build().perform(); 
		// Waiting for the dropdown results to appear
		elementsToAppear(By.cssSelector(".ta-results"));
		// Selecting the country from the list
		selectCountry.click();
	}
	
	/**
	 * Method to submit the order and navigate to the Confirmation Page.
	 * @return ConfirmationPage object
	 * @throws InterruptedException
	 */
	public ConfirmationPage submitOrder() throws InterruptedException {
		// Scrolling down to ensure the submit button is visible
		scrollDownPage();
		// Adding a delay to handle any loading time issues
		Thread.sleep(2000);
		// Clicking the submit button to place the order
		submit.click();
		// Returning the ConfirmationPage instance after submission
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}
}