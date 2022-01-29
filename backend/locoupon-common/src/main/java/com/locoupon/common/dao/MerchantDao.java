package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Merchant;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public interface MerchantDao {

	List<Merchant> getAllMerchant();

	Merchant getMerchantById(Long id);

	Merchant checkIfMerchantExists(Long id, String username,
			ErrorCodes reqError);

	ErrorCodes addMerchant(Merchant cust);

	ErrorCodes deleteMerchant(Merchant dbcust);

	ErrorCodes updateMerchant(Merchant dbcust);

	Merchant getMerchantByEmail(String username);
	
}