package selenium;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class MouseOver {
	
	static JavascriptExecutor js;
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
		
		// Close Driver
		driver.quit();
		
	}
	
	private static void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
