package SeleniumFrameworkDesign.stepDefinitions;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import SeleniumFrameworkDesign.TestComponents.BaseTest;
import SeleniumFrameworkDesign.pageobjects.CartPage;
import SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import SeleniumFrameworkDesign.pageobjects.LandingPage;
import SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImp1 extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage; 
	
	@Given("I landed on ECommerce Page")
	public void I_Landed_On_ECommerce_Page() throws IOException {
		landingPage = launchApplication();
		
	}

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginInto(username, password);
	}
	
	@When("^I add the product (.+) to cart$")
	public void i_add_product_to_cart(String productName ) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) throws InterruptedException {
		CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductsInCart(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");
        confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on the confirmation page")
	public void message_displayed_confirmationPage(String string) throws InterruptedException {
		String confirmMessage = confirmationPage.getConfirmationMessage();
       // Thread.sleep(5000);
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
        driver.close();
	}
	
	@Then("{string} message is displayed")
    public void something_message_is_displayed(String strArg1) throws Throwable {
   
    	Assert.assertEquals(strArg1, landingPage.getErrorMsg());
    	driver.close();
    }	
	
	
}
