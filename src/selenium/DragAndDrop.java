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

public class DragAndDrop {

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
		
		// Switch To Frame
		driver.switchTo().frame(3);
		
		List<WebElement> beforeImages = driver.findElements(By.xpath("//ul[@id='gallery']/li/img"));
		System.out.println(beforeImages.size());
		WebElement target = driver.findElement(By.id("trash"));
		
		Actions builder = new Actions(driver);
		for(WebElement image : beforeImages) {
			highlightElement(image);
			//builder.dragAndDrop(image, target).build().perform();
			builder.clickAndHold(image).moveToElement(target).release(target).build().perform();
			Thread.sleep(2000);
		}
		
		List<WebElement> afterImages = driver.findElements(By.xpath("//ul[@class='gallery ui-helper-reset']/li/img"));
		
		if(afterImages.size() == 4) {
			System.out.println("Status -- Passed | Drag and Drop action is successful");
		}else {
			System.out.println("Status -- Failed | Drag and Drop action is not successful");
		}
		
		
		driver.switchTo().defaultContent();
		
		// Quit Driver
		driver.quit();
}
	
	private static void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}
}
