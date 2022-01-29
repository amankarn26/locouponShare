package com.locoupon.common.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.MerchantDao;
import com.locoupon.common.entity.Merchant;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class MerchantDaoImpl implements MerchantDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(MerchantDaoImpl.class);

	@Override
	public List<Merchant> getAllMerchant() {
		String qlString = "from Merchant order by name";
		TypedQuery<Merchant> query = entityManager.createQuery(qlString, Merchant.class);
		return query.getResultList();
	}

	@Override
	public Merchant getMerchantById(Long id) {
		try {
			String qlString = "from Merchant where id=:id";
			TypedQuery<Merchant> query = entityManager.createQuery(qlString, Merchant.class);
			query.setParameter("id", id);
			Merchant cust = query.getSingleResult();
			return cust;
		} catch (Exception e) {
			logger.error("Exception in getMerchantById " + id, e);
			return null;
		}
	}

	@Override
	public Merchant checkIfMerchantExists(Long id, String email, ErrorCodes reqError) {
		Merchant cust = null;
		try {
			String qlString = "from Merchant where id=:id or login.email=:email ";
			TypedQuery<Merchant> query = entityManager.createQuery(qlString, Merchant.class);
			query.setParameter("email", email);
			query.setParameter("id", id);
			cust = query.getSingleResult();
			if (null == cust) {
				reqError = ErrorCodes.MERCHANT_DOES_NOT_EXIST;
				return null;
			} else {
				return cust;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfMerchantExists " + email + " Exception: " + e.getMessage());
			reqError = ErrorCodes.MERCHANT_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public ErrorCodes addMerchant(Merchant cust) {
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
	public ErrorCodes deleteMerchant(Merchant dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			dbcust = entityManager.merge(dbcust);
			entityManager.remove(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Merchant " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes updateMerchant(Merchant dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Merchant " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	
	@Override
	public Merchant getMerchantByEmail(String email) {
		try {
			String qlString = "from Merchant e where e.login.email=:email";
			TypedQuery<Merchant> query = entityManager.createQuery(qlString, Merchant.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in getMerchantByEmail " + email, e);
			return null;
		}
	}

}