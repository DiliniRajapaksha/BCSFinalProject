/**
 * 
 */
package com.dvd.renter.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.dvd.renter.controller.EventController;
import com.dvd.renter.entity.DVD;
import com.dvd.renter.gui.componant.CustomJButton;
import com.dvd.renter.gui.componant.Label;
import com.dvd.renter.util.GuiUtil;
import com.gui.generictablemodel.GenericTableModel;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Sewwandi
 * 
 */
public class DvdForm extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JTextField txtDvdId;
	protected JTextField txtDvdName;
	protected JTextField txtNoOfDvds;
	protected JComboBox cmbDvdCategory;
	protected JComboBox cmbDVDLanguage;
	protected Label lblDvdCategory;
	protected Label lblDvdLang;
	protected Label lblDvdName;
	protected Label lblDvdCode;
	protected EventController controller;
	protected JButton btnAdd;
	protected JButton btnCancel;
	protected JButton btnRemove;
	protected JButton btnSave;
	protected JTable tblDvd;
	protected GenericTableModel<DVD> dvdTableModel;
	protected Label lblTransNoOfDvds;

	/**
	 * @throws IOException
	 * 
	 */
	private void init() throws IOException {
		lblDvdCategory = new Label("Category:");
		lblDvdCode = new Label("Code:");
		lblDvdLang = new Label("Language:");
		lblDvdName = new Label("Name:");
		lblTransNoOfDvds = new Label("No Of Dvds:");

		txtDvdId = new JTextField();
		txtDvdName = new JTextField();
		txtNoOfDvds = new JTextField();
		cmbDvdCategory = new JComboBox(GuiUtil.getComboBoxData("categories"));
		cmbDvdCategory.insertItemAt("Select Category", 0);
		cmbDvdCategory.setSelectedIndex(0);
		cmbDvdCategory.addItemListener(controller);
		cmbDVDLanguage = new JComboBox(GuiUtil.getComboBoxData("languages"));
		cmbDVDLanguage.insertItemAt("Select Language", 0);
		cmbDVDLanguage.setSelectedIndex(0);
		cmbDVDLanguage.addItemListener(controller);

		btnAdd = new CustomJButton("Add", controller);
		btnCancel = new CustomJButton("Exit", controller);
		btnRemove = new CustomJButton("Remove", controller);
		btnSave = new CustomJButton("Save", controller);
		btnSave.setEnabled(false);
		dvdTableModel = new com.gui.generictablemodel.GenericTableModel<DVD>(
				new DVD());
		tblDvd = new JTable(dvdTableModel);
		tblDvd.setName(DVD.class.getName());
	}

	private JPanel createDvdFrame() {
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		Border border = BorderFactory.createCompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(20, 20, 20, 20));
		builder.setBorder(border);
		builder.appendColumn("right:pref");
		builder.append(createDvdForm());
		builder.nextLine();
		builder.append(btnAdd);
		builder.nextLine();
		builder.append(createDvdTable());
		builder.nextLine();
		builder.append(btnRemove);
		return builder.getPanel();
	}

	private JPanel createDvdForm() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		builder.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(
				"Enter Dvd Details"), BorderFactory.createEmptyBorder(10, 10,
				10, 10)));
		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 250px)");

		// builder.append(lblDvdCode, txtDvdId);
		// builder.nextLine();

		builder.append(lblDvdName, txtDvdName);
		builder.nextLine();

		builder.append(lblDvdLang, cmbDVDLanguage);
		builder.nextLine();

		builder.append(lblDvdCategory, cmbDvdCategory);
		builder.nextLine();

		builder.append(lblTransNoOfDvds, txtNoOfDvds);
		builder.nextLine();

		return builder.getPanel();
	}

	/**
	 * @return
	 * 
	 */
	private JScrollPane createDvdTable() {
		tblDvd.setPreferredScrollableViewportSize(new Dimension(360, 200));
		tblDvd.addMouseListener(controller);
		JScrollPane dvdScrollPane = new JScrollPane(tblDvd);
		tblDvd.setFillsViewportHeight(true);
		return dvdScrollPane;
	}

	/**
 * 
 */
	public void run() {
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
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setTitle("DVD");

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		builder.setBorder(new EmptyBorder(20, 20, 20, 20));
		builder.appendColumn("right:pref");
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.addGriddedButtons(new JButton[] { btnSave, btnCancel });

		builder.append(createDvdFrame());
		builder.nextLine();
		builder.append(btnBuilder.getPanel());

		add(builder.getPanel());

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

	/**
	 * @param args
	 */
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
		DvdForm form = new DvdForm();
		try {
			form.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.run();
	}

	/**
	 * @param eventController
	 */
	public void setController(EventController eventController) {
		this.controller = eventController;
	}

}
