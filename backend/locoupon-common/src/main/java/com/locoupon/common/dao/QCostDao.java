package com.locoupon.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


@Transactional
@Service
public class QCostDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(QCostDao.class);

	public ErrorCodes addQcost(QCost qt) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(qt);
		} catch (Exception e) {
			logger.debug("Exception in saving QCost ", e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}

		return reqError;
	}

	public ErrorCodes deleteQcost(QCost qt) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			qt = entityManager.merge(qt);
			entityManager.remove(qt);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete " + qt.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	public QCost getQcost(Long id) {
		String logprefix = id.toString();
		try {
			String qlString = "from QCost where id=:id";
			TypedQuery<QCost> query = entityManager.createQuery(qlString, QCost.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in find QCost " + logprefix, e);
			return null;
		}
	}

	public ErrorCodes updateQcost(QCost dbaddr) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbaddr);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update " + dbaddr.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

}
