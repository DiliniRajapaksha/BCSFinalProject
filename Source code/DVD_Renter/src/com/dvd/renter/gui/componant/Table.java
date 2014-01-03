/**
 * 
 */
package com.dvd.renter.gui.componant;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author Sewwandi
 * 
 */
public class Table extends JTable {

	private static final long serialVersionUID = 1L;
	Font font = Font.decode("Times New Roman-bold-13");
	{
		this.setFont(font);
		this.getTableHeader().setFont(font);
	}

	/**
	 * 
	 */
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_numRows
	 * @param param_numColumns
	 */
	public Table(int param_numRows, int param_numColumns) {
		super(param_numRows, param_numColumns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_rowData
	 * @param param_columnNames
	 */
	public Table(Object[][] param_rowData, Object[] param_columnNames) {
		super(param_rowData, param_columnNames);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_dm
	 * @param param_cm
	 * @param param_sm
	 */
	public Table(TableModel param_dm, TableColumnModel param_cm,
			ListSelectionModel param_sm) {
		super(param_dm, param_cm, param_sm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_dm
	 * @param param_cm
	 */
	public Table(TableModel param_dm, TableColumnModel param_cm) {
		super(param_dm, param_cm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_dm
	 */
	public Table(TableModel param_dm) {
		super(param_dm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param param_rowData
	 * @param param_columnNames
	 */
	public Table(Vector param_rowData, Vector param_columnNames) {
		super(param_rowData, param_columnNames);
		// TODO Auto-generated constructor stub
	}

}
