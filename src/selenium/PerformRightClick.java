package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class PerformRightClick {
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, AWTException {
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
		
		// Quit Driver
		driver.quit();
		
		
		
		
	}

}
