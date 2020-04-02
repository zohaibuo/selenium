package selenium;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebTableValidation {
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
		
		int rowCount = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr")).size();
		int colCount = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr[1]/th")).size();
		
		ArrayList<String>expValues = new ArrayList<String>();
		
		expValues.add("Alfreds Futterkiste");
		expValues.add("Maria Anders");
		expValues.add("Germany");
		expValues.add("Centro comercial Moctezuma");
		expValues.add("Francisco Chang");
		expValues.add("Mexico");
		expValues.add("Ernst Handel");
		expValues.add("Roland Mendel");
		expValues.add("Austria");
		expValues.add("Island Trading");
		expValues.add("Helen Bennett");
		expValues.add("UK");
		expValues.add("Laughing Bacchus Winecellars");
		expValues.add("Yoshi Tannamuri");
		expValues.add("Canada");
		expValues.add("Magazzini Alimentari Riuniti");
		expValues.add("Giovanni Rovelli");
		expValues.add("Italy");
		
		ArrayList<String>actValues = new ArrayList<String>();
		for(int i=2;i<=rowCount;i++) {
			for(int j=1;j<=colCount;j++) {
				WebElement cell = driver.findElement(By.xpath("//table[@id='customers']/tbody/tr["+i+"]/td["+j+"]"));
				highlightElement(cell);
				String data = cell.getText().trim();
				System.out.println("Cell Data : "+cell.getText().trim());
				actValues.add(data);
			}
		}
		
		
		if(expValues.equals(actValues)) {
			System.out.println("Status -- Passed | Each and every data in the web table is correct");
		}else {
			System.out.println("Status -- Failed | Each and every data in the web table is not correct");
		}
		
		
		// Close Driver
		driver.quit();
		
	}
	
	private static void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}
	
	
	
}
