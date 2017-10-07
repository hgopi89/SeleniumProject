package selenium.automation.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


import org.apache.log4j.Logger;

public class ConfigReader {
	public static Logger logger = Logger.getLogger(ConfigReader.class);
	
	private static void setConfigSyetemProperties(Properties props){
		
		System.setProperty("webdriver.type", props.getProperty("webdriver.type"));
		System.setProperty("urlToTest",  "urlToTest");
		System.setProperty("os.name", "os.name");
		
	}
	
	public static void loadConfigProperties(String configPath) throws IOException{
		Properties props = new Properties();
		props.load(new FileInputStream(new File(configPath)));
		
		logger.info("webdriver.type    =" + props.getProperty("webdriver.type"));
		logger.info("urlToTest         =" + props.getProperty("urlToTest"));
		logger.info("os.name           =" + props.getProperty("os.name"));

		setConfigSyetemProperties(props);
	}

}
