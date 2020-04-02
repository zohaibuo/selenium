package selenium;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class PageScroll {
	static JavascriptExecutor js;
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, IOException {
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
		
		Thread.sleep(3000);
		// Scroll Down
		js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,3000)", "");
		Thread.sleep(3000);
		TakeScreenshot.captureScreenShot(driver, "scrollDown");
		// Scroll Up
		js.executeScript("window.scrollBy(0,-3000)", "");
		Thread.sleep(3000);
		TakeScreenshot.captureScreenShot(driver, "scrollUp");
		// Scroll to an element
		WebElement element = driver.findElement(By.xpath("//a[text()='LEARN HOW TO']"));
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(3000);
		TakeScreenshot.captureScreenShot(driver, "scrollToElement");
		// Scroll to the bottom of the page
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)","");
		Thread.sleep(5000);
		TakeScreenshot.captureScreenShot(driver, "scrollToBottom");
		driver.quit();
	}

}
