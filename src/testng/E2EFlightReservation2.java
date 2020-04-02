package testng;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import selenium.TakeScreenshot;


public class E2EFlightReservation2 {
	
	WebDriver driver;
	JavascriptExecutor js;
	SoftAssert assertion = new SoftAssert();
	ExcelInteraction excel = new ExcelInteraction(System.getProperty("user.dir")+"\\TestData","FR.xlsx");
	ExtentTest logger;
	ExtentReports extent;
	ExtentHtmlReporter htmlReporter;
	String screenShotPath;
	
	@BeforeClass
	public void setUp() throws UnknownHostException {
		
		
		// Extent Report Setup
		extent = new ExtentReports();
		File reportFile = new File(System.getProperty("user.dir")+"\\extent-report\\TestExecutionReport.html");
		htmlReporter = new ExtentHtmlReporter(reportFile);
		
		htmlReporter.config().setReportName("E2E Regression Test");
		htmlReporter.config().setDocumentTitle("Extent Report : Flight Reservation");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent.attachReporter(htmlReporter);
		
		
	// TODO Auto-generated method stub
		String browser = excel.getCellData("LaunchApplication", 1, 0);
		
		if(browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // Implicit Wait
		// sc.close();
		// To maximize the Browser
		driver.manage().window().maximize();
		
		
		// Provide System Information
		extent.setSystemInfo("Project", "Flight Reservation");
		extent.setSystemInfo("Browser", browser);
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User", System.getProperty("user.name"));
		InetAddress myhost = InetAddress.getLocalHost();
		extent.setSystemInfo("Host Name", myhost.getHostName());
		
	}
	
	@BeforeMethod
	public void getMethodName(Method method) {
		logger = extent.createTest(method.getName());
	}
	
	@Test(priority=0)
	public void launchApplication() {
		
		
		// Launch Application Under Test
		logger.info("Launching Flight Reservation Application...");
		String url = excel.getCellData("LaunchApplication", 1, 1);
		driver.get(url);
		logger.info("Launched Flight Reservation Application...");
		String expLoginPageTitle = excel.getCellData("LaunchApplication", 1, 2);
		System.out.println("Browser Title is "+driver.getTitle());
		
		//Thread.sleep(5000);
		// Fetching the actual browser title and comparing with the expected one
		assertion.assertEquals(driver.getTitle(), expLoginPageTitle);
		logger.info("First Test Case Completed");
		assertion.assertAll();
		
	}
	
	
	@Test(priority=1)
	//@Test(dependsOnMethods="launchApplication")
	public void loginToApplication() {
		
		// Login To Application
		String username = excel.getCellData("Login", 1, 0);
		// Enter User Name
		logger.info("Entering '"+username+"' in the 'UserName' edit field...");
		highlightElement(driver.findElement(By.name("userName")));
		driver.findElement(By.name("userName")).clear();
		driver.findElement(By.name("userName")).sendKeys(username);
		logger.info("Entered '"+username+"' in the 'UserName' edit field...");
		// Enter Password
		String password = excel.getCellData("Login", 1, 1);
		logger.info("Entering '"+password+"' in the 'Password' edit field...");
		highlightElement(driver.findElement(By.name("password")));
		driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(password);
	    logger.info("Entering '"+password+"' in the 'Password' edit field...");
	    
	    // Click on 'signIn'
	    //driver.findElement(By.name("login")).click();
	    logger.info("Clicking on 'Sign In'...");
	    highlightElement(driver.findElement(By.name("login")));
	    js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("login")));
	    logger.info("Clicked on 'Sign In'...");
		// Verify 'SIGN-OFF' web element
	    logger.info("Verifying 'SIGN-OFF' web element...");
	    highlightElement(driver.findElement(By.linkText("SIGN-OFF")));
	    Assert.assertTrue(driver.findElement(By.linkText("SIGN-OFF")).isDisplayed());
	    logger.info("Verified 'SIGN-OFF' web element...");
	    logger.info("Second Test Case Completed");
	}
	
	
	@Test(priority=2)
	//@Test(dependsOnMethods="loginToApplication")
	public void bookATicket() throws InterruptedException {
	    // Select 'One way' trip type
	    
	    // driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")).click();
	    highlightElement(driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    // Select Departure From 
	    String departFrom = excel.getCellData("BookATicket", 1, 0);
	    logger.info("Selecting '"+departFrom+"' from the 'Departing From' drop down...");

	    highlightElement(driver.findElement(By.name("fromPort")));
	    Select flyFrom = new Select(driver.findElement(By.name("fromPort")));
	    flyFrom.selectByValue(departFrom);
	    logger.info("Selected '"+departFrom+"' from the 'Departing From' drop down...");
	    // Select Arrival To
	    String arriveIn = excel.getCellData("BookATicket", 1, 1);
	    logger.info("Selecting '"+arriveIn+"' from the 'Arriving In' drop down...");
	    highlightElement(driver.findElement(By.name("toPort")));
	    Select flyTo = new Select(driver.findElement(By.name("toPort")));
	    flyTo.selectByVisibleText(arriveIn);
	    logger.info("Selected '"+arriveIn+"' from the 'Arriving In' drop down...");
	    // Select Class Preference
	    // driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")).click();
	    highlightElement(driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")));
	    js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")));

	    // Select Airlines
	    highlightElement(driver.findElement(By.name("airline")));
	    Select airLinePref = new Select(driver.findElement(By.name("airline")));
	    airLinePref.selectByIndex(1);
	    
	    // Click on 'CONTINUE'
	    logger.info("Clicking on 'CONTINUE'...");
	    highlightElement(driver.findElement(By.name("findFlights")));
	    // driver.findElement(By.name("findFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("findFlights")));
	    logger.info("Clicked on 'CONTINUE'...");
	    
	    // Click on 'CONTINUE'
	    logger.info("Clicking on 'CONTINUE'...");
	    highlightElement(driver.findElement(By.name("reserveFlights")));
	    // driver.findElement(By.name("reserveFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("reserveFlights")));
	    logger.info("Clicked on 'CONTINUE'...");

	    
	    // Enter First Name
	    String firstname = excel.getCellData("BookATicket", 1, 2);
	    logger.info("Entering '"+firstname+"' in the 'First Name' edit field...");

	    highlightElement(driver.findElement(By.name("passFirst0")));
	    driver.findElement(By.name("passFirst0")).clear();
	    driver.findElement(By.name("passFirst0")).sendKeys(firstname);
	    logger.info("Entered'"+firstname+"' in the 'First Name' edit field...");

	    // Enter Last Name
	    String lastname = excel.getCellData("BookATicket", 1, 3);
	    logger.info("Entering '"+lastname+"' in the 'Last Name' edit field...");

	    highlightElement(driver.findElement(By.name("passLast0")));
	    driver.findElement(By.name("passLast0")).clear();
	    driver.findElement(By.name("passLast0")).sendKeys(lastname);
	    logger.info("Entered '"+lastname+"' in the 'Last Name' edit field...");

	    // Enter Credit Card Number
	    String ccnumber = excel.getCellData("BookATicket", 1, 4);
	    logger.info("Entering '"+ccnumber+"' in the 'Credit Number' edit field...");

	    highlightElement(driver.findElement(By.name("creditnumber")));
	    driver.findElement(By.name("creditnumber")).clear();
	    driver.findElement(By.name("creditnumber")).sendKeys(ccnumber);
	    logger.info("Entered '"+ccnumber+"' in the 'Credit Number' edit field...");
	    Thread.sleep(3000);
	    
	    // Click on 'SECURE PURCHASE'
	    logger.info("Clicking on 'SECURE PURCHASE'");
	    highlightElement(driver.findElement(By.name("buyFlights")));
	    // driver.findElement(By.name("buyFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("buyFlights")));
	    Thread.sleep(3000);
	    logger.info("Clicked on 'SECURE PURCHASE'");
	    // Verify Booking Confirmation Text
	    logger.info("Verifying Booking Confirmation Text...");
	    String expConfText = excel.getCellData("BookATicket", 1, 5);
	    highlightElement(driver.findElement(By.xpath("//font[@size='+1']")));
	    String actConfText = driver.findElement(By.xpath("//font[@size='+1']")).getText();
	    System.out.println("Booking Confirmation Text : "+actConfText);
	    Assert.assertTrue(actConfText.contains(expConfText)) ;
	    logger.info("Verified Booking Confirmation Text...");
	   logger.info("Third Test Case Completed");
	   
	}
	
	@Test(priority=3)
	public void testFail() {
		Assert.assertTrue(false);
	}
	
	@Test(priority=4)
	public void testSkip() {
		throw new SkipException("This method was not ready to be executed");
	}
	
	@AfterMethod
	public void generateStatus(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.SUCCESS) {
			logger.pass(MarkupHelper.createLabel(result.getName()+"--> Passed", ExtentColor.GREEN));
			screenShotPath = TakeScreenshot.captureScreenShot(driver, result.getName());
			logger.addScreenCaptureFromPath(screenShotPath);
			
		}else if(result.getStatus() == ITestResult.FAILURE) {
			logger.fail(MarkupHelper.createLabel(result.getName()+"--> Failed", ExtentColor.RED));
			logger.fail(result.getThrowable());
			screenShotPath = TakeScreenshot.captureScreenShot(driver, result.getName());
			logger.addScreenCaptureFromPath(screenShotPath);
		}else if(result.getStatus() == ITestResult.SKIP) {
			logger.skip(MarkupHelper.createLabel(result.getName()+"-->Skipped", ExtentColor.YELLOW));
			logger.skip(result.getThrowable());
		}
	}
	
	//@AfterClass
	@AfterTest
	public void closeBrowser() {
	// Close Excel File
	excel.closeWorkbook();
	// Generate Report
	extent.flush();
	// Close Browser
	driver.quit();
		
	}	
		

	
	
	
	private void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
