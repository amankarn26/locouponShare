package com.locoupon.common.dao;

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
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.StatusType.type;


public interface ReviewDao {

	List<ProductReview> getAllReview();

	ProductReview getReviewById(Long id);

	ErrorCodes addReview(ProductReview prodrl);

	ProductReview checkIfReviewExists(Long id, ErrorCodes reqError);

	ErrorCodes deleteReview(ProductReview dbpentry);

	ErrorCodes updateReview(ProductReview dbprodrl);

	List<ProductReview> getReviewForDish(Long product);

	List<ProductReview> getReviewForCustomer(Long cust);

	ErrorCodes checkIfReviewsExceeded(Long dish, Long addedby, Long orderid);

	List<ProductReview> getReviewForOrder(Long order);

	List<ProductReview> getReviewForDish(Long product, ReviewStatus.type status);

	List<ProductReview> getReviewByStatus(ReviewStatus.type status);
	
}