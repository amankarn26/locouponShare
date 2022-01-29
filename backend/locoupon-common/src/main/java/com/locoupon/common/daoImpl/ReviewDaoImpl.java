package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.ConfigDao;
import com.locoupon.common.dao.ReviewDao;
import com.locoupon.common.entity.Discount;
import com.locoupon.common.entity.ProductReview;
import com.locoupon.common.enums.ApplicationConstants;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.ReviewStatus;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.StatusType.type;


@Transactional
@Service
public class ReviewDaoImpl implements ReviewDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);

	private static final long DISH_REVIEW_LIMIT = 3;

	@Override
	public List<ProductReview> getAllReview() {
		String qlString = "from ProductReview order by id";
		TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
		return query.getResultList();
	}
	@Override
	public ProductReview getReviewById(Long id) {
		try {
			String qlString = "from ProductReview where id=:id";
			TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
			query.setParameter("id", id);
			ProductReview rev = query.getSingleResult();
			return rev;
		} catch (Exception e) {
			logger.error("Exception in getReviewById " + id, e);
			return null;
		}
	}
	@Override
	public ErrorCodes addReview(ProductReview prodrl) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(prodrl);
		} catch (Exception e) {
			logger.debug("Exception in saving Review ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return ErrorCodes.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}
	@Override
	public ProductReview checkIfReviewExists(Long id, ErrorCodes reqError) {
		ProductReview rev = null;
		try {
			String qlString = "from ProductReview p where p.id=:id ";
			TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
			query.setParameter("id", id);
			rev = query.getSingleResult();
			if (null == rev) {
				reqError.setValue(ErrorCodes.PRODUCT_REVIEW_DOES_NOT_EXIST.toString());
				reqError = ErrorCodes.PRODUCT_REVIEW_DOES_NOT_EXIST;
				return null;
			} else {
				return rev;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfReviewExists " + id + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.PRODUCT_REVIEW_DOES_NOT_EXIST.toString());
			reqError = ErrorCodes.PRODUCT_REVIEW_DOES_NOT_EXIST;
			return null;
		}
	}
	@Override
	public ErrorCodes deleteReview(ProductReview dbpentry) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			dbpentry = entityManager.merge(dbpentry);
			entityManager.remove(dbpentry);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Review " + dbpentry.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}
	@Override
	public ErrorCodes updateReview(ProductReview dbprodrl) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbprodrl);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Review " + dbprodrl.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	
	@Override
	public List<ProductReview> getReviewForDish(Long product) {
		String qlString = "from ProductReview where product=:product order by addedon";
		TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
		query.setParameter("product", product);
		return query.getResultList();
	}
	@Override
	public List<ProductReview> getReviewForCustomer(Long addedby) {
		String qlString = "from ProductReview where addedby=:addedby order by addedon";
		TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
		query.setParameter("addedby", addedby);
		return query.getResultList();
	}
	@Override
	public ErrorCodes checkIfReviewsExceeded(Long product, Long addedby,Long orderid) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		Long count = (long) 0;
		try {
			String qlString = "select count(*) from ProductReview where addedby=:addedby and product=:product and orderid=:orderid";
			TypedQuery<Long> query = entityManager.createQuery(qlString, Long.class);
			query.setParameter("addedby", addedby);
			query.setParameter("product", product);
			query.setParameter("orderid", orderid);

			count = query.getSingleResult();
			if (count >= DISH_REVIEW_LIMIT) {
				reqError.setValue(ErrorCodes.REVIEW_LIMIT_EXCEEDED.toString());
				reqError = ErrorCodes.REVIEW_LIMIT_EXCEEDED;
				return reqError;
			} else {
				return reqError;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfReviewsExceeded " + product + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.REVIEW_LIMIT_EXCEEDED.toString());
			reqError = ErrorCodes.REVIEW_LIMIT_EXCEEDED;
			return reqError;
		}
	}
	@Override
	public List<ProductReview> getReviewForOrder(Long orderid) {
		try {
			String qlString = "from ProductReview where orderid=:orderid";
			TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
			query.setParameter("orderid", orderid);
			//ProductReview rev =;
			return  query.getResultList();
		} catch (Exception e) {
			logger.error("Exception in getReviewForOrder " + orderid, e);
			return null;
		}
	}
	@Override
	public List<ProductReview> getReviewForDish(Long product, ReviewStatus.type status) {
		String qlString = "from ProductReview where product=:product and status=:status order by addedon";
		TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
		query.setParameter("product", product);
		query.setParameter("status", status);
		return query.getResultList();
	}
	@Override
	public List<ProductReview> getReviewByStatus(com.locoupon.common.enums.ReviewStatus.type status) {
		String qlString = "from ProductReview where status=:status order by addedon";
		TypedQuery<ProductReview> query = entityManager.createQuery(qlString, ProductReview.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

}