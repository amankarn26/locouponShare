package com.locoupon.common.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.AddressDao;
import com.locoupon.common.entity.Address;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class AddressDaoImpl implements AddressDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class);

	@Override
	public ErrorCodes add(Address address) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(address);
		} catch (Exception e) {
			logger.debug("Exception in saving Address ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return ErrorCodes.HIBERNATE_EXCEPTION;
		}

		return reqError;
	}

	@Override
	public ErrorCodes delete(Address address) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			address = entityManager.merge(address);
			entityManager.remove(address);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete " + address.getLine1(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public Address find(Address address) {
		String logprefix = address.getLine1() + ":" + address.getLine2();
		logger.debug("Address " + logprefix);
		try {
			String qlString = "from Address where line1=:line1 and line2=:line2 and city=:city and pincode=:pincode";
			TypedQuery<Address> query = entityManager.createQuery(qlString, Address.class);
			query.setParameter("line1", address.getLine1());
			query.setParameter("line2", address.getLine2());
			query.setParameter("city", address.getCity());
			query.setParameter("pincode", address.getPincode());

			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in find Address " + logprefix, e);
			return null;
		}
	}

	@Override
	public Address getAddress(Long id) {
		String logprefix = id.toString();
		logger.debug("Address " + logprefix);
		try {
			String qlString = "from Address where id=:id";
			TypedQuery<Address> query = entityManager.createQuery(qlString, Address.class);
			query.setParameter("id", id);

			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in find Address " + logprefix, e);
			return null;
		}
	}

	@Override
	public ErrorCodes update(Address dbaddr) {
		logger.debug("update " + dbaddr.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbaddr);
			logger.debug("update success " + dbaddr.getId());
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update " + dbaddr.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

}
