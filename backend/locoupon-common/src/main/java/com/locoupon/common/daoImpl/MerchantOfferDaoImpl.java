package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.MerchantOfferDao;
import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;


@Transactional
@Service
public class MerchantOfferDaoImpl implements MerchantOfferDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(MerchantOfferDaoImpl.class);

	@Override
	public List<MerchantOffer> getAllProdStock() {
		logger.debug("getAllProdStock");
		String qlString = "from MerchantOffer";
		TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		return query.getResultList();
	}
	
	@Override
	public List<MerchantOffer> getAllProdStock(Integer index, Integer limit) {
		logger.debug("getAllProdStock");
		String qlString = "from MerchantOffer ";
		TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		query.setFirstResult(index);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	


	@Override
	public List<MerchantOffer> getAllProdStock(Long product,Long merchant) {
		String qlString = "from MerchantOffer where merchant=:merchant and product=:product";
		logger.debug("QlString:"+qlString);
		TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		query.setParameter("merchant", merchant);
		query.setParameter("product", product);
		return query.getResultList();
	}
	
	@Override
	public List<MerchantOffer> getAllProdStock(Long product) {
		String qlString = "from MerchantOffer where product=:product";
		TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		query.setParameter("product", product);
		return query.getResultList();
	}
	
	@Override
	public List<MerchantOffer> getProdStockByMerchant(Long merchant) {
		String qlString = "from MerchantOffer where merchant=:merchant";
		TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		query.setParameter("merchant", merchant);
		return query.getResultList();
	}

	@Override
	public MerchantOffer getProdStockById(Long id) {
		logger.debug("getProdStockById " + id);
		try {
			String qlString = "from MerchantOffer where id=:id";
			TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
			query.setParameter("id", id);

			MerchantOffer ProdStock = query.getSingleResult();
			return ProdStock;
		} catch (Exception e) {
			logger.error("Exception in getProdStockById " + id, e);
			return null;
		}
	}

	
	@Override
	public ErrorCodes deleteProdStock(MerchantOffer dbProdStock,Request reqType) {
		logger.debug("delete ProdStock " + dbProdStock.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbProdStock);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete ProdStock " + dbProdStock.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes updateProdStock(MerchantOffer dbProdStock) {

		logger.debug("update ProdStock " + dbProdStock.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbProdStock);
			// logger.debug("update ProdStock success " + dbProdStock.getId());
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update ProdStock " + dbProdStock.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes addProdStock(MerchantOffer ProdStock) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		// logger.debug("Saving ProdStock " +ProdStock.getId());
		try {
			entityManager.persist(ProdStock);
			// logger.debug("Saving ProdStock Success " +ProdStock.getId());
		} catch (Exception e) {
			logger.debug("Exception in saving ProdStock ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public MerchantOffer checkIfProdStockExists(Long product,String variant,String pname) {
		logger.debug("checkIfProdStockExists " + pname);

		MerchantOffer prod = null;
		try {
			String qlString = "from MerchantOffer where product=:product";
			TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
		
			query.setParameter("product",product);

			prod = query.getSingleResult();
			if (null == prod) {
				
				return null;
			} else {
				return prod;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfProdStockExists " + pname + " Exception: " + e.getMessage());
			
			return null;
		}
	}

	@Override
	public List<Long> getAllProdList(Long firm) {
		String qlString = "select o.product from ProdStock o where o.firm=:firm GROUP BY o.product";
		TypedQuery<Long> query = entityManager.createQuery(qlString, Long.class);
		query.setParameter("firm", firm);
		return query.getResultList();
	}

	@Override
	public List<Long> getAllProdList() {
		String qlString = "select product from ProdStock GROUP BY product";
		TypedQuery<Long> query = entityManager.createQuery(qlString, Long.class);
		
		return query.getResultList();
	}

	
	@Override
	public MerchantOffer getProductStockByPname(Long firm, String pname, String variant) {
		logger.debug("getProductStockByPname " + pname);

		MerchantOffer prod = null;
		try {
			String qlString = "from ProdStock where pname=:pname and variant=:variant and firm=:firm";
			TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
			query.setParameter("pname", pname);
			query.setParameter("variant", variant);
			query.setParameter("firm",firm);

			prod = query.getSingleResult();
			
				return prod;
			
		} catch (Exception e) {
			logger.error("Exception in getProductStockByPname " + pname + " Exception: " + e.getMessage());
			
			return null;
		}
	}

	@Override
	public MerchantOffer checkIfProdStockExists(Long merchant, Long product) {
		logger.debug("checkIfProdStockExists " );

		MerchantOffer prod = null;
		try {
			String qlString = "from MerchantOffer where merchant=:merchant and product=:product";
			TypedQuery<MerchantOffer> query = entityManager.createQuery(qlString, MerchantOffer.class);
			query.setParameter("merchant", merchant);
			query.setParameter("product", product);
			prod = query.getSingleResult();
			if (null == prod) {
				
				return null;
			} else {
				return prod;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfProdStockExists Exception: " + e.getMessage());
			
			return null;
		}
	}

	


	

}