/**
 * 
 */
package com.dvd.renter.gui;

import java.awt.Frame;
import java.awt.Window;

/**
 * @author Sewwandi
 *
 */
public class FrmMain extends Window {

	private static final long serialVersionUID = 1L;

	/**
	 * @param owner
	 */
	public FrmMain(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
	}

}
