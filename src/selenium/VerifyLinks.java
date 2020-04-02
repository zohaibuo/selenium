package selenium;

import java.util.ArrayList;
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

public class VerifyLinks {
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
		
		
		
		List<WebElement> navigationLinks = driver.findElements(By.xpath("//ul[@id='superfish-1']/li/a"));
		System.out.println("Total number of navigation links : "+navigationLinks.size());
		
		
		if(navigationLinks.size() == 8) {
			System.out.println("Status -- Passed | Expected number of navigation links are available on the home page of StepIn2It Aplication");
		}else {
			System.out.println("Status -- Failed | ##Expected Navigation Links : 8 | ##Actual Navigation Links : "+navigationLinks.size());
		}
		
		
		ArrayList<String> expLinks = new ArrayList<String>();
		expLinks.add("Home");
		expLinks.add("About Us");
		expLinks.add("Training Program");
		expLinks.add("Corporate Training");
		expLinks.add("Recruitment");
		expLinks.add("Events");
		expLinks.add("FAQ's");
		expLinks.add("Contact Us");
		
		
		// Fetch Link Texts
		ArrayList<String> actLinks = new ArrayList<String>();
		for(WebElement l1 : navigationLinks) {
			highlightElement(l1);
			Thread.sleep(1000);
			System.out.println(l1.getText());
			if(l1.getText().contains("About Us")) {
				actLinks.add(l1.getText().replaceAll("»", "").trim());
				System.out.println(l1.getText().replaceAll("»", "").trim());
				
			}else {
			actLinks.add(l1.getText());
			}
		}
		
		Thread.sleep(2000);
		
		if(expLinks.equals(actLinks)) {
			System.out.println("Status -- Passed | Expected Navigation Links are present");
		}else {
			System.out.println("Status -- Failed | Expected Navigation Links are not present");

		}
		driver.close();
	}	
	
	
	

	private static void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}


}
