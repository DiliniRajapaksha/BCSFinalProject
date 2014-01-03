/**
 * 
 */
package com.dvd.renter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.gui.generictablemodel.ColumnName;
import com.gui.generictablemodel.TableTransient;

/**
 * @author Sewwandi
 * 
 */
@Entity
public class DVD {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ColumnName(name = "Code")
	private int id;
	@ColumnName(name = "Name")
	private String dvdName;
	@ColumnName(name = "Language")
	private String language;
	@ColumnName(name = "Category")
	private String category;
	@ColumnName(name = "Availability")
	@Transient
	private Boolean available;
	@TableTransient
	private int NumberOfDvds;

	public DVD() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDvdName() {
		return dvdName;
	}

	public void setDvdName(String dvdName) {
		this.dvdName = dvdName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setNumberOfDvds(int numberOfDvds) {
		NumberOfDvds = numberOfDvds;
	}

	public int getNumberOfDvds() {
		return NumberOfDvds;
	}

}
