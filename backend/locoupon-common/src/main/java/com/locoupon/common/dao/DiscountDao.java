package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Discount;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.EnumBoolean.type;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.CustomerType;


public interface DiscountDao {

	List<Discount> getAllValidDiscount();

	Discount getDiscountById(Long id);

	ErrorCodes addDiscount(Discount discount);

	ErrorCodes deleteDiscount(Discount discount);

	Discount checkIfDiscountExists(String name, ErrorCodes reqError);

	ErrorCodes updateDiscount(Discount discount);

	boolean checkDiscountInUsed(Long id, ErrorCodes reqError);

	Discount getDiscountByCouponCode(String couponcode);

	List<Discount> getAllDiscount();

	List<Discount> getDiscountByType(com.locoupon.common.model.CustomerType.type type);

	Discount getDiscountByCouponCode(String couponcode, com.locoupon.common.model.CustomerType.type type);

	List<Discount> getAllValidDiscount(CustomerType.type type);
	
}
