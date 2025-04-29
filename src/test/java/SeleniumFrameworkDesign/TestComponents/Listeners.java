package SeleniumFrameworkDesign.TestComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import SeleniumFrameworkDesign.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal <ExtentTest> extentTest = new ThreadLocal <ExtentTest>(); 
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestSuccess(result);
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
	    String filePath = null;
	    WebDriver driver = null;

	    try {
	        // Fetch the correct instance of WebDriver
	        BaseTest baseTest = (BaseTest) result.getInstance();
	        driver = baseTest.driver;

	        if (driver != null) {  
	            filePath = getScreenshotPath(result.getMethod().getMethodName(), driver);
	        } else {
	            System.out.println("WebDriver instance is null, cannot take a screenshot.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    if (filePath != null && !filePath.isEmpty()) {
	    	extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	        System.out.println("Screenshot added to report: " + filePath);
	    } else {
	        System.out.println("Screenshot filePath is null or empty, skipping attachment.");
	    }
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onFinish(context);
		extent.flush();
	}
		
		
}
