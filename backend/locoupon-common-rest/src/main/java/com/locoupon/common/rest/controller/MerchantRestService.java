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

import com.locoupon.common.entity.Customer;
import com.locoupon.common.entity.Merchant;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.BulkUpdateCust;
import com.locoupon.common.model.OneToManyRequest;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.MerchantService;

@RestController
@RequestMapping(value = "/merchant", produces = "application/json")
public class MerchantRestService {

	private static final Logger logger = LoggerFactory.getLogger(MerchantRestService.class);

	@Autowired
	private MerchantService custServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Merchant> getAllMerchant(@ModelAttribute("city") String city,
			@ModelAttribute("pincode") String pincode) {

		// logger.debug("getAllMerchant");

		List<Merchant> aList = new ArrayList<Merchant>();
		try {

			if (city.trim().isEmpty() && pincode.trim().isEmpty()) {
				aList = custServ.getAllMerchant();
			}
		}

		catch (Exception e) {
			logger.error(" Exception in getAllMerchant ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getMerchantById(@PathVariable(value = "param") Long id) {

		// logger.debug("getMerchantById: " + id);
		if (null == id) {
			logger.error("getMerchantById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(custServ.getMerchantById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getMerchantById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Merchant> addMerchant(@RequestBody Merchant cust) {
		// logger.debug("addMerchant Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {

			reqError = custServ.validateAddMerchant(cust, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("validateAddMerchant End:" + reqError.toString());
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}

			reqError = custServ.addMerchant(cust, reqType);
			ReturnObject obj = new ReturnObject();
			obj.setId(cust.getId());
			return new ResponseEntity(cust, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addMerchant " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE Merchant\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteMerchant(@PathVariable(value = "param") Long id) {

		// logger.debug("deleteMerchant Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Merchant dbcust = null;
		try {
			Merchant cust = custServ.getMerchantById(id);
			/*reqError = custServ.checkIfMerchantIsINUse(cust, reqError);
			if(reqError!=RequestTypes.ErrorCodes.NONE)
			{
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}*/
			reqError = custServ.deleteMerchant(cust, reqType);
			logger.debug("deleteMerchant End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception in deleteMerchant End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateMerchant(@RequestBody Merchant cust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		try {

			Merchant dbcust = custServ.validateUpdateMerchant(cust, reqType, reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {

				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = custServ.updateMerchant(dbcust, reqType);
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateMerchant " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/docs", method = RequestMethod.POST)
	public ResponseEntity addFilesToCustomer(@RequestBody Customer customer) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		if (customer.getFiles().size() <= 0) {
			return new ResponseEntity(reqError.REQUIRED_INPUT_NULL, HttpStatus.OK);
		}
		logger.debug("addFilesToMerchant Begin");
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		Merchant dbcust = null;
		try {
			dbcust = custServ.getMerchantById(customer.getId());
			if (null == dbcust) {
				reqError = RequestTypes.ErrorCodes.MERCHANT_DOES_NOT_EXIST;
				return new ResponseEntity(reqError, HttpStatus.OK);
			}

			reqError = custServ.addFilesToMerchant(dbcust, customer.getFiles(), reqType);
			logger.debug("addFilesToMerchant End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addFilesToMerchant: " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE \"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/docs/deleteM", method = RequestMethod.POST)
	public ResponseEntity deleteMultiplePanPhotos(@RequestBody OneToManyRequest files) {

		//logger.debug("deleteMultiplePanPhotos Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Merchant dbcust = null;

		try {

			dbcust = custServ.getMerchantById(files.getContainer());
			if (null == dbcust) {
				reqError = RequestTypes.ErrorCodes.MERCHANT_DOES_NOT_EXIST;
				return new ResponseEntity(reqError, HttpStatus.OK);
			}

			reqError = custServ.deleteMultipleMerchantFiles(dbcust, files.getIds(), reqType);
			logger.debug("deleteMultipleMerchantFiles End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteMultipleMerchantFiles End ", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}


}