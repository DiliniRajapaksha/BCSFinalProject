/**
 * 
 */
package com.dvd.renter.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.dvd.renter.controller.EventController;
import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.gui.generictablemodel.GenericTableModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Sewwandi
 * 
 */
public class MainGuiForm extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MainGuiForm.class);

	private EventController con;
	private JPanel panel = new JPanel();
	private JTextField txtCustId = new JTextField(20);
	private JTextField txtCustName = new JTextField(20);
	private JTextField txtContact = new JTextField(20);
	private JTextField txtEmail = new JTextField(20);
	private JTextField txtDvdId = new JTextField(20);
	private JTextField txtDvdName = new JTextField(20);
	private JComboBox cmbDvdCategory = new JComboBox();
	private JComboBox cmbDVDLanguage = new JComboBox();
	private JTextField txtDueDate = new JTextField(20);
	private JTextField txtLentDate = new JTextField(20);
	private JTextField txtDeposit = new JTextField(20);
	private JTextField txtTotalPay = new JTextField(20);
	private JTextField txtTotalFine = new JTextField(20);
	private JTextField txtOverDueDays = new JTextField(20);
	private JButton btnEditCustomer;
	private JButton btnAddCustomer;
	private JButton btnAddtoTransaction;
	private JButton btnLend;
	private JButton btnReturn;
	private JButton btnClear;
	private JButton btnRemoveTransaction;
	private JButton btnExit;
	private JTable tblCustomer;
	private JTable tblDvd;
	private JTable tblTransaction;

	private GenericTableModel<Customer> customerTableModel = new GenericTableModel<Customer>(
			new Customer());
	private GenericTableModel<DVD> dvdTableModel = new GenericTableModel<DVD>(
			new DVD());
	private String[] formLabelsCust = new String[] { "Customer ID:",
			"Customer Name:", "Contact No.:", "Email:" };
	private String[] formLabelsDvd = new String[] { "DVD Id:", "DVD Name:",
			"DVD Category:", "DVD Language:" };
	private String[] formLabelsTrans = new String[] { "Lend Date:",
			"Due Date:", "Deposit:", "Total Fine:", "Total Payable:",
			"Over Due:" };

	public MainGuiForm() {
		super();
	}

	public void init() {

		// JPanel pnlCustomer = new JPanel();
		// JPanel pnlDvd = new JPanel();
		// JPanel pnlTransaction = new JPanel();
		// JPanel pnlCustButton = new JPanel();
		// JPanel pnlTransButton = new JPanel();

		LOGGER.debug("initiating");
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setSize(size.width - 200, 600);
		LOGGER.info("w: " + this.getWidth() + ", h: " + this.getHeight());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);
		setResizable(false);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(createCustomer(), gc);

		GridBagConstraints gcDvd = new GridBagConstraints();
		gcDvd.gridx = 0;
		gcDvd.gridy = 1;
		gcDvd.weightx = 0.5;
		gcDvd.weighty = 0.5;
		gcDvd.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(createDvdForm(), gcDvd);

		tblCustomer = new JTable(customerTableModel);
		tblCustomer.setPreferredScrollableViewportSize(new Dimension(400, 200));
		tblCustomer.setName(Customer.class.getName());
		tblCustomer.addMouseListener(con);
		JScrollPane custScrollPane = new JScrollPane(tblCustomer);
		tblCustomer.setFillsViewportHeight(true);
		GridBagConstraints custGc = new GridBagConstraints();
		custGc.insets = new Insets(20, 20, 20, 20);
		custGc.gridx = 1;
		custGc.gridy = 0;
		custGc.weightx = 1;
		custGc.weighty = 1;
		custGc.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(custScrollPane, custGc);

		tblDvd = new JTable(dvdTableModel);
		tblDvd.setPreferredScrollableViewportSize(new Dimension(400, 200));
		tblDvd.setName(DVD.class.getName());
		tblDvd.addMouseListener(con);
		JScrollPane dvdScrollPane = new JScrollPane(tblDvd);
		tblDvd.setFillsViewportHeight(true);
		GridBagConstraints dvdGc = new GridBagConstraints();
		dvdGc.insets = new Insets(20, 20, 20, 20);
		dvdGc.gridx = 1;
		dvdGc.gridy = 1;
		dvdGc.weightx = 1;
		dvdGc.weighty = 1;
		dvdGc.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(dvdScrollPane, dvdGc);

		JPanel custBtnsPanel = new JPanel();
		custBtnsPanel.setLayout(new BoxLayout(custBtnsPanel, BoxLayout.X_AXIS));
		btnAddCustomer = new JButton("Add");
		btnAddCustomer.addActionListener(con);
		custBtnsPanel.add(btnAddCustomer);
		GridBagConstraints btnGc = new GridBagConstraints();
		btnGc.insets = new Insets(20, 20, 20, 20);
		btnGc.gridx = 0;
		btnGc.gridy = 0;
		btnGc.weightx = 0.5;
		btnGc.weighty = 0.5;

		// btnGc.anchor = GridBagConstraints.LAST_LINE_START;
		panel.add(custBtnsPanel, btnGc);

		GridBagConstraints btnAddDvdGc = new GridBagConstraints();
		btnAddDvdGc.insets = new Insets(20, 20, 20, 20);
		btnAddDvdGc.gridx = 0;
		btnAddDvdGc.gridy = 1;
		btnAddDvdGc.weightx = 0.5;
		btnAddDvdGc.weighty = 0.5;
		btnAddDvdGc.anchor = GridBagConstraints.ABOVE_BASELINE;
		JPanel dvdBtnsPanel = new JPanel();
		// btnAddDvd = new JButton("Add");
		// btnAddDvd.addActionListener(con);
		// btnAddDvd.setActionCommand(Customer.class.getName());
		// dvdBtnsPanel.add(btnAddDvd);
		// panel.add(dvdBtnsPanel, btnAddDvdGc);
		setContentPane(panel);
		// panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

	}

	public JPanel createCustomer() {

		JPanel panelLbls = new JPanel();
		JPanel panelTxts = new JPanel();

		Dimension dimentionRigid = new Dimension(10, 10);
		panelLbls.add(Box.createGlue());

		Dimension dimentionLblRigid = new Dimension(10, 17);
		panelLbls.add(Box.createRigidArea(new Dimension(10, 20)));
		for (int i = 0; i < formLabelsCust.length; i++) {
			JLabel lbl = new JLabel(formLabelsCust[i]);
			lbl.setAlignmentX(RIGHT_ALIGNMENT);
			panelLbls.add(lbl);
			panelLbls.add(Box.createRigidArea(dimentionLblRigid));
		}

		for (int i = 0; i < Customer.class.getFields().length; i++) {

		}
		panelLbls.add(Box.createGlue());
		txtCustName.getDocument().addDocumentListener(con);
		panelTxts.add(Box.createGlue());
		txtCustId.setAlignmentX(RIGHT_ALIGNMENT);
		panelTxts.add(txtCustId);
		txtCustId.setMaximumSize(new Dimension(200, 6));
		panelTxts.add(Box.createRigidArea(dimentionRigid));
		panelTxts.add(txtCustName);
		txtCustName.setMaximumSize(new Dimension(200, 6));
		txtCustName.setAlignmentX(RIGHT_ALIGNMENT);
		panelTxts.add(Box.createRigidArea(dimentionRigid));
		panelTxts.add(txtContact);
		txtContact.setMaximumSize(new Dimension(200, 6));
		txtContact.setAlignmentX(RIGHT_ALIGNMENT);
		panelTxts.add(Box.createRigidArea(dimentionRigid));
		panelTxts.add(txtEmail);
		txtEmail.setAlignmentX(RIGHT_ALIGNMENT);
		txtEmail.setMaximumSize(new Dimension(200, 6));
		panelTxts.add(Box.createGlue());
		JPanel custBtnsPanel = new JPanel();
		custBtnsPanel.setLayout(new BoxLayout(custBtnsPanel, BoxLayout.X_AXIS));
		btnAddCustomer = new JButton("Add");
		btnAddCustomer.addActionListener(con);
		custBtnsPanel.add(btnAddCustomer);
		panelTxts.add(custBtnsPanel);
		JPanel panelTxtLbl = new JPanel();
		panelTxtLbl.add(Box.createGlue());
		panelTxtLbl.add(Box.createRigidArea(dimentionRigid));
		panelTxtLbl.add(Box.createRigidArea(dimentionRigid));
		panelTxtLbl.add(panelLbls);
		// panelTxtLbl.add(Box.createRigidArea(dimentionRigid));
		panelTxtLbl.add(panelTxts);
		panelTxtLbl.add(Box.createGlue());
		panelTxtLbl.setLayout(new BoxLayout(panelTxtLbl, BoxLayout.X_AXIS));
		panelLbls.setLayout(new BoxLayout(panelLbls, BoxLayout.Y_AXIS));
		panelTxts.setLayout(new BoxLayout(panelTxts, BoxLayout.Y_AXIS));
		// panelTxts.setBackground(Color.black);
		// panelTxtLbl.setBackground(Color.red);
		return panelTxtLbl;

	}

	public JPanel createDvdForm() {

		FormLayout layout = new FormLayout("right:pref,10px,left:pref ",
				"pref,4px,pref,4px,pref");
		layout.setRowGroups(new int[][] { { 1, 3, 5 } });
		JPanel panelDvd = new JPanel(layout);
		CellConstraints cc = new CellConstraints();
		panelDvd.add(new JLabel(formLabelsDvd[0]), cc.xy(1, 1));
		panelDvd.add(txtDvdName, cc.xyw(3, 1, 1));
		panelDvd.add(new JLabel(formLabelsDvd[1]), cc.xy(1, 3));
		panelDvd.add(cmbDvdCategory, cc.xyw(3, 3, 1));
		panelDvd.add(new JLabel(formLabelsDvd[2]), cc.xy(1, 5));
		panelDvd.add(cmbDVDLanguage, cc.xyw(3, 5, 1));
		return panelDvd;

	}

	public EventController getCon() {
		return con;
	}

	public JTextField getTxtContact() {
		return txtContact;
	}

	public void setTxtContact(JTextField txtContact) {
		this.txtContact = txtContact;
	}

	public JTable getTable() {
		return tblCustomer;
	}

	public void setTable(JTable table) {
		this.tblCustomer = table;
	}

	/**
	 * @return the txtCustId
	 */
	public JTextField getTxtCustId() {
		return txtCustId;
	}

	/**
	 * @param txtCustId
	 *            the txtCustId to set
	 */
	public void setTxtCustId(JTextField txtCustId) {
		this.txtCustId = txtCustId;
	}

	/**
	 * @return the txtCustName
	 */
	public JTextField getTxtCustName() {
		return txtCustName;
	}

	/**
	 * @param txtCustName
	 *            the txtCustName to set
	 */
	public void setTxtCustName(JTextField txtCustName) {
		this.txtCustName = txtCustName;
	}

	/**
	 * @return the txtDvdId
	 */
	public JTextField getTxtDvdId() {
		return txtDvdId;
	}

	/**
	 * @param txtDvdId
	 *            the txtDvdId to set
	 */
	public void setTxtDvdId(JTextField txtDvdId) {
		this.txtDvdId = txtDvdId;
	}

	/**
	 * @return the txtDvdName
	 */
	public JTextField getTxtDvdName() {
		return txtDvdName;
	}

	/**
	 * @param txtDvdName
	 *            the txtDvdName to set
	 */
	public void setTxtDvdName(JTextField txtDvdName) {
		this.txtDvdName = txtDvdName;
	}

	/**
	 * @return the customerTableModel
	 */
	public GenericTableModel<Customer> getCustomerTableModel() {
		return customerTableModel;
	}

	/**
	 * @param customerTableModel
	 *            the customerTableModel to set
	 */
	public void setCustomerTableModel(
			GenericTableModel<Customer> customerTableModel) {
		this.customerTableModel = customerTableModel;
	}

	/**
	 * @return the dvdTableModel
	 */
	public GenericTableModel<DVD> getDvdTableModel() {
		return dvdTableModel;
	}

	/**
	 * @param dvdTableModel
	 *            the dvdTableModel to set
	 */
	public void setDvdTableModel(GenericTableModel<DVD> dvdTableModel) {
		this.dvdTableModel = dvdTableModel;
	}

	/**
	 * @param btnAdd
	 *            the btnAdd to set
	 */
	public void setBtnAdd(JButton btnAdd) {
		this.btnAddCustomer = btnAdd;
	}

	/**
	 * @return the btnAdd
	 */
	public JButton getBtnAdd() {
		return btnAddCustomer;
	}

	@Override
	public void run() {

		init();
		this.setVisible(true);

	}

	/**
	 * @param param_con
	 *            the con to set
	 */
	public void setCon(EventController param_con) {
		con = param_con;
	}

	/**
	 * @return the txtEmail
	 */
	public JTextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * @param param_txtEmail
	 *            the txtEmail to set
	 */
	public void setTxtEmail(JTextField param_txtEmail) {
		txtEmail = param_txtEmail;
	}

	/**
	 * @return the cmbDvdCategory
	 */
	public JComboBox getCmbDvdCategory() {
		return cmbDvdCategory;
	}

	/**
	 * @param param_cmbDvdCategory
	 *            the cmbDvdCategory to set
	 */
	public void setCmbDvdCategory(JComboBox param_cmbDvdCategory) {
		cmbDvdCategory = param_cmbDvdCategory;
	}

	/**
	 * @return the cmbDVDLanguage
	 */
	public JComboBox getCmbDVDLanguage() {
		return cmbDVDLanguage;
	}

	/**
	 * @param param_cmbDVDLanguage
	 *            the cmbDVDLanguage to set
	 */
	public void setCmbDVDLanguage(JComboBox param_cmbDVDLanguage) {
		cmbDVDLanguage = param_cmbDVDLanguage;
	}

	/**
	 * @return the txtDueDate
	 */
	public JTextField getTxtDueDate() {
		return txtDueDate;
	}

	/**
	 * @param param_txtDueDate
	 *            the txtDueDate to set
	 */
	public void setTxtDueDate(JTextField param_txtDueDate) {
		txtDueDate = param_txtDueDate;
	}

	/**
	 * @return the txtLentDate
	 */
	public JTextField getTxtLentDate() {
		return txtLentDate;
	}

	/**
	 * @param param_txtLentDate
	 *            the txtLentDate to set
	 */
	public void setTxtLentDate(JTextField param_txtLentDate) {
		txtLentDate = param_txtLentDate;
	}

	/**
	 * @return the txtDeposit
	 */
	public JTextField getTxtDeposit() {
		return txtDeposit;
	}

	/**
	 * @param param_txtDeposit
	 *            the txtDeposit to set
	 */
	public void setTxtDeposit(JTextField param_txtDeposit) {
		txtDeposit = param_txtDeposit;
	}

	/**
	 * @return the txtTotalPay
	 */
	public JTextField getTxtTotalPay() {
		return txtTotalPay;
	}

	/**
	 * @param param_txtTotalPay
	 *            the txtTotalPay to set
	 */
	public void setTxtTotalPay(JTextField param_txtTotalPay) {
		txtTotalPay = param_txtTotalPay;
	}

	/**
	 * @return the txtTotalFine
	 */
	public JTextField getTxtTotalFine() {
		return txtTotalFine;
	}

	/**
	 * @param param_txtTotalFine
	 *            the txtTotalFine to set
	 */
	public void setTxtTotalFine(JTextField param_txtTotalFine) {
		txtTotalFine = param_txtTotalFine;
	}

	/**
	 * @return the txtOverDueDays
	 */
	public JTextField getTxtOverDueDays() {
		return txtOverDueDays;
	}

	/**
	 * @param param_txtOverDueDays
	 *            the txtOverDueDays to set
	 */
	public void setTxtOverDueDays(JTextField param_txtOverDueDays) {
		txtOverDueDays = param_txtOverDueDays;
	}

	/**
	 * @return the btnEditCustomer
	 */
	public JButton getBtnEditCustomer() {
		return btnEditCustomer;
	}

	/**
	 * @param param_btnEditCustomer
	 *            the btnEditCustomer to set
	 */
	public void setBtnEditCustomer(JButton param_btnEditCustomer) {
		btnEditCustomer = param_btnEditCustomer;
	}

	/**
	 * @return the btnAddCustomer
	 */
	public JButton getBtnAddCustomer() {
		return btnAddCustomer;
	}

	/**
	 * @param param_btnAddCustomer
	 *            the btnAddCustomer to set
	 */
	public void setBtnAddCustomer(JButton param_btnAddCustomer) {
		btnAddCustomer = param_btnAddCustomer;
	}

	/**
	 * @return the btnAddtoTransaction
	 */
	public JButton getBtnAddtoTransaction() {
		return btnAddtoTransaction;
	}

	/**
	 * @param param_btnAddtoTransaction
	 *            the btnAddtoTransaction to set
	 */
	public void setBtnAddtoTransaction(JButton param_btnAddtoTransaction) {
		btnAddtoTransaction = param_btnAddtoTransaction;
	}

	/**
	 * @return the btnLend
	 */
	public JButton getBtnLend() {
		return btnLend;
	}

	/**
	 * @param param_btnLend
	 *            the btnLend to set
	 */
	public void setBtnLend(JButton param_btnLend) {
		btnLend = param_btnLend;
	}

	/**
	 * @return the btnReturn
	 */
	public JButton getBtnReturn() {
		return btnReturn;
	}

	/**
	 * @param param_btnReturn
	 *            the btnReturn to set
	 */
	public void setBtnReturn(JButton param_btnReturn) {
		btnReturn = param_btnReturn;
	}

	/**
	 * @return the btnClear
	 */
	public JButton getBtnClear() {
		return btnClear;
	}

	/**
	 * @param param_btnClear
	 *            the btnClear to set
	 */
	public void setBtnClear(JButton param_btnClear) {
		btnClear = param_btnClear;
	}

	/**
	 * @return the btnRemoveTransaction
	 */
	public JButton getBtnRemoveTransaction() {
		return btnRemoveTransaction;
	}

	/**
	 * @param param_btnRemoveTransaction
	 *            the btnRemoveTransaction to set
	 */
	public void setBtnRemoveTransaction(JButton param_btnRemoveTransaction) {
		btnRemoveTransaction = param_btnRemoveTransaction;
	}

	/**
	 * @return the btnExit
	 */
	public JButton getBtnExit() {
		return btnExit;
	}

	/**
	 * @param param_btnExit
	 *            the btnExit to set
	 */
	public void setBtnExit(JButton param_btnExit) {
		btnExit = param_btnExit;
	}

	/**
	 * @return the tblCustomer
	 */
	public JTable getTblCustomer() {
		return tblCustomer;
	}

	/**
	 * @param param_tblCustomer
	 *            the tblCustomer to set
	 */
	public void setTblCustomer(JTable param_tblCustomer) {
		tblCustomer = param_tblCustomer;
	}

	/**
	 * @return the tblDvd
	 */
	public JTable getTblDvd() {
		return tblDvd;
	}

	/**
	 * @param param_tblDvd
	 *            the tblDvd to set
	 */
	public void setTblDvd(JTable param_tblDvd) {
		tblDvd = param_tblDvd;
	}

	/**
	 * @return the tblTransaction
	 */
	public JTable getTblTransaction() {
		return tblTransaction;
	}

	/**
	 * @param param_tblTransaction
	 *            the tblTransaction to set
	 */
	public void setTblTransaction(JTable param_tblTransaction) {
		tblTransaction = param_tblTransaction;
	}
}
