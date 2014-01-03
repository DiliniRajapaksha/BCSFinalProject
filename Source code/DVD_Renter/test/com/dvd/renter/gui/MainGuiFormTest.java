/**
 * 
 */
package com.dvd.renter.gui;

import javax.swing.JFrame;

import org.junit.Test;

/**
 * @author Sewwandi
 * 
 */
public class MainGuiFormTest {

	/**
	 * Test method for {@link com.dvd.renter.gui.MainGuiForm#createCustomer()}.
	 */
	@Test
	public void testCreateCustomer() {
		MainGuiForm mainGui = new MainGuiForm();
		JFrame frame = new JFrame();
		frame.setContentPane(mainGui.createCustomer());
		frame.setVisible(true);
		frame.pack();

	}

}
