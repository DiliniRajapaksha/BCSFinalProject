/**
 * 
 */
package com.dvd.renter.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.gui.generictablemodel.ColumnName;

/**
 * @author Sewwandi
 * 
 */
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ColumnName(name = "ID")
	private int id;
	@ColumnName(name = "Customer Id")
	private int customerId;
	@ColumnName(name = "Dvd Code")
	private int dvdId;
	@ColumnName(name = "Date")
	private Date dateOfTransaction;
	@ColumnName(name = "Due Date")
	private Date dueDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getDvdId() {
		return dvdId;
	}

	public void setDvdId(int dvdId) {
		this.dvdId = dvdId;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public Date getDueDate() {

		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
