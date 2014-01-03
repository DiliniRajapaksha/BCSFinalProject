package com.gui.generictablemodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

public class GenericTableModel<E> extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(GenericTableModel.class);
	private Vector<E> genericVector = new Vector<E>();
	E e = null;
	private List<Field> fields = new ArrayList<Field>();

	/**
	 * 
	 */
	public GenericTableModel() {
		// setAllColumns();
	}

	/**
	 * @param e
	 */
	public GenericTableModel(E e) {
		super();
		this.e = e;
		setAllColumns();
	}

	public Vector<E> getDataVector() {
		return genericVector;
	}

	public void setDataVector(Vector<E> vector) {
		LOGGER.debug("vector set");
		this.genericVector = vector;
	}

	public void setAllColumns() {
		Field[] allFields = e.getClass().getDeclaredFields();
		for (int i = 0; i < allFields.length; i++) {
			if (allFields[i].getAnnotation(TableTransient.class) == null) {
				fields.add(allFields[i]);
			}
		}
	}

	@Override
	public int getColumnCount() {
		LOGGER.debug(new Date() + "getColumnCount" + fields.size());
		return fields.size();
	}

	@Override
	public String getColumnName(int column) {
		String columnName = "";
		ColumnName colName = fields.get(column).getAnnotation(ColumnName.class);
		if (colName != null) {
			columnName = colName.name();
		} else {
			columnName = fields.get(column).getName();
		}
		return columnName;

	}

	@Override
	public int getRowCount() {
		return genericVector.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object value = null;
		try {
			E temp = genericVector.get(rowIndex);
			Field f = temp.getClass().getDeclaredField(
					fields.get(columnIndex).getName());
			f.setAccessible(true);
			value = f.get(temp);
			f.setAccessible(false);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

}
