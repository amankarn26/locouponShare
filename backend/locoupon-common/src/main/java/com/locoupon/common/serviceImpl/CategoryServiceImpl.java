package com.locoupon.common.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.CategoryDao;
import com.locoupon.common.entity.Category;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.CategoryService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.ProductService;
import com.locoupon.common.util.UtilFuncs;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryDao catDao;

	@Autowired
	private DocumentService docServ;
	
	@Autowired
	private ProductService dishServ;

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;

	public List<Category> getAllCategory() {

		// logger.debug("getAllCategory Start");
		List<Category> fList = new ArrayList<Category>();
		try {
			fList = catDao.getAllCategory();
		} catch (Exception e) {
			logger.error("Exception in getAllFirms " + e);
			return null;
		}
		return fList;
	}

	public ErrorCodes addCategory(Category cat, Request reqType) {

		// String logprefix = "["+reqType+"] addFirm ";
		// logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		String catname = UtilFuncs.getNormalizeName(cat.getName());
		cat.setName(catname.toUpperCase());
		Category dbfirm = catDao.checkIfCategoryExists(cat.getName(), reqError);
		if (null != dbfirm) {
			logger.debug("Duplicate Category");
			reqError.setValue(reqError.DUPLICATE_CATEGORY.toString());
			reqError = ErrorCodes.DUPLICATE_CATEGORY;
			return reqError;
		}
		
		// logger.debug(logprefix + " End");
		reqError = catDao.addCategory(cat);
		return reqError;
	}

	public ErrorCodes chekcIfCategoryExist(Category cat, ErrorCodes reqError) {

		Category dbfirm = catDao.checkIfCategoryExists(cat.getName(), reqError);
		if (null != dbfirm) {
			logger.debug("Duplicate Category");
			reqError.setValue(reqError.DUPLICATE_CATEGORY.toString());
			reqError = ErrorCodes.DUPLICATE_CATEGORY;
			return reqError;
		}

		return reqError;
	}

	public ErrorCodes deleteCategory(Category categ, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] deleteCategory Begin");

		try {
			reqError = catDao.deleteCategory(categ);
			logger.debug("[" + reqType + "] deleteCategory end");
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in deleteCategory " + e);
			return reqError.ERROR_EXCEPTION;
		}
	}

	@Override
	public Category getCategoryById(Long id) {
		// logger.debug("getCategoryById Start");
		try {
			Category cat = catDao.getCategoryById(id);
			return cat;
		} catch (Exception e) {
			logger.error("Exception in fetching getCategoryById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes addMultipleCategory(List<Category> catlist, Request reqType) {
		String logprefix = "[" + reqType + "] addMultipleCategory ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		for (Category cat : catlist) {
			reqError = this.addCategory(cat, reqType);
		}

		logger.debug(logprefix + " End");
		return reqError;
	}

	@Override
	public Category validateUpdateCategory(Category cat, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateCategory ";
		logger.debug(logprefix + " Begin");
		Category dbcat = catDao.getCategoryById(cat.getId());

		if (null == dbcat) {
			reqError.setValue(reqError.CATEGORY_DOES_NOT_EXIST.toString());
			return null;
		}

		if (null != cat.getName()) {
			String catname = UtilFuncs.getNormalizeName(cat.getName());
			dbcat.setName(catname.toUpperCase());

		}
		// updating photo
		if (null != cat.getPhoto() && null != cat.getPhoto().getDocname()
				&& !(cat.getPhoto().getDocname().trim().isEmpty())) {
			logger.debug(" Validate incoming photo ");
			try {
				reqError = docServ.validate(cat.getPhoto());
			} catch (Exception e) {
				logger.debug(" Exception in  doc validate", e);
				reqError = reqError.ERROR_EXCEPTION;
				return null;
			}

			logger.debug(" Check if photo exists with dbfirm");
			if (null == dbcat.getPhoto()) {
				logger.debug("Photo is being added first time" + cat.getPhoto().getDocname());

				try {
					Document doc = docServ.add(cat.getPhoto());
					dbcat.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}
			} else {
				logger.debug("Photo Exists; delete old and add new updateON date" + cat.getPhoto().getDocname());
				Document dbPhoto = dbcat.getPhoto();

				try {
					reqError = docServ.deleteDoc(dbPhoto);
				} catch (Exception e) {
					logger.debug(" Exception in  doc delete", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}

				logger.debug("Deleted old, added new" + cat.getPhoto().getDocname());
				try {
					Document doc = docServ.add(cat.getPhoto());
					dbcat.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}
			}
		}

		logger.debug(logprefix + " End");
		return dbcat;
	}

	@Override
	public ErrorCodes updateCategory(Category dbfirm, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateCategory Begin");
		try {
			if (null != dbfirm) {
				reqError = catDao.updateCategory(dbfirm);
			} else {
				logger.debug("updateCategory is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateCategory ", e);
		}
		logger.debug("[" + reqType + "] updateCategory End");
		return reqError;
	}

	@Override
	public Category checkCategoryInUsed(Long id, ErrorCodes reqError) {
		Category category = this.getCategoryById(id);
		List<ProdType> mtype =dishServ.getAllProdTypeByCategory(category.getName());
		if(mtype.size()>0)
		{
			reqError = RequestTypes.ErrorCodes.CATEGORY_IN_USED;
			category.setErrCode(reqError);
			
		}
		// TODO Auto-generated method stub
		return category;
	}

	@Override
	public List<Category> getAllCategory(Integer index, Integer limit) {

		List<Category> fList = new ArrayList<Category>();
		try {
			fList = catDao.getAllCategory(index,limit);
		} catch (Exception e) {
			logger.error("Exception in getAllCategory " + e);
			return null;
		}
		return fList;
	}

}
