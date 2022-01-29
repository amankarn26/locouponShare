package com.locoupon.common.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.entity.Login;
import com.locoupon.common.enums.LoginType;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.LoginService;

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginRestService {

	private static final Logger logger = LoggerFactory.getLogger(LoginRestService.class);

	@Autowired
	private LoginService loginServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getLoginType(@ModelAttribute("type") String stype,
			@ModelAttribute("username") String username) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("getLoginType");

		if (stype.trim().isEmpty()) {
			ReturnObject obj = new ReturnObject();
			logger.debug("Check UserName Exist:");
			reqError = loginServ.getCheckNumberExist(username, reqError);
			obj.setError(reqError);
			return new ResponseEntity(obj, HttpStatus.OK);
		} else {
			try {
				LoginType.type type = null;
				type = LoginType.type.valueOf(stype);
				return new ResponseEntity(loginServ.getLoginType(type), HttpStatus.OK);

			} catch (Exception e) {
				logger.error("Exception in getLoginType: " + stype, e);
				return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);

			}
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity verifyLogin(@RequestBody Login login) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("verifyLogin Begin");
		Object dbObject = null;
		try {

			dbObject = loginServ.verifylogin(login, reqError);
			if (null == dbObject) {
				logger.debug("verifyLogin End");
				return new ResponseEntity("Invalid Email or Password", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(dbObject, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in verifyLogin " + e);
			return new ResponseEntity("Invalid Email or Password", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateLoginPassword(@RequestBody Login login) {

		logger.debug("updateLoginPassword Begin ");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		Login dblogin = null;
		try {
			dblogin = loginServ.validateUpdate(login, reqError);
			if (null == dblogin || reqError != RequestTypes.ErrorCodes.NONE) {
				logger.error("updateLoginPassword End");
				return new ResponseEntity(reqError.LOGIN_DOES_NOT_EXIST.toString(), HttpStatus.OK);
			}

			reqError = loginServ.update(dblogin, reqType);
			logger.debug("updateCustomer End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in updateLoginPassword " + e);
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

}