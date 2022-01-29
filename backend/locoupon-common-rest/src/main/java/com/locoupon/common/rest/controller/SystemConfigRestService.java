package com.locoupon.common.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.entity.SystemConfig;
import com.locoupon.common.enums.RequestState;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.service.SystemConfigService;
import com.locoupon.common.util.UtilFuncs;

@RestController
@RequestMapping(value = "/system", produces = "application/json")
public class SystemConfigRestService {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigRestService.class);

	@Autowired
	private SystemConfigService sysconfServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getAllSystemConfig(@ModelAttribute("firm") String sfirm) {
		logger.debug("getAllSystemConfigLabour");

		Long firm = UtilFuncs.getLongfromStr(sfirm);

		List<SystemConfig> fList = new ArrayList<SystemConfig>();
		try {
			fList = sysconfServ.getAllSystemConfig(firm);

		} catch (Exception e) {
			logger.error(" Exception in getAllSystemConfigLabour ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(fList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getSystemConfigById(@PathVariable(value = "param") Long id) {

		logger.debug("getSystemConfigById: " + id);
		if (null == id) {
			logger.error("getSystemConfigById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.BAD_REQUEST);
		}
		try {
			return new ResponseEntity(sysconfServ.getSystemConfigById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getSystemConfigById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity addSystemConfig(@RequestBody SystemConfig role) {

		logger.debug("addSystemConfig Begin");

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {
			reqError = sysconfServ.addSystemConfig(role, reqType);
			logger.debug("addSystemConfig End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addSystemConfig " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity getSystemConfigdelete(@PathVariable(value = "param") Long id) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;
		logger.debug("getSystemConfigById: " + id);
		if (null == id) {
			logger.error("getSystemConfigById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.BAD_REQUEST);
		}
		SystemConfig dbsupp = null;
		if (null != id) {
			logger.debug("getSystemConfigById: " + id);
			try {
				dbsupp = sysconfServ.getSystemConfigById(id);
				reqError = sysconfServ.deleteSystemConfig(dbsupp, reqType);
				logger.debug("deleteSystemConfig End");
				return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
			} catch (Exception e) {
				logger.debug("Exception in deleteSystemConfig End", e);
				return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

	}

	@RequestMapping(value = "/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleStock(@RequestBody List<SystemConfig> slist) {

		logger.debug("Add Multiple System configuration  data -" + slist.size());
		RequestTypes.Request reqType = RequestTypes.Request.ADD;
		String prefix = "[" + reqType + "] Add Multiple System configuration  ";
		logger.debug(prefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		for (SystemConfig prodstock : slist) {

			prodstock.setReqstatus(RequestState.state.SUCCESS.toString());
			prodstock.setErrCode(reqError);
			logger.debug(prefix + " Adding " + prodstock.toString());

			try {
				reqError = sysconfServ.addSystemConfig(prodstock, reqType);
				if (reqError != RequestTypes.ErrorCodes.NONE) {
					logger.debug("Error in add System config");
					prodstock.setReqstatus(RequestState.state.FAILED.toString());
					prodstock.setErrCode(reqError);
					return new ResponseEntity(slist, HttpStatus.OK);

				}
			} catch (Exception e) {
				logger.debug("Exception in add multiple System config " + e);
				prodstock.setReqstatus(RequestState.state.FAILED.toString());
				prodstock.setErrCode(reqError);
				continue;
			}

		}
		return new ResponseEntity(slist, HttpStatus.OK);
	}

}