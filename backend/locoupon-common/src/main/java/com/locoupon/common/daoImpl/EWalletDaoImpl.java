package com.locoupon.common.daoImpl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.EWalletDao;
import com.locoupon.common.entity.EWallet;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


@Transactional
@Service
public class EWalletDaoImpl implements EWalletDao{

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(EWalletDaoImpl.class);

	public ErrorCodes add(EWallet qt) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		//logger.debug("Saving EWallet " +qt.getOrdercost());
		try{
			entityManager.persist(qt);
			//logger.debug("Saving EWallet Success " +qt.getId());
		}
		catch(Exception e)
		{
			logger.debug("Exception in saving EWallet " ,e);
			reqError.setValue("Hibernate Exception:"+e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
	
		return reqError;
	}

	public ErrorCodes delete(EWallet qt ) {
		logger.debug("delete " +qt.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try{			
			entityManager.remove(qt);
			logger.debug("delete success " +qt.getId());
			return reqError;
		}
		catch(Exception e)
		{
			logger.error("Exception in delete " +qt.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}


	
	
	public EWallet get(Long id) {
		logger.debug("EWallet "+id );
		try{
			 String qlString = "from EWallet where id=:id";
			  TypedQuery<EWallet> query = entityManager.createQuery(qlString, EWallet.class);
					  query.setParameter("id", id);
					 

			  return query.getSingleResult();
		}
		catch(Exception e)
		{
			logger.error("Exception in find EWallet " +id+ e);
			return null;
		}
	}

	
	public ErrorCodes update(EWallet dbaddr) {
		logger.debug("update " +dbaddr.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try{			
			entityManager.merge(dbaddr);
			logger.debug("update success " +dbaddr.getId());
			return reqError;
		}
		catch(Exception e)
		{
			logger.error("Exception in update " +dbaddr.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	
	public List<EWallet> getAll() {
		logger.debug("getAll");
		String qlString = "from EWallet order by id";
		TypedQuery<EWallet> query = entityManager.createQuery(qlString, EWallet.class);		
		return query.getResultList();
	}


}
