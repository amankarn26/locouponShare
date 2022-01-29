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

import com.locoupon.common.entity.Employee;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.EmployeeService;

@RestController
@RequestMapping(value = "/employee", produces = "application/json")
public class EmployeeRestService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeRestService.class);

	@Autowired
	private EmployeeService custServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Employee> getAllEmployee(@ModelAttribute("city") String city,
			@ModelAttribute("pincode") String pincode) {

		// logger.debug("getAllEmployee");

		List<Employee> aList = new ArrayList<Employee>();
		try {

			if (city.trim().isEmpty() && pincode.trim().isEmpty()) {
				aList = custServ.getAllEmployee();
			}
		}

		catch (Exception e) {
			logger.error(" Exception in getAllEmployee ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getEmployeeById(@PathVariable(value = "param") Long id) {

		// logger.debug("getEmployeeById: " + id);
		if (null == id) {
			logger.error("getEmployeeById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(custServ.getEmployeeById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getEmployeeById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee cust) {
		// logger.debug("addEmployee Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {

			reqError = custServ.validateAddEmployee(cust, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("validateAddEmployee End:" + reqError.toString());
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}

			reqError = custServ.addEmployee(cust, reqType);
			ReturnObject obj = new ReturnObject();
			obj.setId(cust.getId());
			return new ResponseEntity(cust, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addEmployee " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE Employee\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteEmployee(@PathVariable(value = "param") Long id) {

		// logger.debug("deleteEmployee Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Employee dbcust = null;
		try {
			Employee cust = custServ.getEmployeeById(id);
			/*reqError = custServ.checkIfEmployeeIsINUse(cust, reqError);
			if(reqError!=RequestTypes.ErrorCodes.NONE)
			{
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}*/
			reqError = custServ.deleteEmployee(cust, reqType);
			logger.debug("deleteEmployee End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception in deleteEmployee End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateEmployee(@RequestBody Employee cust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		try {

			Employee dbcust = custServ.validateUpdateEmployee(cust, reqType, reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {

				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = custServ.updateEmployee(dbcust, reqType);
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateEmployee " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}


}