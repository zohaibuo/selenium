package selenium;

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

public class FirstWebDriverProgram {
	
	static WebDriver driver;
	static JavascriptExecutor js;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		ReadObject reader = new ReadObject();
		Properties p = reader.getConfiguration();
		
		String browser = p.getProperty("browser");
		/*System.out.println("Enter the browser name : ");
		Scanner sc = new Scanner(System.in);
		String browser = sc.nextLine();*/
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
		
		
		// Launch Application Under Test
		String url = p.getProperty("url");
		driver.get(url);
		
		
		System.out.println("Browser Title is "+driver.getTitle());
		
		//Thread.sleep(5000);
		// Fetching the actual browser title and comparing with the expected one
		if(driver.getTitle().equals("Welcome: Mercury Tours")) {
			System.out.println("Status -- Passed | Application has been launched successfully");
		}else {
			System.out.println("Status -- Failed | Application has not been launched successfully");
		}
		
		
		// Login To Application
		
		// Enter User Name
		highlightElement(driver.findElement(By.name("userName")));
		driver.findElement(By.name("userName")).clear();
		driver.findElement(By.name("userName")).sendKeys("mercury");
		
		// Enter Password
		highlightElement(driver.findElement(By.name("password")));
		 driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys("mercury");
	    
	    // Click on 'signIn'
	    //driver.findElement(By.name("login")).click();
	    highlightElement(driver.findElement(By.name("login")));
	    js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("login")));
		
		// Verify 'SIGN-OFF' web element
	    highlightElement(driver.findElement(By.linkText("SIGN-OFF")));
	    if(driver.findElement(By.linkText("SIGN-OFF")).isDisplayed()) {
	    	System.out.println("Status -- Passed | Login Successful");
	    }else {
	    	System.out.println("Status -- Failed | Login Unsuccessful");
	    }
		
	    // Select 'One way' trip type
	    
	    // driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")).click();
	    highlightElement(driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='tripType'][@value='oneway']")));
	    // Select Departure From
	    highlightElement(driver.findElement(By.name("fromPort")));
	    Select flyFrom = new Select(driver.findElement(By.name("fromPort")));
	    flyFrom.selectByValue("London");
	    
	    // Select Arrival To
	    highlightElement(driver.findElement(By.name("toPort")));
	    Select flyTo = new Select(driver.findElement(By.name("toPort")));
	    flyTo.selectByVisibleText("Frankfurt");
	    
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
	    driver.findElement(By.name("passFirst0")).sendKeys("Rohan");
	    
	    // Enter Last Name
	    highlightElement(driver.findElement(By.name("passLast0")));
	    driver.findElement(By.name("passLast0")).clear();
	    driver.findElement(By.name("passLast0")).sendKeys("Oberoi");
	    
	    // Enter Credit Card Number
	    highlightElement(driver.findElement(By.name("creditnumber")));
	    driver.findElement(By.name("creditnumber")).clear();
	    driver.findElement(By.name("creditnumber")).sendKeys("75648756555");
	    
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
	    if(actConfText.contains("itinerary has been booked!")) {
	    //if(driver.getPageSource().contains("itinerary has been booked!")) {
	    	System.out.println("Status -- Passed | Ticket has been booked successfully");
	    }else {
	    	System.out.println("Status -- Failed | Ticket has not been booked successfully");
	    }
	    
		// Close Browser
		driver.close();
		
		
		

	}
	
	
	private static void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
