package com.dvd.renter.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TableModelEvent;

import org.apache.log4j.Logger;

import com.dvd.renter.config.ApplicationProerties;
import com.dvd.renter.controller.EventController;
import com.dvd.renter.dao.DAO;
import com.dvd.renter.dao.DAOBase;
import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.dvd.renter.entity.Transaction;
import com.dvd.renter.entity.User;

/**
 * Controller for all ui events
 * 
 * @author Sewwandi
 * 
 */
public class Controller {

	private static final String USER_ROLE_ADMIN = "Admin";
	private static Logger LOGGER = Logger.getLogger(Controller.class);
	private static Controller controllerInstance;

	private MainGuiForm mainForm;
	private FormMain formMain;
	private DAOBase daoBase;
	private DAO dao;
	private String userRole;
	private DVD selectedDvd = null;
	private Customer selectedCustomer = null;
	private GregorianCalendar calander = new GregorianCalendar();
	private Date today = new Date();
	private int deposit;
	private int rental;
	private boolean dvdTableChanged = false;
	private boolean dvdCodeCanged = false;
	private boolean dvdNameCanged = false;
	private boolean customerTableClicked = false;
	private DvdForm dvdForm;
	private LoginForm login;
	private UserForm userForm;

	{
		daoBase = new DAOBase();
		dao = new DAO(daoBase);
		try {
			deposit = Integer.parseInt(ApplicationProerties
					.getProperty("deposit"));
			rental = Integer.parseInt(ApplicationProerties
					.getProperty("rental"));
			calander.add(GregorianCalendar.DATE,
					Integer.parseInt(ApplicationProerties.getProperty("days")));
		} catch (NumberFormatException e) {
			LOGGER.error("Invalid deposit value in Application Properties", e);
			System.exit(0);
		} catch (IOException e) {
			LOGGER.error("Could not read Application properties", e);
			System.exit(0);
		}
	}

	/**
	 * If the <code>controllerInstance</code> is null creates a new instance of
	 * the <code>Controller</code>.
	 * 
	 * @return the <code>controllerInstance</code>
	 */
	public static Controller getControllerInstance() {

		controllerInstance = new Controller();
		if (controllerInstance == null) {
		}
		return controllerInstance;
	}

	/**
	 * The constructor has been made private in order to make the
	 * <code>Controller</code>, Singleton.
	 * 
	 */
	private Controller() {

	}

	public void txtChanged(DocumentEvent e) {

		if (e.getDocument() == formMain.txtCustName.getDocument()) {
			txtCutomerNameChanged();
		} else if (e.getDocument() == formMain.txtDvdName.getDocument()) {
			txtDvdNameChanged();
		} else if (e.getDocument() == formMain.txtDvdId.getDocument()) {
			txtDvdCodeChanged();
		}

	}

	private void txtDvdCodeChanged() {
		String dvdCode = formMain.txtDvdId.getText();
		if (dvdCode.trim().matches("([0-9]*)")) {
			if (!dvdCode.isEmpty() && !dvdTableChanged && !dvdNameCanged) {
				Vector<DVD> dvds = new Vector<DVD>();
				dvdCodeCanged = true;
				List<DVD> dvdLst = dao.findByCode(Integer.parseInt(dvdCode));
				dvds.addAll(dvdLst);
				for (DVD dvd : dvdLst) {
					if (dvd.getNumberOfDvds() > 0) {
						dvd.setAvailable(true);
					} else
						dvd.setAvailable(false);
				}
				formMain.dvdTableModel.setDataVector(dvds);
				formMain.dvdTableModel.fireTableDataChanged();
				formMain.cmbDvdCategory.setSelectedIndex(0);
				formMain.cmbDVDLanguage.setSelectedIndex(0);
				formMain.txtDvdName.setText("");
				dvdCodeCanged = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please enter  valid DVD code",
					"Invalid DVD Code!", JOptionPane.ERROR_MESSAGE);
			formMain.txtDvdId.selectAll();
			return;
		}
	}

	private void txtDvdNameChanged() {

		if (!dvdTableChanged && !dvdCodeCanged) {
			dvdNameCanged = true;
			formMain.txtDvdId.setText("");
			formMain.cmbDvdCategory.setSelectedIndex(0);
			formMain.cmbDVDLanguage.setSelectedIndex(0);
			Vector<DVD> dvds = new Vector<DVD>();
			dvds.addAll(dao.findEntityByName(formMain.txtDvdName.getText(),
					DVD.class));
			formMain.dvdTableModel.setDataVector(dvds);
			formMain.dvdTableModel.fireTableDataChanged();
			dvdNameCanged = false;
		}
	}

	private void txtCutomerNameChanged() {

		if (!customerTableClicked) {

			if (!formMain.transactinTblModel.getDataVector().isEmpty()) {
				formMain.transactinTblModel.getDataVector().clear();
				formMain.transactinTblModel.fireTableDataChanged();
			}

			LOGGER.debug("txtName: " + formMain.txtCustName.getText());
			Vector<Customer> cs = new Vector<Customer>();
			cs.addAll(dao.findEntityByName(formMain.txtCustName.getText(),
					Customer.class));
			LOGGER.debug("query result for '" + formMain.txtCustName.getText()
					+ "': " + cs.toString());
			formMain.txtContact.setText("");
			formMain.txtCustId.setText("");
			formMain.txtEmail.setText("");
			formMain.customerTableModel.setDataVector(cs);
			formMain.customerTableModel.fireTableDataChanged();
		}
	}

	private void setCustomerFields(Customer customer) {

		formMain.txtCustName.setText(customer.getName());
		formMain.txtCustId.setText(String.valueOf(customer.getId()));
		formMain.txtContact.setText(customer.getContact());
		formMain.txtEmail.setText(customer.getEmail());
	}

	public void customerTableClicked() {

		int row = formMain.tblCustomer.getSelectedRow();

		if (row > -1) {
			customerTableClicked = true;
			selectedCustomer = new Customer();
			selectedCustomer = formMain.customerTableModel.getDataVector().get(
					row);
			LOGGER.debug("selected row customer id: "
					+ selectedCustomer.getId());
			setCustomerFields(selectedCustomer);
			formMain.customerTableModel.getDataVector().clear();
			formMain.customerTableModel.getDataVector().add(selectedCustomer);
			formMain.customerTableModel.fireTableDataChanged();
			List<Transaction> transactionLst = dao
					.findTransactionByCustomer(selectedCustomer);

			if (!transactionLst.isEmpty()) {
				Vector<Transaction> transactions = new Vector<Transaction>();
				transactions.addAll(transactionLst);
				formMain.transactinTblModel.setDataVector(transactions);
				formMain.transactinTblModel.fireTableDataChanged();
				formMain.txtOverDueDays.setEnabled(false);
				formMain.txtRefundable.setEnabled(false);
			}

			customerTableClicked = false;

			if (selectedDvd != null
					&& formMain.tblTransaction.getRowCount() == 0) {
				formMain.btnAddtoTransaction.setEnabled(true);
			}
		}
	}

	public void dvdTableClicked() {

		LOGGER.debug("Dvd table clicked");
		int row = formMain.tblDvd.getSelectedRow();

		if (row > -1) {
			dvdTableChanged = true;
			selectedDvd = formMain.dvdTableModel.getDataVector().get(row);
			formMain.txtDvdName.setText(selectedDvd.getDvdName());
			formMain.txtDvdId.setText(String.valueOf(selectedDvd.getId()));
			formMain.cmbDvdCategory.setSelectedItem(selectedDvd.getCategory());
			formMain.cmbDVDLanguage.setSelectedItem(selectedDvd.getLanguage());
			dvdTableChanged = false;

			if (selectedCustomer != null
					&& formMain.tblTransaction.getRowCount() == 0) {
				formMain.btnAddtoTransaction.setEnabled(true);
			}
		}
	}

	public void btnPressedFormMain(ActionEvent e) throws NumberFormatException,
			IOException, NoSuchAlgorithmException {

		if (e.getSource() == formMain.btnAddCustomer) {
			LOGGER.debug("add pressed");
			addCustomer();
		} else if (e.getSource() == formMain.btnAddtoTransaction) {
			if (selectedDvd != null && selectedCustomer != null) {
				addTransaction();
			}
		} else if (e.getSource() == formMain.btnLend) {
			if (!formMain.transactinTblModel.getDataVector().isEmpty()) {
				for (Transaction transaction : formMain.transactinTblModel
						.getDataVector()) {
					dao.addEntity(transaction);
				}
			}
		} else if (e.getSource() == formMain.btnRemoveTransaction) {
			int selectedIndex = formMain.tblTransaction.getSelectedRow();
			formMain.transactinTblModel.getDataVector().removeElementAt(
					selectedIndex);
			formMain.transactinTblModel.fireTableDataChanged();
		} else if (e.getSource() == formMain.btnReturn) {
			for (Transaction transaction : formMain.transactinTblModel
					.getDataVector()) {
				dao.removeEntity(transaction);
			}
		} else if (e.getSource() == formMain.btnOk) {
			saveTransaction();
			clearTxt(new JTextField[] { formMain.txtContact,
					formMain.txtCustId, formMain.txtCustName, formMain.txtEmail });
		} else if (e.getSource() == formMain.btnExit) {
			System.exit(0);
		}
	}

	private void clearTxt(JTextField[] param_jTextFields) {

		for (JTextField jTextField : param_jTextFields) {
			jTextField.setText("");
		}
	}

	public void btnPressedDvdForm(ActionEvent e) {

		if (e.getSource() == dvdForm.btnAdd) {
			addDvd();
		} else if (e.getSource() == dvdForm.btnSave) {
			saveDvds();
		} else if (e.getSource() == dvdForm.btnCancel) {
			cancel(dvdForm);
		} else if (e.getSource() == dvdForm.btnRemove) {
			removeDvd();
		}

	}

	private void removeDvd() {
		dvdForm.dvdTableModel.getDataVector().remove(
				dvdForm.tblDvd.getSelectedRow());
		dvdForm.dvdTableModel.fireTableDataChanged();
		if (dvdForm.dvdTableModel.getDataVector().size() > 0) {
			dvdForm.btnSave.setEnabled(true);
		} else {
			dvdForm.btnSave.setEnabled(false);
		}

	}

	public void btnPressedUserForm(ActionEvent e)
			throws NoSuchAlgorithmException {

		if (validateEmpty(new JTextField[] { userForm.txtOldPassword })) {
			return;
		}
		if (e.getSource() == userForm.btnChangePw) {
			User user = new User();
			String newPasswd = String.valueOf(userForm.txtPassword
					.getPassword());
			String newPasswd2 = String.valueOf(userForm.txtPassword2
					.getPassword());
			String oldPasswd = String.valueOf(userForm.txtOldPassword
					.getPassword());

			if (newPasswd.equals(newPasswd2)) {
				user.setRole(userForm.cmbUserRole.getSelectedItem().toString());

				if (checkOldPasswd(user, oldPasswd)) {
					BigInteger encryptedPasswd = null;
					encryptedPasswd = encryptPassword(newPasswd);
					user.setPassword(encryptedPasswd);
					dao.modify(user);
				} else {
					JOptionPane.showMessageDialog(userForm,
							"The current password is incorrect!",
							"Password Incorrect", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(userForm,
						"Your new password entries did not match!",
						"New Password Entries Did Not Match",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void btnPressedLoginForm(ActionEvent param_e)
			throws NoSuchAlgorithmException, IOException {
		if (param_e.getSource() == login.btnLogin) {
			login();
		} else if (param_e.getSource() == login.btnCancel) {
			cancel(login);
		}
	}

	private void cancel(JFrame frame) {
		frame.dispose();
	}

	private boolean validateUser() throws NoSuchAlgorithmException {

		boolean valid = false;
		User user = new User();
		BigInteger encryptedPassword = encryptPassword(String
				.valueOf(login.txtPassword.getPassword()));
		user.setPassword(encryptedPassword);
		user.setRole(login.cmbUserRole.getSelectedItem().toString());
		User foundUser = dao.findEntity(user, user.getRole());
		LOGGER.debug(foundUser.getRole());
		if (foundUser.getPassword().equals(
				String.valueOf(login.txtPassword.getPassword()))) {
			// if (foundUser.getPassword().equals(user.getPassword())) {
			LOGGER.debug("user found");
			valid = true;
			userRole = foundUser.getRole();
			login.dispose();
		}
		return valid;
	}

	private void login() throws NoSuchAlgorithmException, IOException {

		if (login.cmbUserRole.getSelectedIndex() > 0) {
			if (validateUser()) {
				if (formMain == null) {
					EventController eventController = EventController
							.getInstance();
					FormMain mainForm;
					mainForm = new FormMain(eventController);
					eventController.setCurrentGuiInstance(mainForm);
					this.setFormMain(mainForm);
					mainForm.createAndShow();
				}
			} else {
				JOptionPane.showMessageDialog(login,
						"Password you entered is incorrect", "Access Dinied",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(login,
					"Please select a valid user type", "Access Dinied",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean checkOldPasswd(User user, String oldPasswd)
			throws NoSuchAlgorithmException {

		boolean passwdMatched = false;
		BigInteger encryptedPasswd = null;
		encryptedPasswd = encryptPassword(oldPasswd);
		user.setPassword(encryptedPasswd);
		User foundUser = dao.findEntity(user, user.getRole());

		if (foundUser != null) {

			if (foundUser.getPassword().equals(user.getPassword())) {
				passwdMatched = true;
			}
		} else {
			LOGGER.error("User '" + user.getRole()
					+ "' does not exist in the database");
		}

		return passwdMatched;
	}

	private boolean validateEmpty(JTextField[] param_txts) {

		boolean result = true;
		int count = 0;

		for (JTextField jTextField : param_txts) {

			if (jTextField.getText().isEmpty()) {
				count++;
			}
		}

		if (count > 0) {
			JOptionPane.showMessageDialog(null, "Please Fill All details!",
					"Details Incomplete", JOptionPane.ERROR_MESSAGE);
			result = false;
		}

		return result;

	}

	private void addCustomer() {

		String id = formMain.txtCustId.getText();

		if (selectedCustomer != null && !id.isEmpty()) {

			if (selectedCustomer.getId() == Integer.parseInt(id)) {
				JOptionPane.showMessageDialog(null, "The customer "
						+ formMain.txtCustName.getText()
						+ " already exist in the system!",
						"Customer Already Exist!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		Customer c = new Customer();
		JTextField[] txts = new JTextField[] { formMain.txtContact,
				formMain.txtCustName, formMain.txtEmail };

		if (!validateEmpty(txts)) {
			return;
		}

		c.setContact(formMain.txtContact.getText());
		c.setName(formMain.txtCustName.getText());
		c.setEmail(formMain.txtEmail.getText());
		dao.addEntity(c);
		formMain.txtCustId.setText(String.valueOf(c.getId()));
		selectedCustomer = c;
		Vector<Customer> vector = new Vector<Customer>();
		vector.add(c);
		formMain.customerTableModel.setDataVector(vector);
		formMain.customerTableModel.fireTableDataChanged();
	}

	public BigInteger encryptPassword(String passwd)
			throws NoSuchAlgorithmException {

		MessageDigest md;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Could not encrypt password", e);
			throw e;
		}

		md.update(passwd.getBytes());
		BigInteger encryptedPasswd = new BigInteger(1, md.digest());
		return encryptedPasswd;
	}

	private void saveTransaction() {

		Vector<Transaction> transactions = formMain.transactinTblModel
				.getDataVector();

		for (Transaction transaction : transactions) {
			dao.addEntity(transaction);
		}

	}

	private void saveDvds() {

		Vector<DVD> data = dvdForm.dvdTableModel.getDataVector();

		if (!data.isEmpty()) {

			for (DVD dvd : data) {
				dao.addEntity(dvd);
			}

			dvdForm.dvdTableModel.fireTableDataChanged();
			dvdForm.btnSave.setEnabled(false);
		}
	}

	private void addDvd() {

		if (!(dvdForm.btnSave.isEnabled())) {
			dvdForm.dvdTableModel.getDataVector().clear();
		}

		JTextField[] txts = new JTextField[] { dvdForm.txtDvdName,
				dvdForm.txtNoOfDvds };

		JComboBox[] cmbs = new JComboBox[] { dvdForm.cmbDvdCategory,
				dvdForm.cmbDVDLanguage };

		if (!validateEmpty(txts) || !validateCombo(cmbs)) {
			return;
		}

		if (!validateNumberOfDvds()) {
			return;
		}

		DVD dvd = new DVD();
		dvd.setDvdName(dvdForm.txtDvdName.getText());
		dvd.setCategory(dvdForm.cmbDvdCategory.getSelectedItem().toString());
		dvd.setLanguage(dvdForm.cmbDVDLanguage.getSelectedItem().toString());
		dvd.setNumberOfDvds(Integer.parseInt(dvdForm.txtNoOfDvds.getText()));
		dvd.setAvailable(true);
		LOGGER.debug("dvd addded: " + dvd.getDvdName());
		dvdForm.dvdTableModel.getDataVector().add(dvd);
		dvdForm.dvdTableModel.fireTableDataChanged();
		dvdForm.btnSave.setEnabled(true);
		dvdForm.btnSave.setEnabled(true);
		resetForm(dvdForm);
	}

	private void resetDvdForm() {

		dvdForm.txtDvdName.setText("");
		dvdForm.txtNoOfDvds.setText("");
		dvdForm.cmbDvdCategory.setSelectedIndex(0);
		dvdForm.cmbDVDLanguage.setSelectedIndex(0);

	}

	private void resetForm(Container frame) {

		for (Component comp : frame.getComponents()) {
			LOGGER.debug("componant: " + comp.getClass());
			if (comp instanceof JTextField) {
				((JTextField) comp).setText("");
			} else if (comp instanceof JComboBox) {
				((JComboBox) comp).setSelectedIndex(0);
			} else if (comp instanceof JPanel || comp instanceof JRootPane
					|| comp instanceof JLayeredPane) {
				resetForm((Container) comp);
			}
			// resetComponant(comp);
		}
	}

	private void resetComponant(Component comp) {

		if (comp instanceof JTextField) {
			((JTextField) comp).setText("");
		} else if (comp instanceof JComboBox) {
			((JComboBox) comp).setSelectedIndex(0);
		} else if (comp instanceof JPanel) {
			for (Component componant : ((Container) comp).getComponents()) {
				resetComponant(comp);
			}
		}
	}

	private boolean validateNumberOfDvds() {

		boolean result = true;

		if (!dvdForm.txtNoOfDvds.getText().trim().matches("([0-9]*)")) {
			result = false;
			JOptionPane.showMessageDialog(null, "Please enter a valid number",
					"Invalid Number of DVDs!", JOptionPane.ERROR_MESSAGE);
			dvdForm.txtNoOfDvds.setText("");
			return result;
		}

		return result;
	}

	private boolean validateCombo(JComboBox[] param_cmbs) {

		boolean result = true;
		int count = 0;

		for (JComboBox jComboBox : param_cmbs) {

			if (jComboBox.getSelectedIndex() == 0) {
				count++;
				result = false;
			}
		}

		if (count > 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select a value from the combo box!",
					"Details Incomplete", JOptionPane.ERROR_MESSAGE);
			result = false;
		}

		return result;
	}

	private void addTransaction() throws NumberFormatException, IOException {

		Transaction transaction = new Transaction();
		transaction.setCustomerId(selectedCustomer.getId());
		transaction.setDateOfTransaction(today);
		transaction.setDvdId(selectedDvd.getId());
		Date dueDate = calander.getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
		transaction.setDateOfTransaction(today);
		transaction.setDueDate(dueDate);
		formMain.txtDueDate.setText(dateFormat.format(dueDate));
		formMain.txtLentDate.setText(dateFormat.format(today));
		Vector<Transaction> vector = new Vector<Transaction>();
		vector.add(transaction);
		formMain.transactinTblModel.setDataVector(vector);
		formMain.transactinTblModel.fireTableDataChanged();
	}

	public void itemChanged(ItemEvent param_e) {

		if (param_e.getSource() == formMain.cmbDvdCategory) {

			if (formMain.cmbDvdCategory.getSelectedIndex() > 0
					&& !dvdTableChanged) {
				String category = formMain.cmbDvdCategory.getSelectedItem()
						.toString();
				Vector<DVD> dvds = new Vector<DVD>();
				dvds.addAll(dao.findByCategory(category));
				formMain.dvdTableModel.setDataVector(dvds);
				formMain.dvdTableModel.fireTableDataChanged();
				formMain.txtDvdId.setText("");
				formMain.txtDvdName.setText("");
				formMain.cmbDVDLanguage.setSelectedIndex(0);
			}
		}

		if (param_e.getSource() == formMain.cmbDVDLanguage) {

			if (formMain.cmbDVDLanguage.getSelectedIndex() > 0
					&& !dvdTableChanged) {
				String language = formMain.cmbDVDLanguage.getSelectedItem()
						.toString();
				Vector<DVD> dvds = new Vector<DVD>();
				dvds.addAll(dao.findByLanguage(language));
				formMain.dvdTableModel.setDataVector(dvds);
				formMain.dvdTableModel.fireTableDataChanged();
				formMain.txtDvdId.setText("");
				formMain.txtDvdName.setText("");
				formMain.cmbDvdCategory.setSelectedIndex(0);
			}
		}
	}

	public void tableChanged(TableModelEvent param_e) {

		if (param_e.getSource() == formMain.transactinTblModel) {
			int noOfRows = formMain.transactinTblModel.getRowCount();
			LOGGER.debug("No of transactions: " + noOfRows);
			int _deposit = deposit * noOfRows;
			int _rental = rental * noOfRows;
			formMain.txtDeposit.setText(String.valueOf(_deposit));
			formMain.txtNoOfDvds.setText(String.valueOf(noOfRows));
			formMain.txtRent.setText(String.valueOf(_rental));
			formMain.txtTotalPay.setText(String.valueOf(_deposit + _rental));

			if (noOfRows > 0) {
				formMain.btnOk.setEnabled(true);
				formMain.btnCancel.setEnabled(true);
				formMain.btnRemoveTransaction.setEnabled(true);

				if (!formMain.btnAddtoTransaction.isEnabled()) {
					formMain.btnReturn.setEnabled(true);
				}
			}
		}

	}

	public void menuItemClicked(ActionEvent param_e) throws IOException {

		if (param_e.getSource() == formMain.dvdMenuItem) {
			if (userRole.equals(USER_ROLE_ADMIN)) {
				dvdForm = new DvdForm();
				EventController eventContr = EventController.getInstance();
				eventContr.setCurrentGuiInstance(dvdForm);
				dvdForm.setController(eventContr);
				dvdForm.run();
			} else {
				JOptionPane
						.showMessageDialog(
								formMain,
								"You are logged in as "
										+ userRole
										+ ". \nPlease Login as Administrator to show Inventory Form",
								"Access Denied", JOptionPane.ERROR_MESSAGE);
				// EventController eventContr = EventController.getInstance();
				// login = new LoginForm(eventContr);
				// eventContr.setCurrentGuiInstance(login);
				// login.createAndShow();
			}
		} else if (param_e.getSource() == formMain.usersMenuItem) {
			userForm = new UserForm();
			EventController eventContr = EventController.getInstance();
			eventContr.setCurrentGuiInstance(userForm);
			userForm.setController(eventContr);
			userForm.init();
			userForm.run();
		} else if (param_e.getSource() == formMain.loginMenuItem) {
			EventController eventContr = EventController.getInstance();
			login = new LoginForm(eventContr);
			eventContr.setCurrentGuiInstance(login);
			login.createAndShow();
		}

	}

	public MainGuiForm getMainForm() {
		return mainForm;
	}

	public void setMainForm(MainGuiForm param_m) {
		mainForm = param_m;
	}

	public void setFormMain(FormMain formMain) {
		this.formMain = formMain;
	}

	public FormMain getFormMain() {
		return formMain;
	}

	public DvdForm getDvdForm() {
		return dvdForm;
	}

	public void setDvdForm(DvdForm param_dvdForm) {
		dvdForm = param_dvdForm;
	}

	public LoginForm getLogin() {
		return login;
	}

	public void setLogin(LoginForm param_login) {
		login = param_login;
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm param_userForm) {
		userForm = param_userForm;
	}

}
