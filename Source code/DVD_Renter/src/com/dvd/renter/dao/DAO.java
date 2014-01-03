package com.dvd.renter.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.dvd.renter.entity.Customer;
import com.dvd.renter.entity.DVD;
import com.dvd.renter.entity.Transaction;

/**
 * Contains generic methods to manipulate all domain objects.
 * 
 * @author Sewwandi
 * 
 */
public class DAO {

	protected static Logger LOGGER = Logger.getLogger(DAO.class);
	protected EntityManager em;
	protected DAOBase dbUtil;
	@SuppressWarnings("unchecked")
	private Map<Class, String> findByNameQuery = new HashMap<Class, String>();

	public DAO(DAOBase db) {

		this.dbUtil = db;
		findByNameQuery.put(Customer.class,
				"SELECT * FROM customer WHERE name LIKE ?");
		findByNameQuery
				.put(DVD.class, "SELECT * FROM dvd WHERE dvdName LIKE ?");
	}

	public <E> void addEntity(E e) {

		em = dbUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	public <E> E findEntity(E e, String id) {

		em = dbUtil.getEntityManager();
		em.getTransaction().begin();
		e = (E) em.find(e.getClass(), id);
		em.getTransaction().commit();
		em.close();
		return e;
	}

	public <E> void modify(E e) {

		em = dbUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	public List findEntityByName(String name, Class c) {

		List ds = new ArrayList();
		em = dbUtil.getEntityManager();
		String sql = findByNameQuery.get(c);
		Query q = em.createNativeQuery(sql, c);
		q.setParameter(1, name + "%");

		if (!q.getResultList().isEmpty()) {
			ds = q.getResultList();
			LOGGER.debug(new Date() + "entity found" + ds.toString());
		}

		return ds;

	}

	public List<DVD> findByCategory(String param_category) {

		List<DVD> dvds = new ArrayList<DVD>();
		String query = "SELECT * FROM dvd WHERE category=?";
		em = dbUtil.getEntityManager();
		Query q = em.createNativeQuery(query, DVD.class);
		q.setParameter(1, param_category);
		dvds = q.getResultList();
		return dvds;

	}

	public List<DVD> findByLanguage(String param_language) {
		List<DVD> dvds = new ArrayList<DVD>();
		String query = "SELECT * FROM dvd WHERE language=?";
		em = dbUtil.getEntityManager();
		Query q = em.createNativeQuery(query, DVD.class);
		q.setParameter(1, param_language);
		dvds = q.getResultList();
		return dvds;
	}

	public <E> void removeEntity(E e) {
		em = dbUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(e);
		em.getTransaction().commit();
		em.close();
	}

	public List<DVD> findByCode(int Id) {
		List<DVD> dvds = new ArrayList<DVD>();
		String query = "SELECT * FROM dvd WHERE id=?";
		em = dbUtil.getEntityManager();
		Query q = em.createNativeQuery(query, DVD.class);
		q.setParameter(1, Id);
		dvds = q.getResultList();
		return dvds;
	}

	public List<Transaction> findTransactionByCustomer(
			Customer param_selectedCustomer) {

		List<Transaction> transactions = new ArrayList<Transaction>();
		String query = "SELECT * FROM transaction WHERE customerId=?";
		em = dbUtil.getEntityManager();
		Query q = em.createNativeQuery(query, Transaction.class);
		q.setParameter(1, param_selectedCustomer.getId());
		transactions = q.getResultList();
		return transactions;

	}
}
