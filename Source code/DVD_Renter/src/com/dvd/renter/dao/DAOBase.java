package com.dvd.renter.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class DAOBase {

	static Logger LOGGER = Logger.getLogger(DAOBase.class);

	private EntityManagerFactory emf;
	private EntityManager em;

	public DAOBase() {
		this.emf = getEmf();
	}

	public EntityManager getEntityManager() {

		emf = getEmf();

		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
			LOGGER.info(new Date() + "em creaed");
		}

		return em;

	}

	public EntityManagerFactory getEmf() {

		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("testUnit");
			LOGGER.info(new Date() + "emf creaed");
		}

		return emf;
	}

}
