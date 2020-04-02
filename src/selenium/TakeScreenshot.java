package selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class TakeScreenshot {
	
	public static String captureScreenShot(WebDriver driver, String screenShotName) throws IOException {
		
		TakesScreenshot srcShot = (TakesScreenshot)driver;
		File srcFile = srcShot.getScreenshotAs(OutputType.FILE);
		String dateFormat = new SimpleDateFormat("ddMMYYYY_hhmmss").format(new Date());
		String destination = System.getProperty("user.dir")+"\\screenshots\\"+screenShotName+"_"+dateFormat+".PNG";
		File destFile = new File(destination);
		Files.copy(srcFile, destFile);
		
		return destination;
	}

}
