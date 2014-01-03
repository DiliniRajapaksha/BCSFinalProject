/**
 * 
 */
package com.dvd.renter.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.dvd.renter.config.ApplicationProerties;
import com.dvd.renter.gui.Controller;

/**
 * @author Sewwandi
 * 
 */
public class GuiUtil {

	private static Logger LOGGER = Logger.getLogger(Controller.class);

	public static String[] getComboBoxData(String key) throws IOException {
		String[] values = new String[] {};
		try {
			values = ApplicationProerties.getProperty(key).split(",");
		} catch (IOException e) {
			LOGGER
					.error(
							"Could not read combo box values from application properties",
							e);
			throw e;
		}
		return values;

	}
}
