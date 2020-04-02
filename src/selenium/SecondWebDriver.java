package selenium;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
public class SecondWebDriver {
	
	static WebDriver driver;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String browser = "IE";
		if(browser.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browser.equals("firefox"))
		{
			System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir")+ "\\drivers\\firefoxdriver.exe");
			driver = new FirefoxDriver();
		}
		else if (browser.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		//To maximize the browser
		driver.manage().window().maximize();
		
		//Launch application under test
		driver.get("http://www.facebook.com");
		
		//Get website title
		System.out.println("The Home Page Title is"+ driver.getTitle());
	}

}
