package com.locoupon.common.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.MerchantDao;
import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.Merchant;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.EWallet;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.AddressService;
import com.locoupon.common.service.MerchantService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.EWalletService;
import com.locoupon.common.util.UtilFuncs;


@Service
public class MerchantServiceImpl implements MerchantService {

	private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

	@Autowired
	private MerchantDao custDao;

	@Autowired
	private AddressService addressServ;

	@Autowired
	private EWalletService walletServ;
	
	@Autowired
	private DocumentService docServ;
	

	public List<Merchant> getAllMerchant() {
		logger.debug("getAllMerchant Start");
		List<Merchant> fList = new ArrayList<Merchant>();
		try {
			fList = custDao.getAllMerchant();
		} catch (Exception e) {
			logger.error("Exception in getAllMerchant " + e);
			return null;
		}
		return fList;
	}

	public Merchant getMerchantById(Long id) {
		// logger.debug("getMerchantById Start");
		try {
			Merchant cust = custDao.getMerchantById(id);

			return cust;

		} catch (Exception e) {
			logger.error("Exception in fetching getMerchantById ", e);
			return null;
		}
	}

	public Merchant checkIfMerchantExists(Merchant cust, ErrorCodes reqError) {
				String logprefix = " checkIfMerchantExists ";
				logger.debug(logprefix + " Begin");
				Merchant appl = custDao.checkIfMerchantExists(cust.getId(), cust.getLogin().getEmail(),reqError);
				logger.debug(logprefix + " End " + "Error: " + reqError);
				return appl;
		
	}

	public ErrorCodes validateAddMerchant(Merchant cust, Request reqType) {
		String logprefix = " validateAddMerchant ";
		logger.debug("validateAddMerchant Start");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		if (!(cust.getName().trim().isEmpty())) {

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug(logprefix + "Failed Lname is Null");
				reqError.setValue(reqError.INVALID_NAME.toString());
				reqError = ErrorCodes.INVALID_NAME;
				logger.debug(logprefix + " End");
				return reqError;
			}
		}

		cust.setName(UtilFuncs.getNormalizeName(cust.getName()));
		Merchant dbcust = checkIfMerchantExists(cust, reqError);
		if (null != dbcust) {
			logger.debug(logprefix + " Merchant already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_MERCHANT.toString());
			reqError = ErrorCodes.DUPLICATE_MERCHANT;
			logger.debug(logprefix + " End");
			return reqError;
		}

		if (reqError != RequestTypes.ErrorCodes.NONE) {
			logger.debug(logprefix + "Failed Email Id is Null");
			reqError.setValue(reqError.INVALID_EMAIL.toString());
			reqError = ErrorCodes.INVALID_EMAIL;
			logger.debug(logprefix + " End");
			return reqError;
		}
		if(null!=cust.getAddress())
		{
			cust.setAddress(UtilFuncs.convertAddressToCamelCase(cust.getAddress()));
		}

		logger.debug("validateAddMerchant end");
		return reqError;
	}

	public ErrorCodes addMerchant(Merchant cust, Request reqType) {

		String logprefix = "[" + reqType + "] addMerchant ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Date ts = new Date();
		Timestamp currtime = new Timestamp(ts.getTime());
		cust.setAddedon(currtime);
		cust.setName(UtilFuncs.convertToCamelCase(cust.getName()));
		
		
		
		//Add ewallet on salon add
		EWallet eWallet = new EWallet();
		cust.setEwallet(eWallet);	
		reqError = custDao.addMerchant(cust);

		logger.debug(logprefix + " End");
		return reqError;
	}

	public ErrorCodes deleteMerchant(Merchant dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] deleteMerchant Begin");
		try {
			reqError = custDao.deleteMerchant(dbcust);
			logger.debug("[" + reqType + "] deleteMerchant end");
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in deleteMerchant " + e);
			return reqError.ERROR_EXCEPTION;
		}
	}

	public Merchant validateUpdateMerchant(Merchant merchant, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateMerchant ";
		logger.debug(logprefix + " Begin");

		Merchant dbcust = custDao.getMerchantById(merchant.getId());
		if (null == dbcust) {
			reqError.setValue(reqError.MERCHANT_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.MERCHANT_DOES_NOT_EXIST;
			return null;
		}


		if (null != merchant.getPhone() && !(merchant.getPhone().toString().trim().isEmpty())) {
			dbcust.setPhone(merchant.getPhone());
		}

		if (null != merchant.getEmail() && !(merchant.getEmail().toString().trim().isEmpty())) {
			dbcust.setEmail(merchant.getEmail());
		}

		//		if (null != cust.getLogin() && !(cust.getLogin().toString().isEmpty())) {
		//			loginServ.update(dbcust.getLogin().getId(), cust.getLogin());
		//		}
		if (null != merchant.getAddress()) {
			logger.debug(logprefix + " updating Addr");
			Address newAddr = addressServ.update(dbcust.getAddress().getId(), UtilFuncs.convertAddressToCamelCase(merchant.getAddress()));
			if (newAddr != null) {
				dbcust.setAddress(newAddr);
			}
		}
		if(null!=merchant.getEwallet())
		{
			EWallet dbwallet = walletServ.update(dbcust.getEwallet().getId(), merchant.getEwallet());
			if(dbwallet!=null)
			{
				dbcust.setEwallet(dbwallet);
			}
		}

		// update photo
		if (null != merchant.getPhoto() && null != merchant.getPhoto().getDocname()
				&& !(merchant.getPhoto().getDocname().trim().isEmpty())) {
			logger.debug(" Validate incoming photo ");
			try {
				reqError = docServ.validate(merchant.getPhoto());
			} catch (Exception e) {
				logger.debug(" Exception in  doc validate", e);
				reqError = ErrorCodes.ERROR_EXCEPTION;
				return null;
			}

			logger.debug(" Check if photo exists with dbCust");
			if (null == dbcust.getPhoto()) {
				logger.debug("Photo is being added first time" + merchant.getPhoto().getDocname());

				try {
					Document doc = docServ.add(merchant.getPhoto());
					dbcust.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}
			} else {
				logger.debug("Photo Exists; delete old and add new updateON date " + merchant.getPhoto().getDocname());
				Document dbPhoto = dbcust.getPhoto();

				try {
					reqError = docServ.deleteDoc(dbPhoto);
				} catch (Exception e) {
					logger.debug(" Exception in  doc delete", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}

				logger.debug("Deleted old, added new" + merchant.getPhoto().getDocname());
				try {
					Document doc = docServ.add(merchant.getPhoto());
					dbcust.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}
			}
		}

		logger.debug(logprefix + " End");
		return dbcust;
	}

	public ErrorCodes updateMerchant(Merchant dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateMerchant Begin");
		try {
			if (null != dbcust) {
				reqError = custDao.updateMerchant(dbcust);
			} else {
				logger.debug("dbcust is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateMerchant ", e);
		}
		logger.debug("[" + reqType + "] updateMerchant End");
		return reqError;
	}

	@Override
	public Merchant getMerchantByEmail(String username) {
		logger.debug("getMerchantByEmail: " + username);
		try {
			return custDao.getMerchantByEmail(username);
		} catch (Exception e) {
			logger.debug("getMerchantByEmail ", e);
			return null;
		}
	}

	
	
	@Override
	public ErrorCodes updateEWallet(Long Merchant, QCost qcost) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug(" updateEwallet Begin For Merchant:" + Merchant);
		Merchant dbMerchant = this.getMerchantById(Merchant);
		
		reqError = walletServ.updateWallet(dbMerchant.getEwallet(), qcost);
		return reqError;
	
	}

	
	@Transient
	@Override
	public ErrorCodes addFilesToMerchant(Merchant dbproduct, Set<Document> photos, Request reqType) {
		String logprefix = "[" + reqType + "] addFilesToMerchant ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Set<Document> productPhotos = dbproduct.getFiles();
		if (null == productPhotos) {
			productPhotos = new HashSet<Document>(0);
		}
		if (null != dbproduct && photos.size() > 0) {
			for (Document photo : photos) {
				reqError = docServ.validate(photo);
				if (reqError != ErrorCodes.NONE) {
					logger.debug("Validation failed for doc " + reqError);
					continue;
				}
				logger.debug("Adding files to Merchant: " + photo.getDocname());
				Document dbDoc = docServ.add(photo);
				productPhotos.add(dbDoc);
			}
		}
		logger.debug("Setting Merchantfiles to cust: " + productPhotos.size());

		dbproduct.setFiles(productPhotos);
		try {
			reqError = custDao.updateMerchant(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}

	@Override
	public ErrorCodes deleteMultipleMerchantFiles(Merchant dbproduct, List<Long> ids, Request reqType) {
		String logprefix = "[" + reqType + "] deleteFiles ";
		logger.debug(logprefix + " Begin " + ids.size());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Set<Document> autoPhoto = dbproduct.getFiles();

		if (autoPhoto.size() > 0) {
			for (Long id : ids) {
				for (Document photo : autoPhoto) {
					if (id.equals(photo.getId())) {
						logger.debug("Found Photo to remove : " + id);
						dbproduct.deleteFiles(photo);
						reqError = docServ.deleteDoc(photo);
						break;
					}
				}
			}
		}

		logger.debug(logprefix + " Product photo size after remove = " + dbproduct.getFiles().size());
		try {
			reqError = custDao.updateMerchant(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}

	
}
