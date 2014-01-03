/**
 * 
 */
package com.dvd.renter.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import com.dvd.renter.controller.EventController;
import com.dvd.renter.gui.componant.CustomJButton;
import com.dvd.renter.gui.componant.Label;
import com.dvd.renter.util.GuiUtil;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Sewwandi
 * 
 */
public class UserForm extends JFrame {

	private static Logger LOGGER = Logger.getLogger(Controller.class);

	protected JPasswordField txtOldPassword;
	protected JPasswordField txtPassword2;
	protected JPasswordField txtPassword;
	protected Label lblUserName;
	protected Label lblPassword;
	protected Label lblOldPassword;
	protected Label lblPassword2;
	protected JButton btnChangePw;
	protected JButton btnDeleteUser;
	protected JButton btnCancel;
	Font titleFont = Font.decode("Cambria-14");
	protected EventController controller;
	protected Label lblUserRole;
	protected JComboBox cmbUserRole;
	Font font = Font.decode("Times New Roman-bold-13");

	/**
	 * @throws IOException
	 *             if Application.properties file could not be read
	 * 
	 */
	public void init() throws IOException {

		txtPassword = new JPasswordField();
		txtOldPassword = new JPasswordField();
		txtPassword2 = new JPasswordField();
		txtOldPassword = new JPasswordField();
		txtPassword.setFont(txtOldPassword.getFont());
		cmbUserRole = new JComboBox(GuiUtil.getComboBoxData("user_roles"));
		cmbUserRole.insertItemAt("Select User Role", 0);
		cmbUserRole.setSelectedIndex(0);
		cmbUserRole.setFont(font);
		lblOldPassword = new Label("Current Password:");
		lblPassword = new Label("New Password:");
		lblPassword2 = new Label("Confirm New Password:");
		lblUserName = new Label("Username:");
		lblUserRole = new Label("User Role");
		btnChangePw = new CustomJButton("Save", controller);
		// btnChangePw.setEnabled(false);
		btnCancel = new CustomJButton("Cancel", controller);
	}

	public JPanel createLoginPanel() {

		JPanel panel = new JPanel();
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));

		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 200px)");

		builder.append(lblUserRole, cmbUserRole);
		builder.nextLine();
		builder.append(lblOldPassword, txtOldPassword);
		builder.nextLine();
		builder.append(lblPassword, txtPassword);
		builder.nextLine();
		builder.append(lblPassword2, txtPassword2);
		builder.nextLine();
		panel = builder.getPanel();
		Border border = BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Enter User Type and Password"),
				BorderFactory.createEmptyBorder(5, 20, 5, 20));

		panel.setBorder(border);
		return panel;
	}

	private JPanel createLoginBtnPanel() {
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.addGriddedButtons(new JButton[] { btnChangePw, btnCancel });
		return btnBuilder.getPanel();
	}

	public void run() {
		JFrame frame = new JFrame("Change Password");
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		JPanel panel = new JPanel();
		builder.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		builder.appendColumn("right:pref");
		builder.append(createLoginPanel());
		builder.nextLine();
		builder.append(createLoginBtnPanel());
		panel.add(builder.getPanel());
		frame.add(panel);
		frame.pack();
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		frame.setLocation(x, y);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserForm l = new UserForm();
		try {
			l.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		l.run();

	}

	/**
	 * @return the controller
	 */
	public EventController getController() {
		return controller;
	}

	/**
	 * @param param_controller
	 *            the controller to set
	 */
	public void setController(EventController param_controller) {
		controller = param_controller;
	}

}
