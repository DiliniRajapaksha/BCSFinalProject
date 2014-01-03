package com.dvd.renter.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Sewwandi
 * 
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id()
	private String role;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(BigInteger param_password) {
		password = param_password.toString(16);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String param_role) {
		role = param_role;
	}

}
