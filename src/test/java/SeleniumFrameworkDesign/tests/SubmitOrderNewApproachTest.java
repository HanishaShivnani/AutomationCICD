package SeleniumFrameworkDesign.tests;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import SeleniumFrameworkDesign.pageobjects.CartPage;
import SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import SeleniumFrameworkDesign.pageobjects.LandingPage;
import SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

/* SubmitOrderNewApproachTest simulates a full purchase flow from login to order confirmation.
 * In this all the details we are putting are not hard coded 
 * In this we are creating object for all classes, 
 * In class "SubmitOrderNewApproachTest2" we will not create object of each class we will try shortcut to create object 
 */
public class SubmitOrderNewApproachTest {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {
        
        String productName = "ZARA COAT 3";

        // Step 1: Set up the WebDriver for Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Step 2: Maximize the browser window
        driver.manage().window().maximize();

        // Step 3: Navigate to the Landing Page
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();

        // Step 4: Log in to the application
        landingPage.loginInto("hanishashivnani1234@gmail.com", "SeleniumH01");

        // Step 5: Navigate to Product Catalogue
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);

        // Step 6: Retrieve the list of products available on the page
        List<WebElement> productList = productCatalogue.getProductList();

        // Step 7: Add the specified product to the cart
        productCatalogue.addProductToCart(productName);

        // Step 8: Navigate to the Cart Page
        productCatalogue.goToCartPage();

        // Step 9: Verify that the selected product is present in the cart
        CartPage cartPage = new CartPage(driver);
        Boolean match = cartPage.VerifyProductsInCart(productName);
        Assert.assertTrue(match);

        // Step 10: Proceed to Checkout Page
        cartPage.goToCheckout();

        // Step 11: Select country and place order
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.selectCountry("India");
        checkoutPage.submitOrder();

        // Step 12: Verify order confirmation message
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Thread.sleep(5000);
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        // Step 13: Close the browser
        driver.close();
    }
}