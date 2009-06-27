package util;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationUtil {
	
	public static final String TEST_CONFIG = "config.properties";
	
	public static Properties getProperties(String fileName) throws IOException{
		Properties properties = new Properties();
		ClassLoader loader = ConfigurationUtil.class.getClassLoader();
		properties.load(loader.getResourceAsStream(fileName));
		
		return properties;
	}
	
	public static Properties getProperties() throws IOException{
		Properties properties = new Properties();
		ClassLoader loader = ConfigurationUtil.class.getClassLoader();
		properties.load(loader.getResourceAsStream(TEST_CONFIG));
		
		return properties;
	}
}

