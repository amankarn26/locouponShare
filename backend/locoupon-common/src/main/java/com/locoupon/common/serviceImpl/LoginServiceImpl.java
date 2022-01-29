package com.locoupon.common.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.LoginDao;
import com.locoupon.common.entity.Customer;
import com.locoupon.common.entity.Login;
import com.locoupon.common.enums.LoginType;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.CustomerService;
import com.locoupon.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private CustomerService custServ;
	


	public ErrorCodes add(Login login, Request reqType) {
		String logprefix = "[" + reqType + "] add Login ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = loginDao.add(login);
		logger.debug(logprefix + " End");
		return reqError;
	}

	public Login update(Long id, Login login) {
		String logprefix = "[" + id + "] update Login ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;
		Login dblogin = null;
		try {
			dblogin = loginDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dblogin == null) {
			logger.debug(logprefix + " Failed, Login does not exist");
			return null;
		}
		dblogin.setPassword(login.getPassword());

		reqError = loginDao.update(dblogin);
		if (reqError != RequestTypes.ErrorCodes.NONE) {
			logger.debug(logprefix + " Failed");
			return null;
		}
		logger.debug(logprefix + " End");
		return dblogin;
	}

	public ErrorCodes delete(Login login, Request reqType) {
		String logprefix = "[" + reqType + "] delete Login";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Login dblogin = loginDao.get(login.getId());
		if (null == dblogin) {
			reqError.setValue(RequestTypes.ErrorCodes.ADDRESS_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.ADDRESS_DOES_NOT_EXIST;
			return reqError;
		}
		logger.debug(logprefix + " Found Login" + login.getId());
		reqError = loginDao.delete(dblogin);
		logger.debug(logprefix + " End");
		return reqError;
	}

	public Object verifylogin(Login login, ErrorCodes reqError) {

		Login dblogin = null;
		logger.debug("verifylogin Begin");
		try {
			if (null != login) {
				dblogin = (Login) loginDao.verifylogin(login.getEmail(),login.getPassword());

				if (dblogin.getType() == LoginType.type.CUSTOMER) {
					Customer retail = custServ.getCustomerByEmail(login.getEmail());
					return retail;
				}/* else if (dblogin.getType() == LoginType.type.FIRM) {
					Firm firm = firmServ.getFirmByUserName(login.getUsername());
					return firm;
				}
				else if (dblogin.getType() == LoginType.type.DELIVERY_PERSON) {
					DeliveryPool dp = dpServ.getDelivaryByUserName(login.getUsername());							
					return dp;
				}*/
			} else {
				logger.debug("Input is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  verifylogin " + e);
		}
		logger.debug("verifylogin End");
		return dblogin;
	}

	public Login validateUpdate(Login login, ErrorCodes reqError) {

		Login dblogin = null;
		logger.debug("update Begin");
		try {
			if (null != login) {
				dblogin = loginDao.getlogin(login.getEmail());
				if (null != dblogin) {
					logger.debug("Login does not exist");
					if (null != login.getPassword()) {
						dblogin.setPassword(login.getPassword());
					}
				}
			} else {
				logger.debug("Input is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  verifylogin " + e);
		}
		logger.debug("verifylogin End");
		return dblogin;
	}

	public ErrorCodes update(Login dblogin, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] update Begin");
		try {
			if (null != dblogin) {
				reqError = loginDao.update(dblogin);
			} else {
				logger.debug("Login is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  update " + e);
		}
		logger.debug("[" + reqType + "] update End");
		return reqError;
	}

	@Override
	public List<Login> getLoginType(LoginType.type stype) {
		logger.debug("getLoginType  : " + stype);
		try {
			List<Login> ltype = loginDao.getLoginType(stype);
			return ltype;

		} catch (Exception e) {
			logger.debug("getLoginType ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes getCheckNumberExist(String username, ErrorCodes reqError) {
		logger.debug("CheckIfUSERNAME Exist:number " + username + ":ErrorCode:" + reqError);
		Login login = loginDao.getlogin(username);
		if (login != null) {
			reqError.setValue(RequestTypes.ErrorCodes.USERNAME_ALREADY_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.USERNAME_ALREADY_EXIST;
			return reqError;
		}
		return reqError;
	}

	@Override
	public Login getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Login getlogin(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
