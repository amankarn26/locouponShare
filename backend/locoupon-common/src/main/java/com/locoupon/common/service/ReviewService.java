package com.locoupon.common.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.ProductReview;
import com.locoupon.common.enums.ApplicationConstants;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.ReviewStatus;
import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


public interface ReviewService {

	List<ProductReview> getAllReview();

	ProductReview getReviewById(Long id);

	ErrorCodes addReview(ProductReview prodrl);

	ProductReview checkIfReviewExists(Long id, ErrorCodes reqError);

	ErrorCodes deleteReview(ProductReview dbpentry);

	ProductReview validateUpdate(ProductReview dishreview,RequestTypes.Request reqType,ErrorCodes reqError);
	
	ErrorCodes updateReview(ProductReview dbprodrl);

	List<ProductReview> getReviewForDish(Long product);
	
	List<ProductReview> getReviewForDish(Long product,ReviewStatus.type status);
	
	List<ProductReview> getReviewForCustomer(Long cust);
	
	List<ProductReview> getReviewForOrder(Long order);
	
	List<ProductReview> getReviewByStatus(ReviewStatus.type status);
	
}