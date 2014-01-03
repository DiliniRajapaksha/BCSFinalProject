/**
 * 
 */
package com.dvd.renter.gui.componant;

import java.awt.Font;

import javax.swing.JButton;

import com.dvd.renter.controller.EventController;

/**
 * Custom font and actionListner added in the constructor.
 * 
 * @author Sewwandi
 */
public class CustomJButton extends JButton {

	private static final long serialVersionUID = 1L;
	private EventController eventController;
	Font font = Font.decode("Times New Roman-bold-13");
	{
		this.setFont(font);
	}

	/**
	 * @param param_eventController
	 *            the actionListner object
	 */
	public CustomJButton(String text, EventController param_eventController) {

		super(text);
		eventController = param_eventController;
		if (eventController != null) {
			addActionListener(eventController);
		}

	}

}
