package SeleniumFrameworkDesign.TestComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SeleniumFrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

// BaseTest class is responsible for setting up and tearing down the WebDriver instance
public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    /**
     * Method to initialize the WebDriver based on the browser specified in the properties file.
     * Reads browser preference from GlobalData.properties and sets up the WebDriver accordingly.
     * @return Initialized WebDriver instance
     * @throws IOException if the properties file cannot be read
     */
    public WebDriver InitializeDriver() throws IOException {
        // Load properties file
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/SeleniumFrameworkDesign/resources/GlobalData.properties");
        prop.load(fis);

        // Get the browser name from the properties file
        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"):prop.getProperty("browser");
        		//prop.getProperty("browser");
        System.out.println("Browser selected: " + browserName);
        // Initialize the WebDriver based on the specified browser
        if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("chromeheadless")) {            
            WebDriverManager.chromedriver().setup(); // Ensures correct driver setup
            ChromeOptions options = new ChromeOptions();

            if (browserName.equalsIgnoreCase("chromeheadless")) {
                options.addArguments("--headless");
                System.out.println("Running Chrome in headless mode.");
            } else {
                System.out.println("Running Chrome in normal mode.");
            }

            driver = new ChromeDriver(options); // Ensures driver is only initialized once
        } 
        else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } 
        else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "edge.exe");
            driver = new EdgeDriver();
        }
        else {
            throw new RuntimeException("Invalid browser name provided: " + browserName);
        }

        // Verify if driver is still null
        if (driver == null) {
            throw new RuntimeException("WebDriver initialization failed.");
        }
        
        
        
        // Configure implicit wait and maximize browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {
        if (driver == null) {
            System.out.println("Driver is null, cannot capture screenshot.");
            return null;
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        File file = new File(destination);
        FileUtils.copyFile(src, file);
        System.out.println("Screenshot saved at: " + destination);
        return destination;
    }
    
    /**
     * BeforeMethod annotation ensures this method runs before each test.
     * It initializes the WebDriver and navigates to the landing page.
     * @return LandingPage object to perform login and other actions
     * @throws IOException if WebDriver initialization fails
     */
    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = InitializeDriver();
        if (driver == null) {
            System.out.println("WebDriver was not initialized!");
        } else {
            System.out.println("WebDriver initialized successfully.");
        }
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    /**
     * AfterMethod annotation ensures this method runs after each test.
     * It closes the browser to clean up resources.
     */
    @AfterMethod(alwaysRun = true)
    public void closePage() {
        if (driver != null) {
            driver.close();
        } else {
            System.out.println("Driver is null. Cannot close the browser.");
        }
    }
    
    
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        // Convert JSON content to List of HashMaps
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }
		
	
  
}
