package SeleniumFrameworkDesign.tests;
import SeleniumFrameworkDesign.TestComponents.*;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumFrameworkDesign.pageobjects.CartPage;
import SeleniumFrameworkDesign.pageobjects.ProductCatalogue;

// ErrorValidation class contains test cases for error handling and product validation

public class ErrorValidation extends BaseTest {

    /**
     * Test case to validate incorrect login credentials.
     * It attempts to log in with an incorrect email/password combination
     * and verifies if the error message is displayed as expected.
     */
	
	
	
	@Test(groups={"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void submitOrder() {
        // Attempt login with incorrect credentials
        landingPage.loginInto("hanishashivnani@gmail.com", "SeleniumH01");

        // Validate the error message
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMsg());
    }

    /**
     * Test case to verify if a specific product is correctly added to the cart.
     * It logs in, adds a product to the cart, navigates to the cart page, and checks if the product is present.
     * @throws InterruptedException in case of thread sleep interruptions
     */
    @Test
    public void matchProduct() throws InterruptedException {
        String productName = "ZARA COAT 3";

        // Login and navigate to Product Catalogue
        ProductCatalogue productCatalogue = landingPage.loginInto("hanishashivnani1234@gmail.com", "SeleniumH01");

        // Retrieve product list (not explicitly used but ensures products are loaded)
        List<WebElement> productList = productCatalogue.getProductList();

        // Add product to cart
        productCatalogue.addProductToCart(productName);

        // Navigate to cart page
        CartPage cartPage = productCatalogue.goToCartPage();

        // Verify if the product is in the cart
        Boolean match = cartPage.VerifyProductsInCart(productName);
        Assert.assertTrue(match);
    }
}