 /* @author Devansh D. SHARMA  
  * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
  * 
  * Property loader for JDBC Config */


package fr.epita.iam.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	
	Logger log = Logger.getLogger(Config.class);

	
	private static Config instance;

	private final Properties properties;

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	private Config() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(System.getProperty("conf.file.path")));
		} catch (final IOException e) {
			log.error("Could not load the configuratons, Please try again... ");
		}
	}

	public String getProperty(String key) {

		return properties.getProperty(key);

	}

	public boolean containsProperty(String key) {
		return properties.containsKey(key);
	}


}
