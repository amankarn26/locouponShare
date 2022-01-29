package com.locoupon.common.daoImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.DiscountDao;
import com.locoupon.common.entity.Discount;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.CustomerType;
import com.locoupon.common.model.CustomerType.type;
import com.locoupon.common.util.UtilFuncs;

@Transactional
@Service
public class DiscountDaoImpl implements DiscountDao {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(DiscountDaoImpl.class);

	@Override
	public List<Discount> getAllValidDiscount() {
		Date currentdate = UtilFuncs.getCurrentDateOnly();
		String qlString = "from Discount where validtill>=:currentdate order by name";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		query.setParameter("currentdate", currentdate);
		return query.getResultList();
	}

	@Override
	public List<Discount> getAllDiscount() {	
		String qlString = "from Discount order by name";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		return query.getResultList();
	}
	
	@Override
	public Discount getDiscountById(Long id) {
		try {
			String qlString = "from Discount where id=:id";
			TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
			query.setParameter("id", id);

			Discount discount = query.getSingleResult();
			;
			return discount;
		} catch (Exception e) {
			logger.error("Exception in getDiscountById " + id + e);
			return null;
		}
	}

	@Override
	public ErrorCodes addDiscount(Discount discount) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(discount);
		} catch (Exception e) {
			logger.debug("Exception in saving Discount ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public ErrorCodes deleteDiscount(Discount dbdiscount) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbdiscount);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Discount " + dbdiscount.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public Discount checkIfDiscountExists(String couponcode, ErrorCodes reqError) {
		Discount depart = null;
		try {
			String qlString = "from Discount where couponcode=:couponcode";
			TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
			query.setParameter("couponcode", couponcode);

			depart = query.getSingleResult();
			if (null == depart) {
				reqError.setValue(ErrorCodes.DISCOUNT_DOES_NOT_EXIST.toString());
				reqError = ErrorCodes.DISCOUNT_DOES_NOT_EXIST;
				return null;
			} else {
				return depart;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfExists " + couponcode + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.DISCOUNT_DOES_NOT_EXIST.toString());
			reqError = ErrorCodes.DISCOUNT_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public ErrorCodes updateDiscount(Discount dbdiscount) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbdiscount);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Discount " + dbdiscount.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public boolean checkDiscountInUsed(Long id, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Discount getDiscountByCouponCode(String couponcode) {
		
		String qlString = "from Discount where couponcode=:couponcode ";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		query.setParameter("couponcode", couponcode);
		return query.getSingleResult();
	}

	@Override
	public List<Discount> getDiscountByType(type type) {	
		String qlString = "from Discount where type=:type";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	@Override
	public Discount getDiscountByCouponCode(String couponcode, type type) {
		
		String qlString = "from Discount where couponcode=:couponcode and type=:type";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		query.setParameter("couponcode", couponcode);
		query.setParameter("type", type);
		return query.getSingleResult();
	}

	@Override
	public List<Discount> getAllValidDiscount(CustomerType.type type) {
		Date currentdate = UtilFuncs.getCurrentDateOnly();
		String qlString = "from Discount where validtill>=:currentdate and type=:type order by name";
		TypedQuery<Discount> query = entityManager.createQuery(qlString, Discount.class);
		query.setParameter("currentdate", currentdate);
		query.setParameter("type", type);
		return query.getResultList();
	}

}
