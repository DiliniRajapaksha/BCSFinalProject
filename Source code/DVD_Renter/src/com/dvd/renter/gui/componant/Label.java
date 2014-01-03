/**
 * 
 */
package com.dvd.renter.gui.componant;

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * @author Sewwandi
 * 
 */
public class Label extends JLabel {

	private static final long serialVersionUID = 1L;
	Font font = Font.decode("Times New Roman-bold-13");
	{
		this.setFont(font);
	}

	/**
	 * 
	 */
	public Label() {
		super();
	}

	/**
	 * @param param_image
	 * @param param_horizontalAlignment
	 */
	public Label(Icon param_image, int param_horizontalAlignment) {
		super(param_image, param_horizontalAlignment);
	}

	/**
	 * @param param_image
	 */
	public Label(Icon param_image) {
		super(param_image);
	}

	/**
	 * @param param_text
	 * @param param_icon
	 * @param param_horizontalAlignment
	 */
	public Label(String param_text, Icon param_icon,
			int param_horizontalAlignment) {
		super(param_text, param_icon, param_horizontalAlignment);
	}

	/**
	 * @param param_text
	 * @param param_horizontalAlignment
	 */
	public Label(String param_text, int param_horizontalAlignment) {
		super(param_text, param_horizontalAlignment);
	}

	/**
	 * @param param_text
	 */
	public Label(String param_text) {
		super(param_text);
	}
}
