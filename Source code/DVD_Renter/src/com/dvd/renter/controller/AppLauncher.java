/**
 * 
 */
package com.dvd.renter.controller;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dvd.renter.gui.Controller;
import com.dvd.renter.gui.LoginForm;

/**
 * Launches the application.
 * 
 * @author Sewwandi
 * 
 */
public class AppLauncher {

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				EventController eventController = EventController.getInstance();
				LoginForm loginForm = null;
				try {
					loginForm = new LoginForm(eventController);
				} catch (IOException e) {
					e.printStackTrace();
				}
				eventController.setCurrentGuiInstance(loginForm);
				Controller contr = Controller.getControllerInstance();
				contr.setLogin(loginForm);
				eventController.setController(contr);
				loginForm.createAndShow();
			}
		});

	}

}
