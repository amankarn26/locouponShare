package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.SystemConfigDao;
import com.locoupon.common.entity.SystemConfig;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


@Transactional
@Service
public class SystemConfigDaoImpl implements SystemConfigDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigDaoImpl.class);

	@Override
	public List<SystemConfig> getAll(Long firm) {
		logger.debug("getAll");
		String qlString = "from SystemConfig where firm=:firm order by id";
		TypedQuery<SystemConfig> query = entityManager.createQuery(qlString, SystemConfig.class);
		query.setParameter("firm", firm);

		return query.getResultList();
	}

	@Override
	public SystemConfig getSystemConfigById(Long id) {
		logger.debug("getAll");
		String qlString = "from SystemConfig where id=:id";
		TypedQuery<SystemConfig> query = entityManager.createQuery(qlString, SystemConfig.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public ErrorCodes deleteSystemConfig(SystemConfig dbsupp) {
		logger.debug("deleteSystemConfig " + dbsupp.getFirm());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbsupp);
			// logger.debug("delete success " +);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete " + dbsupp.getFirm(), e);

			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes addSystemConfig(SystemConfig sys) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("Saving SystemConfig " + sys.getFirm());
		try {
			// entityManager.persist(appl.getAddress());
			entityManager.persist(sys);
			logger.debug("Saving SystemConfig Success " + sys.getFirm());
		} catch (Exception e) {
			logger.debug("Exception in saving SystemConfig ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return ErrorCodes.HIBERNATE_EXCEPTION;
		}
		// logger.debug("Saving Applicant Success " +appl.getFname());
		return reqError;
	}

}
