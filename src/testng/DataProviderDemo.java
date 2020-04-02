package testng;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenium.ReadObject;

public class DataProviderDemo {
	
	WebDriver driver;
	JavascriptExecutor js;	
	Properties p;
	ReadObject reader = new ReadObject();
	ExcelInteraction excel = new ExcelInteraction(System.getProperty("user.dir")+"\\TestData","TestData.xlsx");

	
	@BeforeMethod
	public void setUp() {
		// TODO Auto-generated method stub
		p = reader.getConfiguration();
		String browser = p.getProperty("browser");
		String url = p.getProperty("url");
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
		
		//Launch URL
		driver.get(url);
	}
	
	
	@Test(dataProvider="loginDataProvider")
	public void loginToApplication(String username,String password, String expTitle) {
		
		// Login To Application
		
		// Enter User Name
		highlightElement(driver.findElement(By.name("userName")));
		driver.findElement(By.name("userName")).clear();
		driver.findElement(By.name("userName")).sendKeys(username);
		
		// Enter Password
		highlightElement(driver.findElement(By.name("password")));
		 driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(password);
	    
	    // Click on 'signIn'
	    //driver.findElement(By.name("login")).click();
	    highlightElement(driver.findElement(By.name("login")));
	    js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].click();", driver.findElement(By.name("login")));
		
		// Verify Page Title
	    Assert.assertTrue(driver.getTitle().contains(expTitle));
	    System.out.println("Test Case Completed");
	}
	
	@AfterMethod
	public void closeBrowser() {
	
		// Close Browser
		driver.quit();
		
	}
	
	@AfterClass
	public void closeExcel() {
		excel.closeWorkbook();
	}
	
	
	@DataProvider(name="loginDataProvider")
	public Object[][] provideLoginData(){
		Object[][] loginData = new Object[4][3];
		
		/*loginData[0][0] = "mercury";
		loginData[0][1] = "%^&*($#";
		loginData[0][2] = "Sign-on: Mercury Tours";
		
		loginData[1][0] = "%^&*($#";
		loginData[1][1] = "mercury";
		loginData[1][2] = "Sign-on: Mercury Tours";
		
		loginData[2][0] = "admin";
		loginData[2][1] = "admin";
		loginData[2][2] = "Sign-on: Mercury Tours";
		
		loginData[3][0] = "mercury";
		loginData[3][1] = "mercury";
		loginData[3][2] = "Find a Flight: Mercury Tours:";*/
		
		
		int rowcount = excel.getRowCount("Login");
		
		for(int i=1;i<=rowcount;i++) {
			for(int j=0;j<3;j++) {
			loginData[i-1][j] = excel.getCellData("Login", i, j);
			}
		}
		
		return loginData;
	}
	
	private void highlightElement(WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');", element);
		
	}

}
