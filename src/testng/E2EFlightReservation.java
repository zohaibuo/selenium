package testng;

import java.util.Properties;
// import java.util.Scanner;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import selenium.ReadObject;

public class E2EFlightReservation {
	
	WebDriver driver;
	JavascriptExecutor js;
	SoftAssert assertion = new SoftAssert();
	
	@Parameters({"browser"})
	//@BeforeClass
	@BeforeTest
	public void setUp(String browser) {
		// TODO Auto-generated method stub
	
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
	}
	
	@Parameters({"url","expLoginPageTitle"})	
	@Test(priority=0)
	public void launchApplication(String url, String expLoginPageTitle) {
		// Launch Application Under Test
		
		
		driver.get(url);
		
		
		System.out.println("Browser Title is "+driver.getTitle());
		
		//Thread.sleep(5000);
		// Fetching the actual browser title and comparing with the expected one
		assertion.assertEquals(driver.getTitle(), expLoginPageTitle);
		System.out.println("First Test Case Completed");
		assertion.assertAll();
		
	}
	
	@Parameters({"username","password"})
	@Test(priority=1)
	//@Test(dependsOnMethods="launchApplication")
	public void loginToApplication(String username,String password) {
		
		// Login To Application
		
		// Enter User Name
		highlightElement(driver.findElement(By.name("userName")));
		driver.findElement(By.name("userName")).clear();
		driver.findElement(By.name("userName")).sendKeys(username);
		
		// Enter Password
		highlightElement(driver.findElement(By.name("password")));
		 driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(password);
	    
	    // Click on 'signIn'
	    //driver.findElement(By.name("login")).click();
	    highlightElement(driver.findElement(By.name("login")));
	    js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("login")));
		
		// Verify 'SIGN-OFF' web element
	    highlightElement(driver.findElement(By.linkText("SIGN-OFF")));
	    Assert.assertTrue(driver.findElement(By.linkText("SIGN-OFF")).isDisplayed());
	    System.out.println("Second Test Case Completed");
	}
	
	@Parameters({"flyFrom","flyTo","firstname","lastname","creditnumber","expConfText"})
	@Test(priority=2)
	//@Test(dependsOnMethods="loginToApplication")
	public void bookATicket(String departFrom, String arriveIn, String firstname, String lastname, String ccnumber, String expConfText) throws InterruptedException {
	    // Select 'One way' trip type
	    
	    // driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")).click();
	    highlightElement(driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    // Select Departure From
	    highlightElement(driver.findElement(By.name("fromPort")));
	    Select flyFrom = new Select(driver.findElement(By.name("fromPort")));
	    flyFrom.selectByValue(departFrom);
	    
	    // Select Arrival To
	    highlightElement(driver.findElement(By.name("toPort")));
	    Select flyTo = new Select(driver.findElement(By.name("toPort")));
	    flyTo.selectByVisibleText(arriveIn);
	    
	    // Select Class Preference
	    // driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")).click();
	    highlightElement(driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")));
	    js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='servClass'][@value='Business']")));

	    // Select Airlines
	    highlightElement(driver.findElement(By.name("airline")));
	    Select airLinePref = new Select(driver.findElement(By.name("airline")));
	    airLinePref.selectByIndex(1);
	    
	    // Click on 'CONTINUE'
	    highlightElement(driver.findElement(By.name("findFlights")));
	    // driver.findElement(By.name("findFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("findFlights")));

	    
	    // Click on 'CONTINUE'
	    highlightElement(driver.findElement(By.name("reserveFlights")));
	    // driver.findElement(By.name("reserveFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("reserveFlights")));


	    
	    // Enter First Name
	    highlightElement(driver.findElement(By.name("passFirst0")));
	    driver.findElement(By.name("passFirst0")).clear();
	    driver.findElement(By.name("passFirst0")).sendKeys(firstname);
	    
	    // Enter Last Name
	    highlightElement(driver.findElement(By.name("passLast0")));
	    driver.findElement(By.name("passLast0")).clear();
	    driver.findElement(By.name("passLast0")).sendKeys(lastname);
	    
	    // Enter Credit Card Number
	    highlightElement(driver.findElement(By.name("creditnumber")));
	    driver.findElement(By.name("creditnumber")).clear();
	    driver.findElement(By.name("creditnumber")).sendKeys(ccnumber);
	    
	    Thread.sleep(3000);
	    
	    // Click on 'SECURE PURCHASE'
	    highlightElement(driver.findElement(By.name("buyFlights")));
	    // driver.findElement(By.name("buyFlights")).click();
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("buyFlights")));
	    Thread.sleep(3000);
	    // Verify Booking Confirmation Text
	    highlightElement(driver.findElement(By.xpath("//font[@size='+1']")));
	    String actConfText = driver.findElement(By.xpath("//font[@size='+1']")).getText();
	    System.out.println("Booking Confirmation Text : "+actConfText);
	    Assert.assertTrue(actConfText.contains(expConfText)) ;
	    System.out.println("Third Test Case Completed");
	   
	}
	
	
	
	//@AfterClass
	@AfterTest
	public void closeBrowser() {
	
		// Close Browser
		driver.quit();
		
	}	
		

	
	
	
	private void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
