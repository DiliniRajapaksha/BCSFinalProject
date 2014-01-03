/**
 * 
 */
package com.dvd.renter.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

import com.dvd.renter.controller.EventController;
import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.dvd.renter.entity.Transaction;
import com.dvd.renter.gui.componant.CustomJButton;
import com.dvd.renter.gui.componant.Label;
import com.dvd.renter.gui.componant.Table;
import com.dvd.renter.gui.componant.TextField;
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
public class FormMain extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FormMain.class);

	private EventController con;

	protected JMenuBar menuBar;
	protected JMenu adminMenu;
	protected JMenu reportsMenu;
	protected JMenu dvdSubmenu;
	protected JMenuItem dvdMenuItem;
	protected JMenuItem usersMenuItem;
	protected JMenuItem loginMenuItem;
	protected JMenuItem dailyReportMenuItem;
	protected JTextField txtCustId;
	protected JTextField txtCustName;
	protected JTextField txtContact;
	protected JTextField txtEmail;
	protected JTextField txtDvdId;
	protected JTextField txtDvdName;
	protected JTextField txtDueDate;
	protected JTextField txtLentDate;
	protected JTextField txtDeposit;
	protected JTextField txtTotalPay;
	protected JTextField txtTotalFine;
	protected JTextField txtOverDueDays;
	protected JTextField txtNoOfDvds;
	protected JTextField txtRefundable;
	protected JTextField txtRent;

	protected JComboBox cmbDvdCategory;
	protected JComboBox cmbDVDLanguage;

	protected JButton btnEditCustomer;
	protected JButton btnAddCustomer;
	protected JButton btnAddtoTransaction;
	protected JButton btnLend;
	protected JButton btnReturn;
	protected JButton btnClear;
	protected JButton btnRemoveTransaction;
	protected JButton btnExit;
	protected JButton btnOk;
	protected JButton btnCancel;

	protected JTable tblCustomer;
	protected JTable tblDvd;
	protected JTable tblTransaction;

	protected GenericTableModel<Customer> customerTableModel;
	protected GenericTableModel<DVD> dvdTableModel;
	protected GenericTableModel<Transaction> transactinTblModel;
	protected Label lblCustId;
	protected Label lblTransFine;
	protected Label lblTransPay;
	protected Label lblTransRefund;
	protected Label lblTransOverDue;
	protected Label lblTransDueDate;
	protected Label lblTransRent;
	protected Label lblTransDeposit;
	protected Label lblTransNoOfDvds;
	protected Label lblDvdCategory;
	protected Label lblDvdLang;
	protected Label lblDvdName;
	protected Label lblDvdCode;
	protected Label lblCustEmail;
	protected Label lblCustContact;
	protected Label lblCustName;
	Font font = Font.decode("Times New Roman-bold-13");

	private void initComponants() throws IOException {

		// panel = new JPanel();
		menuBar = new JMenuBar();
		adminMenu = new JMenu("Administrator");
		dvdMenuItem = new JMenuItem("Inventory");
		dvdMenuItem.addActionListener(con);
		usersMenuItem = new JMenuItem("User Profiles");
		usersMenuItem.addActionListener(con);
		adminMenu.add(dvdMenuItem);
		adminMenu.add(usersMenuItem);
		menuBar.add(adminMenu);
		reportsMenu = new JMenu("Reports");
		dailyReportMenuItem = new JMenuItem("View Daily Report");
		reportsMenu.add(dailyReportMenuItem);
		menuBar.add(reportsMenu);
		loginMenuItem = new JMenuItem("Login");
		loginMenuItem.addActionListener(con);
		adminMenu.add(loginMenuItem);
		lblCustContact = new Label("Contact:");
		lblCustEmail = new Label("Email:");
		lblCustId = new Label("ID:");
		lblCustName = new Label("Name:");
		lblDvdCategory = new Label("Category:");
		lblDvdCode = new Label("Code:");
		lblDvdLang = new Label("Language:");
		lblDvdName = new Label("Name:");
		lblTransDeposit = new Label("Total Deposit:");
		lblTransDueDate = new Label("Due Date:");
		lblTransFine = new Label("Total Fine:");
		lblTransNoOfDvds = new Label("No Of Dvds:");
		lblTransOverDue = new Label("Over Due:");
		lblTransPay = new Label("Total Payable:");
		lblTransRefund = new Label("Total Refundable:");
		lblTransRent = new Label("Total Rent:");
		txtCustId = new TextField();
		txtCustId.setEditable(false);
		txtCustName = new TextField();
		txtCustName.getDocument().addDocumentListener(con);
		txtContact = new TextField();
		txtEmail = new TextField();
		txtDvdId = new TextField();
		txtDvdId.getDocument().addDocumentListener(con);
		txtDvdName = new TextField();
		txtDvdName.getDocument().addDocumentListener(con);
		cmbDvdCategory = new JComboBox(GuiUtil.getComboBoxData("categories"));
		cmbDvdCategory.insertItemAt("Select Category", 0);
		cmbDvdCategory.setSelectedIndex(0);
		cmbDvdCategory.setFont(font);
		cmbDvdCategory.addItemListener(con);
		cmbDVDLanguage = new JComboBox(GuiUtil.getComboBoxData("languages"));
		cmbDVDLanguage.insertItemAt("Select Language", 0);
		cmbDVDLanguage.setSelectedIndex(0);
		cmbDVDLanguage.setFont(font);
		cmbDVDLanguage.addItemListener(con);
		txtDueDate = new TextField();
		txtLentDate = new TextField();
		txtDeposit = new TextField();
		txtTotalPay = new TextField();
		txtTotalFine = new TextField();
		txtOverDueDays = new TextField();
		txtNoOfDvds = new TextField();
		txtRent = new TextField();
		txtRefundable = new TextField();
		btnEditCustomer = new CustomJButton("Edit", con);
		btnEditCustomer.setToolTipText("Edit customer details");
		btnAddCustomer = new CustomJButton("Add", con);
		btnAddCustomer.setToolTipText("Add new customer to the system");
		btnAddtoTransaction = new CustomJButton("Lend", con);
		btnAddtoTransaction.setEnabled(false);
		btnAddtoTransaction
				.setToolTipText("Add this DVD to the Transaction table");
		btnLend = new CustomJButton("Lend", con);
		btnReturn = new CustomJButton("Return", con);
		btnReturn.setEnabled(false);
		btnClear = new CustomJButton("Clear", con);
		btnRemoveTransaction = new CustomJButton("Remove", con);
		btnRemoveTransaction.setEnabled(false);
		btnExit = new CustomJButton("Exit", con);
		btnOk = new CustomJButton("Ok", con);
		btnOk.setEnabled(false);
		btnCancel = new CustomJButton("Cancel", con);
		btnCancel.setEnabled(false);
		btnCancel.setToolTipText("Cancel transaction");
		customerTableModel = new GenericTableModel<Customer>(new Customer());
		dvdTableModel = new com.gui.generictablemodel.GenericTableModel<DVD>(
				new DVD());
		transactinTblModel = new GenericTableModel<Transaction>(
				new Transaction());
		transactinTblModel.addTableModelListener(con);
		tblCustomer = new Table(customerTableModel);
		tblCustomer.setName(Customer.class.getName());

		tblDvd = new Table(dvdTableModel);
		tblDvd.setName(DVD.class.getName());
		tblTransaction = new Table(transactinTblModel);
		tblTransaction.setName(Transaction.class.getName());

	}

	/**
 * 
 */
	public void createAndShow() {
		JFrame frame = new JFrame("DVD Renter");
		frame.setJMenuBar(menuBar);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public FormMain(EventController contr) throws IOException {

		super(new BorderLayout());
		con = contr;
		initComponants();
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		builder.appendColumn("right:pref");
		builder.append(createForm());
		builder.nextLine();
		builder.append(btnExit);
		JPanel panel = new JPanel();
		// panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel.add(builder.getPanel());
		add(panel);
	}

	/**
	 * @return
	 */
	private JPanel createForm() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		Border border = BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED), new EmptyBorder(15,
				20, 10, 20));
		builder.setBorder(border);
		builder.appendColumn("right:pref");
		builder.appendColumn("17dlu");
		builder.appendColumn("fill:max(pref; 150px)");
		builder.appendColumn("5dlu");
		builder.appendColumn("right:pref");

		tblCustomer.setPreferredScrollableViewportSize(new Dimension(500, 115));
		tblCustomer.addMouseListener(con);
		JScrollPane custScrollPane = new JScrollPane(tblCustomer);
		tblCustomer.setFillsViewportHeight(true);

		tblDvd.setPreferredScrollableViewportSize(new Dimension(500, 115));
		tblDvd.addMouseListener(con);
		JScrollPane dvdScrollPane = new JScrollPane(tblDvd);
		tblDvd.setFillsViewportHeight(true);

		builder.appendSeparator("Customer");
		builder.append(createCustomer(), custScrollPane);
		builder.nextLine();

		builder.appendSeparator("DVD");
		builder.append(createDvd(), dvdScrollPane);
		builder.nextLine();

		builder.appendSeparator("Transaction");
		builder.append(createTransaction(), createTransactionTable());
		builder.nextLine();
		return builder.getPanel();
	}

	/**
	 * @return
	 * 
	 */
	private JPanel createCustomer() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		// builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 200px)");

		builder.append(lblCustId, txtCustId);
		builder.nextLine();
		builder.append(lblCustName, txtCustName);
		builder.nextLine();

		builder.append(lblCustContact, txtContact);
		builder.nextLine();
		// CellConstraints cc = new CellConstraints();

		builder.append(lblCustEmail, txtEmail);
		builder.nextLine();

		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.addGriddedButtons(new JButton[] { btnAddCustomer,
				btnEditCustomer });
		DefaultFormBuilder outBuilder = new DefaultFormBuilder(new FormLayout(
				""));
		outBuilder.appendColumn("right:pref");
		outBuilder.append(builder.getPanel());
		outBuilder.append(btnBuilder.getPanel());

		// builder.append("", btnBuilder.getPanel());

		return outBuilder.getPanel();

	}

	/**
	 * @return
	 * 
	 */
	private JPanel createDvd() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 200px)");

		builder.append(lblDvdCode, txtDvdId);
		builder.nextLine();

		builder.append(lblDvdName, txtDvdName);
		builder.nextLine();

		builder.append(lblDvdLang, cmbDVDLanguage);
		builder.nextLine();

		builder.append(lblDvdCategory, cmbDvdCategory);
		builder.nextLine();
		builder.append("", btnAddtoTransaction);
		return builder.getPanel();

	}

	/**
	 * @return
	 * 
	 */
	private JPanel createTransaction() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		// builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		builder.appendColumn("right:pref");
		builder.appendColumn("3dlu");
		builder.appendColumn("fill:max(pref; 200px)");
		builder.append(lblTransNoOfDvds, txtNoOfDvds);
		builder.nextLine();

		builder.append(lblTransDeposit, txtDeposit);
		builder.nextLine();

		builder.append(lblTransRent, txtRent);
		builder.nextLine();

		builder.append(lblTransDueDate, txtDueDate);
		builder.nextLine();

		builder.append(lblTransOverDue, txtOverDueDays);
		builder.nextLine();

		builder.append(lblTransFine, txtTotalFine);
		builder.nextLine();

		builder.append(lblTransRefund, txtRefundable);
		builder.nextLine();

		builder.append(lblTransPay, txtTotalPay);
		builder.nextLine();

		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.addGriddedButtons(new JButton[] { btnOk, btnCancel });

		DefaultFormBuilder outBuilder = new DefaultFormBuilder(new FormLayout(
				""));
		outBuilder.appendColumn("right:pref");
		outBuilder.append(builder.getPanel());
		outBuilder.append(btnBuilder.getPanel());

		return outBuilder.getPanel();

	}

	/**
	 * @return
	 * 
	 */
	private JPanel createTransactionTable() {

		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
		builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		builder.appendColumn("right:pref");

		tblTransaction.setPreferredScrollableViewportSize(new Dimension(500,
				190));
		tblTransaction.addMouseListener(con);
		JScrollPane transacScrollPane = new JScrollPane(tblTransaction);
		tblTransaction.setFillsViewportHeight(true);

		ButtonBarBuilder btnBuilder = new ButtonBarBuilder(new FormDebugPanel());
		btnBuilder.addGriddedButtons(new JButton[] { btnReturn,
				btnRemoveTransaction, btnClear });
		builder.append(transacScrollPane);
		builder.nextLine();
		builder.append(btnBuilder.getPanel());
		return builder.getPanel();
	}

	public static void main(String[] a) {
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

		// JFrame f = new JFrame("FormLayout: Default Form Builder Example 1");
		// f.setDefaultCloseOperation(2);
		EventController e = EventController.getInstance();
		FormMain m;
		try {
			m = new FormMain(e);
			Controller contr = Controller.getControllerInstance();
			contr.setFormMain(m);
			e.setController(contr);
			m.setCon(e);
			m.createAndShow();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @return the con
	 */
	public EventController getCon() {
		return con;
	}

	/**
	 * @param param_con
	 *            the con to set
	 */
	public void setCon(EventController param_con) {
		con = param_con;
	}

}
