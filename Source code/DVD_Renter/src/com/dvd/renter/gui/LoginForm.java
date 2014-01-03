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
import javax.swing.border.Border;

import com.dvd.renter.controller.EventController;
import com.dvd.renter.gui.componant.CustomJButton;
import com.dvd.renter.gui.componant.Label;
import com.dvd.renter.util.GuiUtil;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JComboBox cmbUserRole;
	protected JPasswordField txtPassword;
	protected Label lblUserName;
	protected Label lblPassword;
	protected JButton btnLogin;
	protected JButton btnCancel;
	private EventController controller;
	Font font = Font.decode("Times New Roman-bold-14");

	public void initComponants() throws IOException {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		txtPassword = new JPasswordField();
		lblPassword = new Label("Password:");
		lblUserName = new Label("User Type:");
		btnLogin = new CustomJButton("Login", controller);
		btnCancel = new CustomJButton("Cancel", controller);
		cmbUserRole = new JComboBox(GuiUtil.getComboBoxData("user_roles"));
		cmbUserRole.insertItemAt("Select User Type", 0);
		cmbUserRole.setSelectedIndex(0);
		cmbUserRole.setFont(font);
	}

	public JPanel createLoginPanel() {
		JPanel panel = new JPanel();
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));

		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 150px)");

		builder.append(lblUserName, cmbUserRole);
		builder.nextLine();
		builder.append(lblPassword, txtPassword);
		builder.nextLine();
		panel = builder.getPanel();
		Border border = BorderFactory
				.createCompoundBorder(BorderFactory
						.createTitledBorder("Enter Username and Password"),
						BorderFactory.createEmptyBorder(10, 20, 10, 20));
		panel.setBorder(border);
		panel.getBorder().getBorderInsets(panel).set(90, 20, 20, 20);
		return panel;
	}

	public LoginForm(EventController param_controller) throws IOException {
		controller = param_controller;
		initComponants();
	}

	private JPanel createLoginBtnPanel() {
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.setAlignment(CellConstraints.RIGHT, null);
		btnBuilder.addGriddedButtons(new JButton[] { btnLogin, btnCancel });
		return btnBuilder.getPanel();
	}

	public void createAndShow() {

		this.setTitle("Login");
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		JPanel panel = new JPanel();
		builder.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		builder.appendColumn("right:pref");
		builder.append(createLoginPanel());
		builder.nextLine();
		builder.append(createLoginBtnPanel());
		panel.add(builder.getPanel());
		add(panel);
		pack();
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		setLocation(x, y);
		setVisible(true);

	}

	public EventController getController() {
		return controller;
	}

	public void setController(EventController param_controller) {
		controller = param_controller;
	}
}
