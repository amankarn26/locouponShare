package com.locoupon.common.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.ProductDao;
import com.locoupon.common.dao.ReviewDao;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.ProductReview;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.enums.ReviewStatus;
import com.locoupon.common.service.ReviewService;
import com.locoupon.common.util.UtilFuncs;


@Service
public class ReviewServiceImpl implements ReviewService {

	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ProductDao productDao;

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;

	@Override
	public List<ProductReview> getAllReview() {
		logger.debug("getAllReview Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			List<ProductReview> preview = reviewDao.getAllReview();
			for(ProductReview review: preview)
			{
				Product prod=productDao.getProductById(review.getProduct());
				review.setPname(prod.getName());
				logger.debug("REM: get pname:"+review.getPname());
				aList.add(review);
			}
		} catch (Exception e) {
			logger.error("Exception in getAllReview " + e);
			return null;
		}
		return aList;
	}

	@Override
	public ProductReview getReviewById(Long id) {
		logger.debug("getReviewById Start");
		try {
			ProductReview product = reviewDao.getReviewById(id);
			return product;
		} catch (Exception e) {
			logger.error("Exception in fetching getReviewById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes addReview(ProductReview dishreview) {
		String logprefix = " addReview ";
		logger.debug("addReview Start");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		if (null == dishreview.getAddedby() ) {
			logger.debug(logprefix + "Failed Name is Null");
			reqError.setValue(ErrorCodes.CUSTOMER_IS_NULL.toString());
			reqError = ErrorCodes.CUSTOMER_IS_NULL;
			return reqError;
		}

		if (null == dishreview.getProduct() ) {
			logger.debug(logprefix + "Failed HSN Code is Null");
			reqError.setValue(ErrorCodes.PRODUCT_IS_NULL.toString());
			reqError = ErrorCodes.PRODUCT_IS_NULL;
			return reqError;
		}
		reqError = reviewDao.checkIfReviewsExceeded(dishreview.getProduct(), dishreview.getAddedby(),dishreview.getOrderid());
		if(reqError == ErrorCodes.REVIEW_LIMIT_EXCEEDED)
		{
			logger.debug(logprefix + "Failed HSN Code is Null");
			reqError.setValue(ErrorCodes.REVIEW_LIMIT_EXCEEDED.toString());
			reqError = ErrorCodes.REVIEW_LIMIT_EXCEEDED;
			return reqError;
		}
		
		Timestamp currentdate = UtilFuncs.getCurrentTime();
		dishreview.setAddedon(currentdate);
		dishreview.setAddedbyname(UtilFuncs.convertToCamelCase(dishreview.getAddedbyname()));
		reviewDao.addReview(dishreview);
		logger.debug("addReview end");
		return reqError;
	}

	@Override
	public ProductReview checkIfReviewExists(Long id, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorCodes deleteReview(ProductReview dbpentry) {
	//	String logprefix = "[" + reqType + "] delete MenuType ";
		logger.debug(" Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = reviewDao.deleteReview(dbpentry);
		logger.debug(" End");
		return reqError;
	}

	@Override
	public ErrorCodes updateReview(ProductReview dbprodrl) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		//logger.debug("[" + reqType + "] updateReview Begin");
		try {
			if (null != dbprodrl) {
				reqError = reviewDao.updateReview(dbprodrl);
			} else {
				logger.debug("dbreview is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateReview ", e);
		}
		logger.debug("updateReview End");
		return reqError;
	}

	@Override
	public List<ProductReview> getReviewForDish(Long product) {
		logger.debug("getReviewForDish Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			List<ProductReview> preview = reviewDao.getReviewForDish(product);
			for(ProductReview review: preview)
			{
				Product prod=productDao.getProductById(review.getProduct());
				review.setPname(prod.getName());
				aList.add(review);
				
			}
		} catch (Exception e) {
			logger.error("Exception in getReviewForDish " + e);
			return null;
		}
		return aList;
	}

	@Override
	public ProductReview validateUpdate(ProductReview dishreview, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateReview ";
		logger.debug(logprefix + " Begin");

		ProductReview dbdish = reviewDao.getReviewById(dishreview.getId());
		if (null == dbdish) {
			reqError.setValue(reqError.PRODUCT_REVIEW_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODUCT_REVIEW_DOES_NOT_EXIST;
			return null;
		}


		if (null != dishreview.getStatus() && !(dishreview.getStatus().toString().trim().isEmpty())) {
			dbdish.setStatus(dishreview.getStatus());
		}

		
	
		logger.debug(logprefix + " End");
		return dbdish;
	}

	@Override
	public List<ProductReview> getReviewForCustomer(Long cust) {
		logger.debug("getReviewForCustomer Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			aList = reviewDao.getReviewForCustomer(cust);
		} catch (Exception e) {
			logger.error("Exception in getReviewForCustomer " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<ProductReview> getReviewForOrder(Long order) {
		logger.debug("getReviewForOrder Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			List<ProductReview> product = reviewDao.getReviewForOrder(order);
			for(ProductReview review: product)
			{
				Product prod=productDao.getProductById(review.getProduct());
				review.setPname(prod.getName());
				aList.add(review);
			}
			return aList;
		} catch (Exception e) {
			logger.error("Exception in fetching getReviewForOrder ", e);
			return null;
		}
	}

	@Override
	public List<ProductReview> getReviewForDish(Long product, ReviewStatus.type status) {
		logger.debug("getReviewForProduct Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			aList = reviewDao.getReviewForDish(product,status);
		} catch (Exception e) {
			logger.error("Exception in getReviewForProduct " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<ProductReview> getReviewByStatus(ReviewStatus.type status) {
		logger.debug("getReviewByStatus Start");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			List<ProductReview> preview = reviewDao.getReviewByStatus(status);
			for(ProductReview review: preview)
			{
				Product prod=productDao.getProductById(review.getProduct());
				review.setPname(prod.getName());
				aList.add(review);
			}
		} catch (Exception e) {
			logger.error("Exception in getReviewByStatus " + e);
			return null;
		}
		return aList;
	}
	
	
}
