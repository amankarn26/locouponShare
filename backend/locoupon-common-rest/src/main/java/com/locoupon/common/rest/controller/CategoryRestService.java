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

import com.locoupon.common.entity.Category;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.service.CategoryService;
import com.locoupon.common.util.UtilFuncs;

@RestController
@RequestMapping(value = "/category", produces = "application/json")
public class CategoryRestService {
	//	private static Logger logger  = Logger.getLogger(Controller.class);log4j
	private static final Logger logger = LoggerFactory.getLogger(CategoryRestService.class);

	@Autowired
	private CategoryService categServ;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getAllCategory(@ModelAttribute("index") String sindex,@ModelAttribute("limit") String slimit) {



		List<Category> aList = new ArrayList<Category>();
		try {
			if(sindex.trim().isEmpty() && slimit.trim().isEmpty())
			{
				logger.debug("getAllCategory");
				aList = categServ.getAllCategory();
			}else
			{
				logger.debug("getAllCategory by limit");
				Integer index = UtilFuncs.getIntFromStr(sindex);
				Integer limit = UtilFuncs.getIntFromStr(slimit);
				aList = categServ.getAllCategory(index,limit);
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllCategory ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getCategoryById(@PathVariable(value = "param") Long id) {

		logger.debug("getCategoryById: " + id);
		if (id < 0) {
			logger.error("getCategoryById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(categServ.getCategoryById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getCategoryById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestBody Category categ) {
		logger.debug("addCategory Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;
		try {
			reqError = categServ.addCategory(categ, reqType);
			if(reqError!=RequestTypes.ErrorCodes.NONE)
			{
				logger.debug("addCategory End");
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}
			logger.debug("addCategory End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addCategory " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE ACCOUNT\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleCategory(@RequestBody List<Category> actlist) {

		logger.debug("addMultipleCategory data -" + actlist.size());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;
		try {
			reqError = categServ.addMultipleCategory(actlist, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("Error in add multipleaccount");
				return new ResponseEntity(actlist, HttpStatus.OK);
			}
			return new ResponseEntity(actlist, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addMultipleCategory " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCategory(@PathVariable(value = "param") Long id) {

		logger.debug("deleteCategory Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Category dbcateg = null;

		try {
			dbcateg = categServ.checkCategoryInUsed(id,reqError);
			if (dbcateg.getErrCode()==RequestTypes.ErrorCodes.CATEGORY_IN_USED) {
				logger.debug("deleteCategory fail");
				return new ResponseEntity(dbcateg.getErrCode(), HttpStatus.BAD_REQUEST);
			}
			reqError = categServ.deleteCategory(dbcateg, reqType);
			logger.debug("deleteCategory End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.debug("Exception in deleteCategory End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateCategory(@RequestBody Category categ) {

		logger.debug("updateCategory Begin ");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;
		Category dbcateg = null;
		try {
			dbcateg = categServ.validateUpdateCategory(categ, reqType, reqError);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("updateCategory End");
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = categServ.updateCategory(dbcateg, reqType);
			logger.debug("updateCategory End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateCategory " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

}