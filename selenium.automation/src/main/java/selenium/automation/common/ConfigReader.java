package selenium.automation.common;

/**
 * @Class description : Read the Config file
 * @author  Gopinath Hariharan
 * @version 1.0
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


import org.apache.log4j.Logger;

public class ConfigReader {
	public static Logger logger = Logger.getLogger(ConfigReader.class);
	
	private static void setConfigSyetemProperties(Properties props){
		
		System.setProperty("webdriver.type", props.getProperty("webdriver.type"));
		System.setProperty("urlToTest",  props.getProperty("urlToTest"));
		System.setProperty("defaultPageWaitTime",  props.getProperty("defaultPageWaitTime"));
		System.setProperty("defaultObjectWaitTime",  props.getProperty("defaultObjectWaitTime"));
		System.setProperty("inputExcel",  props.getProperty("inputExcel"));

		
		
	}
	
	public static void loadConfigProperties(String configPath) throws IOException{
		Properties props = new Properties();
		props.load(new FileInputStream(new File(configPath)));
		
		logger.info("webdriver.type =" + props.getProperty("webdriver.type"));
		logger.info("urlToTest =" + props.getProperty("urlToTest"));
		logger.info("defaultObjectWaitTime =" + props.getProperty("defaultObjectWaitTime"));
		logger.info("defaultPageWaitTime =" + props.getProperty("defaultPageWaitTime"));
		logger.info("inputExcel =" + props.getProperty("inputExcel"));

		setConfigSyetemProperties(props);
	}

}
