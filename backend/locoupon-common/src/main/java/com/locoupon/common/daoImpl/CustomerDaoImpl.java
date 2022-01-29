package com.locoupon.common.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.CustomerDao;
import com.locoupon.common.entity.Customer;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

	@Override
	public List<Customer> getAllCustomer() {
		String qlString = "from Customer order by name";
		TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
		return query.getResultList();
	}

	@Override
	public Customer getCustomerById(Long id) {
		try {
			String qlString = "from Customer where id=:id";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("id", id);
			Customer cust = query.getSingleResult();
			return cust;
		} catch (Exception e) {
			logger.error("Exception in getCustomerById " + id, e);
			return null;
		}
	}

	@Override
	public Customer checkIfCustomerExists(Long id, String email, ErrorCodes reqError) {
		Customer cust = null;
		try {
			String qlString = "from Customer where id=:id or login.email=:email ";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("email", email);
			query.setParameter("id", id);
			cust = query.getSingleResult();
			if (null == cust) {
				reqError = ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
				return null;
			} else {
				return cust;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfCustomerExists " + email + " Exception: " + e.getMessage());
			reqError = ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public ErrorCodes addCustomer(Customer cust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(cust);
		} catch (Exception e) {
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return ErrorCodes.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public ErrorCodes deleteCustomer(Customer dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			dbcust = entityManager.merge(dbcust);
			entityManager.remove(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Customer " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes updateCustomer(Customer dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Customer " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public Long getTotalCount(Long firm) {
		String qlString = "select count(*) from Customer where firm=:firm";
		TypedQuery<Long> query = entityManager.createQuery(qlString, Long.class);
		query.setParameter("firm", firm);
		Long count = query.getSingleResult();
		return count;
	}

	@Override
	public List<Customer> getAllCustomer(String scity) {
		String qlString = "from Customer  where city=:scity order by id";
		TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
		query.setParameter("scity", scity);
		return query.getResultList();
	}

	@Override
	public Customer getCustomerByName(String name) {
		try {
			String qlString = "from Customer where name=:name";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("name", name);
			Customer cust = query.getSingleResult();
			return cust;
		} catch (Exception e) {
			logger.error("Exception in getCustomerByName " + name, e);
			return null;
		}
	}

	@Override
	public Long getCustIDByName(String name) {
		try {
			String qlString = "from Customer c where c.name=:name";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("name", name);
			Customer cust = query.getSingleResult();
			return cust.getId();
		} catch (Exception e) {
			logger.error("Exception in getCustomerByEmail " + name + "Exception:" + e.getMessage());
			return null;
		}
	}


	@Override
	public Customer getCustomerByEmail(String email) {
		try {
			String qlString = "from Customer e where e.login.email=:email";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in getCustomerByEmail " + email, e);
			return null;
		}
	}

	@Override
	public Customer checkCustomerExistByMobile(String mob, ErrorCodes reqError) {
		Customer cust = null;
		try {
			String qlString = "from Customer where login.email=:mob";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
			query.setParameter("mob", mob);
			cust = query.getSingleResult();
			if (null == cust) {
				reqError = ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
				return null;
			} else {
				return cust;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfCustdataExists " + mob + " Exception: " + e.getMessage());
			reqError = ErrorCodes.CUSTOMER_DATA_DOES_NOT_EXISTS;
			return null;
		}
	}

	@Override
	public List<Customer> getAllCustomerByPincode(String pincode) {
		String qlString = "from Customer c where c.address.pincode=:pincode order by id";
		TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);
		query.setParameter("pincode", pincode);
		return query.getResultList();
	}

}