package testng;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNGAnnotations {
	
	@BeforeSuite
	public void connectToDataBase() {
		System.out.println("======= @BeforeSuite =======");
		System.out.println("Connecting to data base");
		System.out.println("Connected to data base successfully");
	}
	
	
	@BeforeTest
	public void initializeReport() {
		System.out.println("======= @BeforeTest =======");
		System.out.println("Initializing Report");
		System.out.println("Report Initialization is done");
	}
	
	
	@AfterSuite
	public void closeDataBaseConnection() {
		System.out.println("======= @AfterSuite =======");
		System.out.println("Closing data base connection");
		System.out.println("Closed data base connection successfully");		
	}
	
	@BeforeClass
	public void initializeDriver() {
		System.out.println("======= @BeforeClass =======");
		System.out.println("Initializing Driver");
		System.out.println("Initialized Driver successfully");
	}
	
	@BeforeMethod
	public void login() {
		System.out.println("======= @BeforeMethod =======");
		System.out.println("Login Successful");
	}
	
	@Test
	public void testMethod1() {
		System.out.println("Executing first test case...");
	}
	@Test//(enabled=false)
	public void testMethod2() {
		throw new SkipException("This method is skipped as it is not ready to be executed");
		//System.out.println("Executing second test case...");
	}
	@Test
	public void testMethod3() {
		System.out.println("Executing third test case...");
	}
	@Test//(enabled=false)
	public void testMethod4() {
		throw new SkipException("This method is skipped as it is not ready to be executed");
		//System.out.println("Executing fourth test case...");
	}
	@Test
	public void testMethod5() {
		System.out.println("Executing fifth test case...");
	}
	
	@AfterMethod
	public void logout() {
		System.out.println("======= @AfterMethod =======");
		System.out.println("Logout Successful");
	}
	
	@AfterClass
	public void quitDriver() {
		System.out.println("======= @AfterClass =======");
		System.out.println("Closing Driver");
		System.out.println("Closed Driver successfully");
	}
	
	
	@AfterTest
	public void generateReport() {
		System.out.println("======= @AfterTest =======");
		System.out.println("Generating Test Execution Report");
		System.out.println("Test Execution Report generated successfully");
	}

}
