package SeleniumFrameworkDesign.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

/**
 * Represents the Cart Page of the application.
 * Extends AbstractComponents to reuse common functionalities.
 */
public class CartPage extends AbstractComponents {

    WebDriver driver;

    // Locators for products in the cart and checkout button
    @FindBy(css = ".cartSection h3")
    List<WebElement> productsInCart;

    @FindBy(xpath = "(//button[normalize-space()='Checkout'])[1]")
    WebElement clickOnCheckout;

    // Constructor to initialize WebDriver and WebElements
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies if the specified product is present in the cart.
     */
    public Boolean VerifyProductsInCart(String productName) throws InterruptedException {
        Thread.sleep(3000); // Adding wait to ensure elements are loaded
        return productsInCart.stream()
            .anyMatch(selectedProduct -> selectedProduct.getText().equalsIgnoreCase(productName));
    }

    /**
     * Navigates to the Checkout Page.
     */
    public CheckoutPage goToCheckout() {
        clickOnCheckout.click();
        return new CheckoutPage(driver);
    }
}