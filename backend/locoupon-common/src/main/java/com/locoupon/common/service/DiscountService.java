package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Discount;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.EnumBoolean.type;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.CustomerType;
import com.locoupon.common.model.DiscountModal;

@Service
public interface DiscountService {

	List<Discount> getAllValidDiscount();
	
	List<Discount> getAllValidDiscount(CustomerType.type type);

	ErrorCodes addDiscount(Discount discount, Request reqType);

	ErrorCodes deleteDiscount(Discount discount, Request reqType);

	Discount getDiscountById(Long id);

	ErrorCodes addMultipleDiscount(List<Discount> catlist, Request reqType);

	Discount validateUpdateDiscount(Discount discount, Request reqType, ErrorCodes reqError);

	ErrorCodes updateDiscount(Discount discount, Request reqType);

	boolean checkDiscountInUsed(Long id, ErrorCodes reqError);
	
	DiscountModal getDiscountForCouponcode(String Couponcode,Double cartval);

	List<Discount> getAllDiscount(); 
	
	List<Discount> getDiscountByType(CustomerType.type type);
	
	DiscountModal getDiscountForCouponcode(String Couponcode,Double cartval,CustomerType.type type);

}
