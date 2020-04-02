package testng;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import selenium.ReadObject;

public class TestNgExecution {
	
	JavascriptExecutor js;
	WebDriver driver;
	Properties p;
	
	@BeforeMethod
	public void setUp() {
		// TODO Auto-generated method stub
		ReadObject reader = new ReadObject();
		p = reader.getConfiguration();
		
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
	}	
		
		
		@Test
		public void perfromMouseOver() throws InterruptedException {
		// Launch Application Under Test
		String url = p.getProperty("mouseOverUrl");
		driver.get(url);
		
		
		// Close Overlay
		driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']")).click();
		
		// Perform Mouse Over
		
		List<WebElement> menuItems = driver.findElements(By.xpath("//ul[@class='_114Zhd']/li/span"));
		
		Actions builder = new Actions(driver);
		
		for(WebElement item : menuItems) {
			highlightElement(item);
			builder.moveToElement(item).build().perform();
			Thread.sleep(2000);
		}
		
		
		builder.moveToElement(driver.findElement(By.xpath("//span[text()='Men']"))).build().perform();
		Thread.sleep(1000);
		
		highlightElement(driver.findElement(By.linkText("T-Shirts")));
		driver.findElement(By.linkText("T-Shirts")).click();
		
		Thread.sleep(5000);
		
		if(driver.getTitle().contains("T-Shirts for Men")) {
			System.out.println("Status -- Passed | T-Shirts for Men page has been displayed successfully");
		}else {
			System.out.println("Status -- Failed | T-Shirts for Men page has not been displayed successfully");

		}
		}
		
	@Test
	public void performRightClick() throws InterruptedException, AWTException {
		// Launch Application Under Test
				String url = p.getProperty("rightClickurl");
				driver.get(url);
				
				
				Actions builder = new Actions(driver);
				
				// Perform Right Click
				
				WebElement element = driver.findElement(By.linkText("Gmail"));
				builder.contextClick(element).build().perform();
				
				Thread.sleep(2000);
				
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				Thread.sleep(1000);
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				Thread.sleep(1000);
				Thread.sleep(1000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				Thread.sleep(5000);
				
				if(driver.getWindowHandles().size() == 2) {
					System.out.println("Status -- Passed | A new browser has been opened");
				}else {
					System.out.println("Status -- Failed | A new browser has not been opened");
				}
				
				
				String parentWindowHandle = driver.getWindowHandle();
				System.out.println("Parent Window Reference : "+parentWindowHandle);
				
				
				Set<String> handles = driver.getWindowHandles();
				String popupWindowHandle = null;
				
				for(String handle : handles){
					
					System.out.println(handle);
					
					if(!handle.equals(parentWindowHandle)) {
						popupWindowHandle = handle;
					}
					
					
				}
				// Switch To Child Window
				driver.switchTo().window(popupWindowHandle);
				
				
				if(driver.getTitle().contains("Gmail")) {
					System.out.println("Status -- Passed | Gmail application has been opened in a new browser window");
				}else {
					System.out.println("Status -- Failed | Gmail application has not been opened in a new browser window");
				}
				
				// Close Child Window
				driver.close();
				
				// Switch Back to the Parent Window
				driver.switchTo().window(parentWindowHandle);
	}
	
	@Test
	public void handleAlert() throws InterruptedException {
		// Launch Application Under Test
				String url = p.getProperty("handleAlertUrl");
				driver.get(url);
				
				// Switch To Frame
				driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
				
				// Click on 'Try it' button
				driver.findElement(By.xpath("//button[text()='Try it']")).click();
				
				Thread.sleep(3000);
				
				
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				
				System.out.println("Alert Text : "+alertText);
				
				
				// Enter Name
				alert.sendKeys("Zohaib");
				
				// Click on 'OK'
				alert.accept();
				
				// Click on 'Cancel'
				//alert.dismiss();
				Thread.sleep(3000);
				// Fetch the displayed Text
				String actText = driver.findElement(By.id("demo")).getText();
				if(actText.equals("Hello Zohaib! How are you today?")) {
					System.out.println("Status -- Passed | Displayed Text is correct");
				}else {
					System.out.println("Status -- Failed | Displayed Text is not correct");
					
				}
				
				// Switch back to the Parent Page
				
				driver.switchTo().defaultContent();
	}
	@AfterMethod
	public void closeDriver() {
		// Close Driver
		driver.quit();
		
	}
	
	private void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
