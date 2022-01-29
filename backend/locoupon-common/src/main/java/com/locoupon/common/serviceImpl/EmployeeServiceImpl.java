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

import com.locoupon.common.dao.EmployeeDao;
import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.Employee;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.EWallet;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.AddressService;
import com.locoupon.common.service.EmployeeService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.EWalletService;
import com.locoupon.common.util.UtilFuncs;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDao custDao;

	@Autowired
	private AddressService addressServ;

	@Autowired
	private EWalletService walletServ;
	
	@Autowired
	private DocumentService docServ;
	

	public List<Employee> getAllEmployee() {
		logger.debug("getAllEmployee Start");
		List<Employee> fList = new ArrayList<Employee>();
		try {
			fList = custDao.getAllEmployee();
		} catch (Exception e) {
			logger.error("Exception in getAllEmployee " + e);
			return null;
		}
		return fList;
	}

	public Employee getEmployeeById(Long id) {
		// logger.debug("getEmployeeById Start");
		try {
			Employee cust = custDao.getEmployeeById(id);

			return cust;

		} catch (Exception e) {
			logger.error("Exception in fetching getEmployeeById ", e);
			return null;
		}
	}

	public Employee checkIfEmployeeExists(Employee cust, ErrorCodes reqError) {
				String logprefix = " checkIfEmployeeExists ";
				logger.debug(logprefix + " Begin");
				Employee appl = custDao.checkIfEmployeeExists(cust.getId(), cust.getLogin().getEmail(),reqError);
				logger.debug(logprefix + " End " + "Error: " + reqError);
				return appl;
		
	}

	public ErrorCodes validateAddEmployee(Employee cust, Request reqType) {
		String logprefix = " validateAddEmployee ";
		logger.debug("validateAddEmployee Start");
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
		Employee dbcust = checkIfEmployeeExists(cust, reqError);
		if (null != dbcust) {
			logger.debug(logprefix + " Employee already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_EMPLOYEE.toString());
			reqError = ErrorCodes.DUPLICATE_EMPLOYEE;
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

		logger.debug("validateAddEmployee end");
		return reqError;
	}

	public ErrorCodes addEmployee(Employee cust, Request reqType) {

		String logprefix = "[" + reqType + "] addEmployee ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Date ts = new Date();
		Timestamp currtime = new Timestamp(ts.getTime());
		cust.setAddedon(currtime);
		cust.setName(UtilFuncs.convertToCamelCase(cust.getName()));
		
		
		
		//Add ewallet on salon add
		EWallet eWallet = new EWallet();
		cust.setEwallet(eWallet);	
		reqError = custDao.addEmployee(cust);

		logger.debug(logprefix + " End");
		return reqError;
	}

	public ErrorCodes deleteEmployee(Employee dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] deleteEmployee Begin");
		try {
			reqError = custDao.deleteEmployee(dbcust);
			logger.debug("[" + reqType + "] deleteEmployee end");
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in deleteEmployee " + e);
			return reqError.ERROR_EXCEPTION;
		}
	}

	public Employee validateUpdateEmployee(Employee emp, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateEmployee ";
		logger.debug(logprefix + " Begin");

		Employee dbcust = custDao.getEmployeeById(emp.getId());
		if (null == dbcust) {
			reqError.setValue(reqError.EMPLOYEE_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.EMPLOYEE_DOES_NOT_EXIST;
			return null;
		}


		if (null != emp.getPhone() && !(emp.getPhone().toString().trim().isEmpty())) {
			dbcust.setPhone(emp.getPhone());
		}

		if (null != emp.getEmail() && !(emp.getEmail().toString().trim().isEmpty())) {
			dbcust.setEmail(emp.getEmail());
		}

		//		if (null != cust.getLogin() && !(cust.getLogin().toString().isEmpty())) {
		//			loginServ.update(dbcust.getLogin().getId(), cust.getLogin());
		//		}
		if (null != emp.getAddress()) {
			logger.debug(logprefix + " updating Addr");
			Address newAddr = addressServ.update(dbcust.getAddress().getId(), UtilFuncs.convertAddressToCamelCase(emp.getAddress()));
			if (newAddr != null) {
				dbcust.setAddress(newAddr);
			}
		}
		if(null!=emp.getEwallet())
		{
			EWallet dbwallet = walletServ.update(dbcust.getEwallet().getId(), emp.getEwallet());
			if(dbwallet!=null)
			{
				dbcust.setEwallet(dbwallet);
			}
		}
		
		// update photo
				if (null != emp.getPhoto() && null != emp.getPhoto().getDocname()
						&& !(emp.getPhoto().getDocname().trim().isEmpty())) {
					logger.debug(" Validate incoming photo ");
					try {
						reqError = docServ.validate(emp.getPhoto());
					} catch (Exception e) {
						logger.debug(" Exception in  doc validate", e);
						reqError = ErrorCodes.ERROR_EXCEPTION;
						return null;
					}

					logger.debug(" Check if photo exists with dbCust");
					if (null == dbcust.getPhoto()) {
						logger.debug("Photo is being added first time" + emp.getPhoto().getDocname());

						try {
							Document doc = docServ.add(emp.getPhoto());
							dbcust.setPhoto(doc);
						} catch (Exception e) {
							logger.debug(" Exception in  doc add", e);
							reqError = ErrorCodes.ERROR_EXCEPTION;
							return null;
						}
					} else {
						logger.debug("Photo Exists; delete old and add new updateON date " + emp.getPhoto().getDocname());
						Document dbPhoto = dbcust.getPhoto();

						try {
							reqError = docServ.deleteDoc(dbPhoto);
						} catch (Exception e) {
							logger.debug(" Exception in  doc delete", e);
							reqError = ErrorCodes.ERROR_EXCEPTION;
							return null;
						}

						logger.debug("Deleted old, added new" + emp.getPhoto().getDocname());
						try {
							Document doc = docServ.add(emp.getPhoto());
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

	public ErrorCodes updateEmployee(Employee dbcust, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateEmployee Begin");
		try {
			if (null != dbcust) {
				reqError = custDao.updateEmployee(dbcust);
			} else {
				logger.debug("dbcust is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateEmployee ", e);
		}
		logger.debug("[" + reqType + "] updateEmployee End");
		return reqError;
	}

	@Override
	public Employee getEmployeeByEmail(String username) {
		logger.debug("getEmployeeByEmail: " + username);
		try {
			return custDao.getEmployeeByEmail(username);
		} catch (Exception e) {
			logger.debug("getEmployeeByEmail ", e);
			return null;
		}
	}

	
	@Override
	public ErrorCodes updateEWallet(Long Employee, QCost qcost) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug(" updateEwallet Begin For Employee:" + Employee);
		Employee dbEmployee = this.getEmployeeById(Employee);
		
		reqError = walletServ.updateWallet(dbEmployee.getEwallet(), qcost);
		return reqError;
	
	}

	
	
	
}
