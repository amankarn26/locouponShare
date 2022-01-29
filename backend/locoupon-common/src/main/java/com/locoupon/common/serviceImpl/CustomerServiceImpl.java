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

import com.locoupon.common.dao.CustomerDao;
import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.Customer;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.EWallet;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.CompactCustomer;
import com.locoupon.common.service.AddressService;
import com.locoupon.common.service.CustomerService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.EWalletService;
import com.locoupon.common.util.UtilFuncs;


@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao custDao;

	@Autowired
	private AddressService addressServ;

	@Autowired
	private EWalletService walletServ;

	@Autowired
	private DocumentService docServ;


	public List<Customer> getAllCustomer() {
		logger.debug("getAllCustomer Start");
		List<Customer> fList = new ArrayList<Customer>();
		try {
			fList = custDao.getAllCustomer();
		} catch (Exception e) {
			logger.error("Exception in getAllCustomer " + e);
			return null;
		}
		return fList;
	}

	public Customer getCustomerById(Long id) {
		// logger.debug("getCustomerById Start");
		try {
			Customer cust = custDao.getCustomerById(id);

			return cust;

		} catch (Exception e) {
			logger.error("Exception in fetching getCustomerById ", e);
			return null;
		}
	}

	public Customer checkIfCustomerExists(Customer cust, ErrorCodes reqError) {
		String logprefix = " checkIfCustomerExists ";
		logger.debug(logprefix + " Begin");
		Customer appl = custDao.checkIfCustomerExists(cust.getId(), cust.getLogin().getEmail(),reqError);
		logger.debug(logprefix + " End " + "Error: " + reqError);
		return appl;

	}

	public ErrorCodes validateAddCustomer(Customer cust, Request reqType) {
		String logprefix = " validateAddCustomer ";
		logger.debug("validateAddCustomer Start");
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
		Customer dbcust = checkIfCustomerExists(cust, reqError);
		if (null != dbcust) {
			logger.debug(logprefix + " Customer already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_CUSTOMER.toString());
			reqError = ErrorCodes.DUPLICATE_CUSTOMER;
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

		logger.debug("validateAddCustomer end");
		return reqError;
	}

	public ErrorCodes addCustomer(Customer cust, Request reqType) {

		String logprefix = "[" + reqType + "] addCustomer ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Date ts = new Date();
		Timestamp currtime = new Timestamp(ts.getTime());
		cust.setAddedon(currtime);
		cust.setName(UtilFuncs.convertToCamelCase(cust.getName()));



		//Add ewallet on salon add
		EWallet eWallet = new EWallet();
		cust.setEwallet(eWallet);	
		reqError = custDao.addCustomer(cust);

		logger.debug(logprefix + " End");
		return reqError;
	}

	public ErrorCodes deleteCustomer(Customer dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] deleteCustomer Begin");
		try {
			reqError = custDao.deleteCustomer(dbcust);
			logger.debug("[" + reqType + "] deleteCustomer end");
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in deleteCustomer " + e);
			return reqError.ERROR_EXCEPTION;
		}
	}

	public Customer validateUpdateCustomer(Customer cust, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateCustomer ";
		logger.debug(logprefix + " Begin");

		Customer dbcust = custDao.getCustomerById(cust.getId());
		if (null == dbcust) {
			reqError.setValue(reqError.CUSTOMER_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
			return null;
		}


		if (null != cust.getPhone() && !(cust.getPhone().toString().trim().isEmpty())) {
			dbcust.setPhone(cust.getPhone());
		}

		if (null != cust.getEmail() && !(cust.getEmail().toString().trim().isEmpty())) {
			dbcust.setEmail(cust.getEmail());
		}

		//		if (null != cust.getLogin() && !(cust.getLogin().toString().isEmpty())) {
		//			loginServ.update(dbcust.getLogin().getId(), cust.getLogin());
		//		}
		if (null != cust.getAddress()) {
			logger.debug(logprefix + " updating Addr");
			Address newAddr = addressServ.update(dbcust.getAddress().getId(), UtilFuncs.convertAddressToCamelCase(cust.getAddress()));
			if (newAddr != null) {
				dbcust.setAddress(newAddr);
			}
		}
		if(null!=cust.getEwallet())
		{
			EWallet dbwallet = walletServ.update(dbcust.getEwallet().getId(), cust.getEwallet());
			if(dbwallet!=null)
			{
				dbcust.setEwallet(dbwallet);
			}
		}
		if(null!=cust.getAadhar())
		{
			dbcust.setAadhar(cust.getAadhar());
		}
		if(null!=cust.getPan())
		{
			dbcust.setPan(cust.getPan());
		}
		//logger.debug("rem :check photo"+cust.getPhoto().getDocname());
		// update photo
		if (null != cust.getPhoto() && null != cust.getPhoto().getDocname()
				&& !(cust.getPhoto().getDocname().trim().isEmpty())) {
			logger.debug(" Validate incoming photo ");
			try {
				reqError = docServ.validate(cust.getPhoto());
			} catch (Exception e) {
				logger.debug(" Exception in  doc validate", e);
				reqError = ErrorCodes.ERROR_EXCEPTION;
				return null;
			}

			logger.debug(" Check if photo exists with dbCust");
			if (null == dbcust.getPhoto()) {
				logger.debug("Photo is being added first time" + cust.getPhoto().getDocname());

				try {
					Document doc = docServ.add(cust.getPhoto());
					dbcust.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}
			} else {
				logger.debug("Photo Exists; delete old and add new updateON date " + cust.getPhoto().getDocname());
				Document dbPhoto = dbcust.getPhoto();

				try {
					reqError = docServ.deleteDoc(dbPhoto);
				} catch (Exception e) {
					logger.debug(" Exception in  doc delete", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}

				logger.debug("Deleted old, added new" + cust.getPhoto().getDocname());
				try {
					Document doc = docServ.add(cust.getPhoto());
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

	public ErrorCodes updateCustomer(Customer dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateCustomer Begin");
		try {
			if (null != dbcust) {
				reqError = custDao.updateCustomer(dbcust);
			} else {
				logger.debug("dbcust is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateCustomer ", e);
		}
		logger.debug("[" + reqType + "] updateCustomer End");
		return reqError;
	}

	@Override
	public Customer getCustomerByEmail(String username) {
		logger.debug("getCustomerByEmail: " + username);
		try {
			return custDao.getCustomerByEmail(username);
		} catch (Exception e) {
			logger.debug("getCustomerByEmail ", e);
			return null;
		}
	}

	@Override
	public Long getAllCount(Long firm) {
		logger.debug("getAllCount Start");
		try {
			Long total = custDao.getTotalCount(firm);
			return total;
		} catch (Exception e) {
			logger.error("Exception in fetching getAllCount " + e);
			return (long) 0;
		}
	}

	@Override
	public List<Customer> getAllCustomer(String scity) {
		logger.debug("getAllCustomer Start:" + scity);
		List<Customer> cList = new ArrayList<Customer>();
		try {
			cList = custDao.getAllCustomer(scity);
		} catch (Exception e) {
			logger.error("Exception in getAllCustomers " + e);
			return null;
		}
		return cList;

	}

	@Override
	public Customer getCustomerByName(String name) {
		logger.debug("getCustomerByName Start");
		try {
			Customer cust = custDao.getCustomerByName(name);

			return cust;
		} catch (Exception e) {
			logger.error("Exception in fetching getCustomerByName ", e);
			return null;
		}
	}

	@Override
	public Long getCustIDByName(String custname) {
		logger.debug("getCustIDByName Start");
		try {
			Long custid = custDao.getCustIDByName(custname);
			return custid;
		} catch (Exception e) {
			logger.error("Exception in fetching getCustIDByName ", e);
			return null;
		}
	}


	@Override
	public ErrorCodes updateEWallet(Long customer, QCost qcost) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug(" updateEwallet Begin For Customer:" + customer);
		Customer dbcustomer = this.getCustomerById(customer);

		reqError = walletServ.updateWallet(dbcustomer.getEwallet(), qcost);
		return reqError;

	}

	@Override
	public ErrorCodes checkIfCustomerIsINUse(Customer cust, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer checkCustomerExistByMobile(String mob, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomerByPincode(String pincode) {
		// TODO Auto-generated method stub
		return null;
	}


	@Transient
	@Override
	public ErrorCodes addFilesToCustomer(Customer dbproduct, Set<Document> photos, Request reqType) {
		String logprefix = "[" + reqType + "] addFilesToCustomer ";
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
				logger.debug("Adding files to customer: " + photo.getDocname());
				Document dbDoc = docServ.add(photo);
				productPhotos.add(dbDoc);
			}
		}
		logger.debug("Setting customerfiles to cust: " + productPhotos.size());

		dbproduct.setFiles(productPhotos);
		try {
			reqError = custDao.updateCustomer(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}

	@Override
	public ErrorCodes deleteMultipleCustomerFiles(Customer dbproduct, List<Long> ids, Request reqType) {
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
			reqError = custDao.updateCustomer(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}


}
