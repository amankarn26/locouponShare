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
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.BulkUpdateCust;
import com.locoupon.common.model.OneToManyRequest;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.CustomerService;

@RestController
@RequestMapping(value = "/customer", produces = "application/json")
public class CustomerRestService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerRestService.class);

	@Autowired
	private CustomerService custServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Customer> getAllCustomer(@ModelAttribute("city") String city,
			@ModelAttribute("pincode") String pincode) {

		// logger.debug("getAllCustomer");

		List<Customer> aList = new ArrayList<Customer>();
		try {

			if (city.trim().isEmpty() && pincode.trim().isEmpty()) {
				aList = custServ.getAllCustomer();
			} else if (!pincode.trim().isEmpty()) {
				logger.debug("pincode" + pincode);
				aList = custServ.getAllCustomerByPincode(pincode);
			} else {
				aList = custServ.getAllCustomer(city);
			}
		}

		catch (Exception e) {
			logger.error(" Exception in getAllCustomer ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getCustomerById(@PathVariable(value = "param") Long id) {

		// logger.debug("getCustomerById: " + id);
		if (null == id) {
			logger.error("getCustomerById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(custServ.getCustomerById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getCustomerById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/Name/{param}", method = RequestMethod.GET)
	public ResponseEntity getCustomerByName(@PathVariable(value = "param") String name) {

		// logger.debug("getCustomerByName: " + name);
		if (null == name) {
			logger.error("getCustomerByName id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(custServ.getCustomerByName(name), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getCustomerByName: " + name, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer cust) {
		// logger.debug("addCustomer Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {

			reqError = custServ.validateAddCustomer(cust, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("validateAddCustomer End:" + reqError.toString());
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}

			reqError = custServ.addCustomer(cust, reqType);
			ReturnObject obj = new ReturnObject();
			obj.setId(cust.getId());
			return new ResponseEntity(cust, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addCustomer " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE CUSTOMER\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/bulk/rm", method = RequestMethod.POST)
	public ResponseEntity setBulkRelationManager(@RequestBody BulkUpdateCust bulkupcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;
		try {

			for (Long cust : bulkupcust.getCustomers()) {
				Customer dbcust = custServ.getCustomerById(cust);
			}

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				return new ResponseEntity(reqError, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.debug("Exception in setBulkRelationManager", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCustomer(@PathVariable(value = "param") Long id) {

		// logger.debug("deleteCustomer Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Customer dbcust = null;
		try {
			Customer cust = custServ.getCustomerById(id);
			reqError = custServ.checkIfCustomerIsINUse(cust, reqError);
			if(reqError!=RequestTypes.ErrorCodes.NONE)
			{
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}
			reqError = custServ.deleteCustomer(cust, reqType);
			logger.debug("deleteCustomer End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception in deleteCustomer End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateCustomer(@RequestBody Customer cust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		try {

			Customer dbcust = custServ.validateUpdateCustomer(cust, reqType, reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {

				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = custServ.updateCustomer(dbcust, reqType);
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateCustomer " + e);
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
		logger.debug("addFilesToCustomer Begin");
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		Customer dbcust = null;
		try {
			dbcust = custServ.getCustomerById(customer.getId());
			if (null == dbcust) {
				reqError = RequestTypes.ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
				return new ResponseEntity(reqError, HttpStatus.OK);
			}

			reqError = custServ.addFilesToCustomer(dbcust, customer.getFiles(), reqType);
			logger.debug("addFilesToCustomer End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addFilesToCustomer: " + e);
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

		Customer dbcust = null;

		try {

			dbcust = custServ.getCustomerById(files.getContainer());
			if (null == dbcust) {
				reqError = RequestTypes.ErrorCodes.CUSTOMER_DOES_NOT_EXIST;
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = custServ.deleteMultipleCustomerFiles(dbcust, files.getIds(), reqType);
			logger.debug("deleteMultipleCustomerFiles End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteMultipleCustomerFiles End ", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}
}