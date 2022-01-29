package com.locoupon.common.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.EWalletDao;
import com.locoupon.common.entity.EWallet;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.EWalletService;


@Service
public class EWalletServiceImpl implements EWalletService{

	private static final Logger logger = LoggerFactory.getLogger(EWalletServiceImpl.class);
	
	@Autowired
	private EWalletDao walletDao ;
	
	

	public ErrorCodes add(EWallet qt, Request reqType) {
		String logprefix = "["+reqType+"] add EWallet ";
        logger.debug(logprefix + " Begin");
        RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
        
        
        
		reqError = walletDao.add(qt);
		logger.debug(logprefix + " End");
		return reqError;
	}



	@Override
	public EWallet update(Long id, EWallet qt) {
		String logprefix = "["+id+"] update EWallet ";
        logger.debug(logprefix + " Begin");
        RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
        
		EWallet dbobj = null;
		try {
			dbobj = walletDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dbobj == null)
		{
			logger.debug(logprefix + " Failed, EWallet does not exist");
			return null;
		}
		
		if(null != qt)
		{
			dbobj.setAvailed(qt.getAvailed());
			dbobj.setBalance(qt.getBalance());
			dbobj.setPoints(qt.getPoints());
			dbobj.setTotal(qt.getTotal());
			dbobj.setValidtill(qt.getValidtill());
		}
			
		
		reqError = walletDao.update(dbobj);
		if(reqError != RequestTypes.ErrorCodes.NONE )
		{
			  logger.debug(logprefix + " Failed");
			  return null;
		}
		
		logger.debug(logprefix + " End");
		return dbobj;
	}

//
	public ErrorCodes delete(EWallet qt, Request reqType) {
		String logprefix = "["+reqType+"] delete EWallet";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;

		EWallet dbobj = walletDao.get(qt.getId());
		if (null == dbobj) {
			reqError.setValue(reqError.EWALLET_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.EWALLET_DOES_NOT_EXIST ;
			return reqError;
		}
		logger.debug(logprefix + " Found EWallet" + qt.getId());
		reqError = walletDao.delete(dbobj);
		logger.debug(logprefix + " End");
		return reqError;
	}




	@Override
	public List<EWallet> getAll() {
		logger.debug("getAll Start");
		return walletDao.getAll();
	}



	@Override
	public EWallet get(Long id) {
		logger.debug("get Start");
		return walletDao.get(id);
	}




	@Override
	public ErrorCodes addBookingAmtToEWallet(EWallet ewallet, QCost qcost) {
		String logprefix = "["+ewallet.getId()+"] addBookingAmtToWewallet ";
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
        logger.debug(logprefix + " Begin");
        
       /* Double currbalance = ewallet.getBalance();
        Double currtotal = ewallet.getTotal();
        
        double newbalance = currbalance+qcost.getBookingamt();
        double newtotal = currtotal+qcost.getBookingamt();
        
        ewallet.setBalance(newbalance);
        ewallet.setTotal(newtotal);*/
        reqError = walletDao.update(ewallet);
        
        logger.debug(logprefix + " End");
        
		return reqError;
        
	}




	@Override
	public ErrorCodes updateWallet(EWallet ewallet, QCost qcost) {
		String logprefix = "["+ewallet.getId()+"] updateWallet ";
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
        logger.debug(logprefix + " Begin");
        
        Double currbalance = ewallet.getBalance();
        Double curravailed = ewallet.getAvailed();
        
        /*double newbalance = currbalance-qcost.getEwalletamt();
        double newavailed = curravailed+qcost.getEwalletamt();
        
        ewallet.setBalance(newbalance);
        ewallet.setAvailed(newavailed);*/
        reqError = walletDao.update(ewallet);

        
        logger.debug(logprefix + " End");
        
		return reqError;
        
	}

}
