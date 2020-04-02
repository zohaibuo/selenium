package selenium;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class HandleAlert {

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
		
		// Close Browser
		driver.quit();
		
		
	}

}
