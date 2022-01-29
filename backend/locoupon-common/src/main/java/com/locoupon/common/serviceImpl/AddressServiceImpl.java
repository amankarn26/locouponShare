package com.locoupon.common.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.AddressDao;
import com.locoupon.common.entity.Address;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.AddressService;
import com.locoupon.common.util.UtilFuncs;

@Service
public class AddressServiceImpl implements AddressService {

	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private AddressDao addressDao;

	public ErrorCodes add(Address address, Request reqType) {
		String logprefix = "[" + reqType + "] add Address ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = addressDao.add(address);
		logger.debug(logprefix + " End");
		return reqError;
	}

	public Address find(Address address, Request reqType) {
		String logprefix = "[" + reqType + "] find Address ";
		logger.debug(logprefix + " Begin");
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;

		Address dbaddress = addressDao.find(address);
		logger.debug(logprefix + " End");
		return dbaddress;
	}

	public Address update(Long id, Address address) {
		String logprefix = "[" + id + "] update Address ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Address dbaddr = null;
		try {
			dbaddr = addressDao.getAddress(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dbaddr == null) {
			logger.debug(logprefix + " Failed, Address does not exist");
			return null;
		}
		dbaddr.setLine1(UtilFuncs.getNormalizeName(address.getLine1()));
		dbaddr.setLine2(UtilFuncs.getNormalizeName(address.getLine2()));
		dbaddr.setCity(address.getCity());
		dbaddr.setPincode(address.getPincode());
		dbaddr.setStates(address.getStates());
		dbaddr.setCountry(address.getCountry());
		dbaddr.setLatitude(address.getLatitude());
		dbaddr.setLongitude(address.getLongitude());

		reqError = addressDao.update(dbaddr);
		if (reqError != RequestTypes.ErrorCodes.NONE) {
			logger.debug(logprefix + " Failed");
			return null;
		}

		logger.debug(logprefix + " End");
		return dbaddr;
	}

	public ErrorCodes delAddr(Address address, Request reqType) {
		String logprefix = "[" + reqType + "] delete Address";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Address dbaddr = addressDao.getAddress(address.getId());
		if (null == dbaddr) {
			reqError.setValue(ErrorCodes.ADDRESS_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.ADDRESS_DOES_NOT_EXIST;
			return reqError;
		}
		logger.debug(logprefix + " Found Address" + dbaddr.getLine1());
		reqError = addressDao.delete(dbaddr);
		logger.debug(logprefix + " End");
		return reqError;
	}

}
