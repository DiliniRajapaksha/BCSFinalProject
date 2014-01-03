/**
 * 
 */
package com.dvd.renter.config;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sewwandi
 * 
 */
public class ApplicationProertiesTest {

	/**
	 * Test method for
	 * {@link com.dvd.renter.config.ApplicationProerties#loadProperties()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetProperty() throws IOException {
		Assert.assertEquals("Sinhala, English, Tamil, Hindi",
				ApplicationProerties.getProperty("languages"));
	}
}
