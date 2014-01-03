package com.dvd.renter.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.dvd.renter.gui.Controller;
import com.dvd.renter.gui.DvdForm;
import com.dvd.renter.gui.FormMain;
import com.dvd.renter.gui.UserForm;

/**
 * Controls all the events.
 * 
 * @author Sewwandi
 * 
 */
public class EventController implements ActionListener, DocumentListener,
		MouseListener, ItemListener, TableModelListener, WindowListener {

	private static Logger LOGGER = Logger.getLogger(EventController.class);
	private Object currentGuiInstance;
	private Controller controller;
	private static EventController eventController;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public static EventController getInstance() {

		if (eventController == null) {
			eventController = new EventController();
		}

		return eventController;

	}

	/**
	 * The constructor has been made private in order to make the
	 * <code>EventController</code> singleton.
	 */
	private EventController() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			if (e.getSource() instanceof JButton) {
				LOGGER.debug("button pressed");
				if (currentGuiInstance instanceof FormMain) {
					controller.btnPressedFormMain(e);
				} else if (currentGuiInstance instanceof DvdForm) {
					controller.btnPressedDvdForm(e);
				} else if (currentGuiInstance instanceof UserForm) {
					controller.btnPressedUserForm(e);
				} else {
					controller.btnPressedLoginForm(e);
				}
			} else if (e.getSource() instanceof JMenuItem) {
				controller.menuItemClicked(e);
			}

		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			LOGGER.error("Password Encryption Error", e1);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		LOGGER.info(new Date() + "insert action performed");
		controller.txtChanged(e);
		LOGGER.info(new Date() + "insert action started");

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		LOGGER.info(new Date() + "remove action performed");
		controller.txtChanged(e);
		LOGGER.info(new Date() + "remove action started");

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals(Customer.class.getName())) {
			controller.customerTableClicked();
		} else if (e.getComponent().getName().equals(DVD.class.getName())) {
			controller.dvdTableClicked();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent param_e) {

		controller.itemChanged(param_e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent param_e) {
		LOGGER.debug("table changed");
		controller.tableChanged(param_e);
	}

	/**
	 * @param currentGuiInstance
	 *            the currentGuiInstance to set
	 */
	public void setCurrentGuiInstance(Object currentGuiInstance) {
		this.currentGuiInstance = currentGuiInstance;
	}

	/**
	 * @return the currentGuiInstance
	 */
	public Object getCurrentGuiInstance() {
		return currentGuiInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent param_e) {

		if (param_e.getSource() instanceof DvdForm) {
			currentGuiInstance = controller.getFormMain();
		} else if (param_e.getSource() instanceof UserForm) {
			currentGuiInstance = controller.getFormMain();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeactivated(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeiconified(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent param_e) {
		// TODO Auto-generated method stub

	}
}
