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

// ProductCatalogue class manages product listing and adding products to the cart
public class ProductCatalogue extends AbstractComponents {

	WebDriver driver;

	// Constructor to initialize driver and page elements
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// WebElement list storing all available products
	@FindBy(css=".mb-3")
	List<WebElement> products;

	// WebElement representing the loading spinner animation
	@FindBy(css=".ng-animating")
	WebElement spinner;

	// Locators for product elements
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".btn.w-10.rounded");
	By toastMsg = By.cssSelector("div[aria-label='Product Added To Cart']");

	/**
	 * Method to retrieve the list of products available on the page.
	 * @return List of WebElements representing products
	 */
	public List<WebElement> getProductList() {
		elementsToAppear(productsBy);
		return products;
	}

	/**
	 * Method to find a specific product by name.
	 * @param productName The name of the product to find
	 * @return WebElement representing the found product, or null if not found
	 */
	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
			.filter(product -> product.findElement(By.cssSelector("b"))
			.getText().equalsIgnoreCase(productName))
			.findFirst()
			.orElse(null);
		return prod;
	}

	/**
	 * Method to add a specified product to the cart.
	 * @param productName The name of the product to add to the cart
	 * @return CartPage object after product is added to the cart
	 * @throws InterruptedException
	 */
	public CartPage addProductToCart(String productName) throws InterruptedException {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		elementsToAppear(toastMsg);
		elementsToDisappear(spinner);
		return new CartPage(driver);
	}
}