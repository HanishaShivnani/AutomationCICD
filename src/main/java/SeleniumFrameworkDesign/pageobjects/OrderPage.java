package SeleniumFrameworkDesign.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

// OrderPage class handles the validation of ordered products
public class OrderPage extends AbstractComponents {

	// WebElement list storing all product names from the order summary
	@FindBy(xpath="//tbody/tr/td[2]")
	List<WebElement> productsInOrders;

	WebDriver driver;

	// Constructor to initialize the driver and page elements
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Method to verify if a specific product is present in the order summary.
	 * @param productName The name of the product to check
	 * @return Boolean value indicating whether the product exists in the order
	 * @throws InterruptedException
	 */
	public Boolean VerifyProductInOrders(String productName) throws InterruptedException {
		// Checking if any product in the order matches the given product name
		Boolean match = productsInOrders.stream()
			.anyMatch(selectedProduct -> selectedProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
}