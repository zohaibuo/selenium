package selenium;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

public class DatePicker {
	static JavascriptExecutor js;
	static WebDriver driver;
	 public static void main(String[] args) throws InterruptedException {
	  // TODO Auto-generated method stub
	  System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.get("https://www.spicejet.com/");
	  driver.manage().window().maximize();
	  // Enter Departure From
	  driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).clear();
	  driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).sendKeys("DEL");
	  Thread.sleep(1000);
	  // Enter Arrival To
	  driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).clear();
	  driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).sendKeys("GOI");
	  
	  Thread.sleep(1000);
	  String departDate = "31 December 2019";
	  String departDateArr[] = departDate.split(" ");
	  String month_year = departDateArr[1]+" "+departDateArr[2];
	  String day = departDateArr[0];
	  
	  
	  
	 // selectDate(month_year,day);
	  
	  
	  			// OR 
	  
	  js = (JavascriptExecutor)driver;
	  js.executeScript("arguments[0].setAttribute('value','31-12-2019');", driver.findElement(By.id("ctl00_mainContent_txt_Fromdate")));
	  
	  Thread.sleep(5000);
	     
	  // Click on Flight Icon
	 
	  js.executeScript("arguments[0].click();",driver.findElement(By.name("ctl00$mainContent$btn_FindFlights")));

	     Thread.sleep(5000);
	  if(driver.findElement(By.id("availabilityTable0")).isDisplayed()){
	   System.out.println("Status -- Passed | Available Flights are displayed successfully");
	   
	  }else{
	   System.out.println("Status -- Failed | Available Flights are displayed successfully");
	  }
	    
	  

	 }
	 
	 
	private static void selectDate(String month_year,String day) throws InterruptedException{
	List<WebElement> elements = driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//div[@class='ui-datepicker-title']"));
	  
	  for(int i=0;i<elements.size();i++){
	   String elementText = elements.get(i).getText();
	   if(elementText.equals(month_year)){
	    List<WebElement> days = driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//div[contains(@class,'ui-datepicker-group')]["+(i+1)+"]/table/tbody/tr/td/a"));
	    for(WebElement d : days){
	     if(d.getText().equals(day)){
	      d.click();
	      return;
	     }
	    }
	   }
	  }
	  
	  driver.findElement(By.xpath("//a[@title='Next']")).click();
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//a[@title='Next']")).click();
	  Thread.sleep(2000);
	  selectDate(month_year,day);
	 }

	}

