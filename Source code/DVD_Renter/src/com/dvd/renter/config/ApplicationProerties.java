/**
 * 
 */
package com.dvd.renter.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Handler for all application properties from property files.
 * 
 * @author Sewwandi
 * 
 */
public class ApplicationProerties {
	static Properties appProperties = new Properties();

	/**
	 * @throws IOException
	 * 
	 */
	private static void loadProperties() throws IOException {
		InputStream in = ClassLoader
				.getSystemResourceAsStream("Application.properties");
		appProperties.load(in);

	}

	public static String getProperty(String key) throws IOException {
		if (appProperties.isEmpty()) {
			loadProperties();
		}
		return (String) appProperties.get(key);
	}
}
