package SeleniumFrameworkDesign.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SeleniumFrameworkDesign.pageobjects.CartPage;
import SeleniumFrameworkDesign.pageobjects.OrderPage;

/**
 * This class provides reusable components (methods) 
 * that can be used across different pages in the framework.
 * It contains methods for waiting, scrolling, and navigating to cart & orders pages.
 */
public class AbstractComponents {

    WebDriver driver;

    /**
     * Constructor to initialize WebDriver.
     * The driver is passed from another class since it has no life cycle of its own here.
     * 
     * @param driver - WebDriver instance
     */
    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
    }

    // Locators using @FindBy annotation for cart and orders page buttons
    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement goToCart;

    @FindBy(xpath = "//button[@routerlink='/dashboard/myorders']")
    WebElement goToOrders;

    /**
     * Waits until a specific element (located using a By locator) becomes visible.
     * 
     * @param findBy - Locator of the element to wait for
     */
    public void elementsToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    /**
     * Waits until a specific WebElement becomes visible.
     * 
     * @param FindBy - WebElement to wait for
     */
    public void WebElementsToAppear(WebElement FindBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(FindBy));
    }

    /**
     * Waits until a specific WebElement disappears.
     * Uses Thread.sleep as an alternative method since some elements take time to disappear.
     * 
     * @param ele - WebElement to wait for
     * @throws InterruptedException - Handles interruption exception for Thread.sleep
     */
    public void elementsToDisappear(WebElement ele) throws InterruptedException {
        // Using Thread.sleep since some elements take longer to disappear in the backend.
        Thread.sleep(2000);

        // Alternative (preferred) approach:
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // wait.until(ExpectedConditions.invisibilityOf(ele));
    }

    /**
     * Navigates to the Cart Page by clicking on the cart button.
     * Returns an instance of the CartPage.
     * 
     * @return CartPage instance
     */
    public CartPage goToCartPage() {
        goToCart.click();
        return new CartPage(driver); // Navigates to the cart page
    }

    /**
     * Navigates to the Orders Page by clicking on the orders button.
     * Returns an instance of the OrderPage.
     * 
     * @return OrderPage instance
     */
    public OrderPage goToOrders() {
        goToOrders.click();
        return new OrderPage(driver); // Navigates to the orders page
    }

    /**
     * Scrolls down the web page by 500 pixels using JavaScript Executor.
     */
    public void scrollDownPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
    }

    public void scrollUpPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 500);");
    }
}