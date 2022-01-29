package com.locoupon.common.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.MerchantOfferDao;
import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.ProductService;
import com.locoupon.common.service.MerchantOfferService;


@Service
public class MerchantOfferServiceImpl implements MerchantOfferService {

	private static final Logger logger = LoggerFactory.getLogger(MerchantOfferServiceImpl.class);

	@Autowired
	private MerchantOfferDao prodstockDao;

	
	@Override
	public List<MerchantOffer> getAllProdStock() {
		logger.debug("getAllProdStock Start");
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		try {
			aList = prodstockDao.getAllProdStock();
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdStock " + e);
			return null;
		}
		return aList;
	}
	
	
	
	@Override
	public List<MerchantOffer> getAllProdStock(Integer index, Integer limit) {
		logger.debug("getAllProdStock Start");
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		try {
			aList = prodstockDao.getAllProdStock(index, limit);
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdStock " + e);
			return null;
		}
		return aList;
	}


	@Override
	public MerchantOffer getProdStockById(Long id) {
		logger.debug("getProdStockById Start");
		try {
			MerchantOffer dish = prodstockDao.getProdStockById(id);
			
			return dish;
		} catch (Exception e) {
			logger.error("Exception in fetching getProdStockById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes validateAddProdStock(MerchantOffer dish, Request reqType) {
		String logprefix = " validateAddProdStock ";
		logger.debug("validateAddProdStock Start");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		

		MerchantOffer dbprod = checkIfProdStockExists(dish, reqError);
		if (null != dbprod) {
			logger.debug(logprefix + " ProdStock already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_PRODUCTSTOCK.toString());
			reqError = ErrorCodes.DUPLICATE_PRODUCTSTOCK;
			logger.debug(logprefix + " End");
			return reqError;
		}
		reqError = prodstockDao.addProdStock(dish);
		logger.debug("validateAddProdStock end");
		
		return reqError;
	}

	private MerchantOffer checkIfProdStockExists(MerchantOffer pstock, ErrorCodes reqError) {
		
		MerchantOffer dbpstock = prodstockDao.checkIfProdStockExists(pstock.getMerchant(),pstock.getProduct());
		if(dbpstock!=null)
		{
			return dbpstock;
		}
		return null;
	}


	@Override
	public ErrorCodes deleteProdStock(MerchantOffer dbproduct, Request reqType) {

		String logprefix = "[" + reqType + "] delete ProdStock ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		reqError = prodstockDao.deleteProdStock(dbproduct,reqType);
		
		logger.debug(logprefix + " End");
		return reqError;

	}

	@Override
	public MerchantOffer validateUpdateProdStock(MerchantOffer prodstk, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateProdStock ";
		logger.debug(logprefix + " Begin");

		MerchantOffer dbdish = prodstockDao.getProdStockById(prodstk.getId());
		if (null == dbdish) {
			reqError.setValue(reqError.PRODUCT_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
			return null;
		}

		if(null!=prodstk.getGst().toString())
		{
			dbdish.setGst(prodstk.getGst());
		}
		
		
		logger.debug(logprefix + " End");
		return dbdish;
	}

	@Override
	public ErrorCodes updateProdStock(MerchantOffer dbproduct, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateProdStock Begin");
		try {
			if (null != dbproduct) {
				reqError = prodstockDao.updateProdStock(dbproduct);
			} else {
				logger.debug("dbproduct is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateProdStock ", e);
		}
		logger.debug("[" + reqType + "] updateProdStock End");
		return reqError;
	}

	@Override
	public List<MerchantOffer> getAllProdStock(Long product, Long firm) {
		logger.debug("getAllProdStock Start : Product:"+product+":firm:"+firm);
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		try {
			aList = prodstockDao.getAllProdStock(product, firm);
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdStock firm" + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<MerchantOffer> getAllProdStock(Long product) {
		logger.debug("getAllProdStock Start PRoduct:");
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		try {
			aList = prodstockDao.getAllProdStock(product);
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdStock Product" + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<MerchantOffer> getProdStockByMerchant(Long merchant) {
		logger.debug("getAllProdStock Start PRoduct:");
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		try {
			aList = prodstockDao.getProdStockByMerchant(merchant);
			
		} catch (Exception e) {
			logger.error("Exception in getProdStockByMerchant " + e);
			return null;
		}
		return aList;
	}
	
	@Override
	public List<Long> getAllProdList(Long firm) {
		logger.debug("getAllProdList Start");
		List<Long> aList = new ArrayList<Long>();
		try {
			aList = prodstockDao.getAllProdList(firm);
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdList " + e);
			return null;
		}
		return aList;
	}

	@Override
	public MerchantOffer getProductStockByPname(Long firm, String pname, String variant) {
		logger.debug("getProductStockByPname Start");
	
		try {
			MerchantOffer	aList = prodstockDao.getProductStockByPname(firm,pname,variant);
			return aList;
		} catch (Exception e) {
			logger.error("Exception in getProductStockByPname " + e);
			return null;
		}
		
	}

	@Override
	public List<Long> getAllProdList() {
		logger.debug("getAllProdList Start");
		List<Long> aList = new ArrayList<Long>();
		try {
			aList = prodstockDao.getAllProdList();
			
		} catch (Exception e) {
			logger.error("Exception in getAllProdList " + e);
			return null;
		}
		return aList;
	}


	@Override
	public ErrorCodes addProdStock(MerchantOffer prodstk, Request reqType) {
		String logprefix = "[" + reqType + "] addProdStock ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		logger.debug(logprefix + " validateAddProductStock Begin");
		reqError = this.validateAddProdStock(prodstk, reqType);
		if (reqError != RequestTypes.ErrorCodes.NONE) {
			logger.debug(logprefix + " validateAddProductStock Failed");
			return reqError;
		}
		
		
		reqError = prodstockDao.addProdStock(prodstk);

		// updateStockUnit(prodstk,reqType);zs

		logger.debug(logprefix + " End");
		return reqError;
	}
	

}
