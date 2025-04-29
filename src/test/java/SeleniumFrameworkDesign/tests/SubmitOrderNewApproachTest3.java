package SeleniumFrameworkDesign.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumFrameworkDesign.TestComponents.BaseTest;
import SeleniumFrameworkDesign.pageobjects.CartPage;
import SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import SeleniumFrameworkDesign.pageobjects.OrderPage;
import SeleniumFrameworkDesign.pageobjects.ProductCatalogue;

/* This test class performs order submission and order verification
 * In this we are trying to get data through 2 method 1) array and 2) hash map
 * */

public class SubmitOrderNewApproachTest3 extends BaseTest {
    
	String productName = "ZARA COAT 3";
	
    /**
     * Test to submit an order using different login credentials.
     * Uses a DataProvider to pass multiple sets of data.
     */
    @Test(dataProvider = "getData", groups = "PurchaseOrder")
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        
        // Step 1: Login using provided email and password from DataProvider
        ProductCatalogue productCatalogue = landingPage.loginInto(input.get("email"), input.get("password"));

        // Step 2: Retrieve product list and add specified product to the cart
        List<WebElement> productList = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("product"));

        // Step 3: Navigate to the Cart Page and verify the product in the cart
        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertTrue(cartPage.VerifyProductsInCart(input.get("product")));

        // Step 4: Proceed to Checkout
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");

        // Step 5: Submit the order and verify confirmation message
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    /**
     * Test to verify if the placed order appears in the Orders Page.
     * This test depends on the successful completion of submitOrder().
     */
    @Test(dependsOnMethods = "submitOrder")
    public void verifyOrder() throws InterruptedException {
        ProductCatalogue productCatalogue = landingPage.loginInto("hanishashivnani1234@gmail.com", "SeleniumH01");
        OrderPage orderPage = productCatalogue.goToOrders();
        Assert.assertTrue(orderPage.VerifyProductInOrders(productName));
    }

    
    
    /**
     * DataProvider method that supplies test data for submitOrder().
     * Uses HashMap to store multiple sets of email, password, and product name.
     * @return Object[][] containing login credentials and products.
     * @throws IOException 
     */
    @DataProvider
    public Object[][] getData() throws IOException {
        
    	List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "src/test/java/SeleniumFrameworkDesign/data/PurchaseOrder.json");
        return new Object[][] { { data.get(0) }, { data.get(1) } };
    }
}