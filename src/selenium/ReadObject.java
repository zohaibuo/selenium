package selenium;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadObject {
	
	
	Properties p = new Properties();
	
	public Properties getConfiguration() {
		try {
		File file = new File(System.getProperty("user.dir")+"\\src\\config.properties");
		FileInputStream stream = new FileInputStream(file);
		p.load(stream);
		return p;
		}catch(Exception ex) {
			System.out.println("Exception occurred while reading config.properties file : "+ex.getMessage());
			return null;
		}
	}

}
