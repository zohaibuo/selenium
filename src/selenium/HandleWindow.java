package selenium;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class HandleWindow {

	static WebDriver driver;
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
		
		
		System.out.println("Parent Window URL : "+driver.getCurrentUrl());
		
		// Click on "New Browser Window' button
		driver.findElement(By.id("button1")).click();
		
		Thread.sleep(5000);
		
		
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
		
		System.out.println("Child Window URL : "+driver.getCurrentUrl());
		
		// Close Child Window
		driver.close();
		
		// Switch Back to the Parent Window
		driver.switchTo().window(parentWindowHandle);
		
		System.out.println("Parent Window URL : "+driver.getCurrentUrl());
		
		// Close Parent Window
		driver.quit();
		
		
		
		
}
}
