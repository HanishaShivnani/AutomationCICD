package SeleniumFrameworkDesign.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import SeleniumFrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

//In this class we are using old approach 

public class SubmitOrderTest {

    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
    	
    	String productName = "ZARA COAT 3";
        // Set up the WebDriver for Chrome	
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        
        // Maximize window
        driver.manage().window().maximize();
        
        //creating object and using driver of this class to LandingPage.java class in "src/main/java" 
        LandingPage landingPage = new LandingPage(driver);
        
        //login
        driver.findElement(By.id("userEmail")).sendKeys("hanishashivnani1234@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("SeleniumH01");
       // waiting till password filled by user 
        new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> !d.findElement(By.id("userPassword"))
        .getAttribute("value").isEmpty());
        driver.findElement(By.id("login")).click();
        	
        // Use explicit wait to wait for the product list to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Get list of product elements
        List<WebElement> productList = driver.findElements(By.cssSelector(".mb-3"));

        // Filter the product list for the product with the name "ADIDAS"[product name can be change so change name in code too
        WebElement prod = productList.stream()
            .filter(product -> {
                // Wrap in try-catch in case the <b> tag is not found in a product element
                try {
                    return product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName);
                } catch (Exception e) {
                    return false;
                }
            })
            .findFirst()
            .orElse(null);

        // Check if the product was found before trying to click the button
        if (prod != null) {
            prod.findElement(By.cssSelector(".btn.w-10.rounded")).click();
        } else {
            System.out.println("Product "+ productName + " not found!");
        }
        
        //using explicit wait until products added to cart toast message will visible
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[aria-label='Product Added To Cart']")));
        
        //we can use invisibility also but this line not working here  
        //wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[aria-label='Product Added To Cart']")));
        
        //go to cart
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();      
        
        //waiting till reached cart page
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[normalize-space()='Checkout'])[1]")));
        
        //In cart, check that the product added is in cart or not
        List <WebElement> productsInCart = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = productsInCart.stream().anyMatch(selectedProduct-> selectedProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
        
        //click on checkout 
        driver.findElement(By.xpath("(//button[normalize-space()='Checkout'])[1]")).click();    
        
        Thread.sleep(3000);
        
        //filled country
        Actions act = new Actions(driver);
        act.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"India").build().perform(); 

        // Use JavaScript Executor to scroll down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
            
        //waiting till results will visible by enter India in country dynamic dropdown 
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        
        try {
            Thread.sleep(5000);  // Allow some time for suggestions to appear
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        act.sendKeys(Keys.ARROW_DOWN).pause(500)  // Move to first suggestion
        .sendKeys(Keys.ARROW_DOWN).pause(500)  // Move to second suggestion (India)
        .sendKeys(Keys.ENTER)                  // Select India
        .build()
        .perform();
        
        //click on button India and then click on place order 
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2][normalize-space()='India']")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();
        
        // Clean up (optional)
        driver.quit();
 }
}