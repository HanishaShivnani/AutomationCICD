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

public class SubmitOrderNewApproachTest2 extends BaseTest {
    
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

    /* THIS IS THE FIRST WAY TO GET DATA THROUGH ARRAY [step 3 to 5 will be the same]
     * 
    public void submitOrder(String email, String password, String productName) throws IOException, InterruptedException {
        
        // Step 1: Login using provided email and password from DataProvider
        ProductCatalogue productCatalogue = landingPage.loginInto(email, password);

        // Step 2: Retrieve product list and add specified product to the cart
        List<WebElement> productList = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    ------------------------------------------------------------------------------------   
    creating an array here  
    @DataProvider
    public Object[][] getLoginData() {
        return new Object[][] {
            { "hanishashivnani1234@gmail.com", "SeleniumH01","ZARA COAT 3" },
            { "kashtii@yopmail.com", "K@shtii01","IPHONE 13 PRO" }
        };
    }*/
    
    /**
     * DataProvider method that supplies test data for submitOrder().
     * Uses HashMap to store multiple sets of email, password, and product name.
     * @return Object[][] containing login credentials and products.
     */
    @DataProvider
    public Object[][] getData() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("email", "hanishashivnani1234@gmail.com");
        map1.put("password", "SeleniumH01");
        map1.put("product", "ZARA COAT 3");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("email", "kashtii@yopmail.com");
        map2.put("password", "K@shtii01");
        map2.put("product", "ADIDAS ORIGINAL");

        return new Object[][] { { map1 }, { map2 } };
    }
}