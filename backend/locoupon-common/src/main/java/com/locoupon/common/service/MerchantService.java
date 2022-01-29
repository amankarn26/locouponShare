package com.locoupon.common.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Merchant;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface MerchantService {

	List<Merchant> getAllMerchant();

	Merchant getMerchantById(Long id);

	ErrorCodes validateAddMerchant(Merchant cust, Request reqType);

	ErrorCodes addMerchant(Merchant cust, Request reqType);

	ErrorCodes deleteMerchant(Merchant dbcust, Request reqType);

	Merchant validateUpdateMerchant(Merchant cust, Request reqType, ErrorCodes reqError);

	ErrorCodes updateMerchant(Merchant dbcust, Request reqType);

	Merchant checkIfMerchantExists(Merchant cust, ErrorCodes reqError);
	
	Merchant getMerchantByEmail(String email);

	ErrorCodes updateEWallet(Long Merchant, QCost qcost);

	ErrorCodes addFilesToMerchant(Merchant dbproduct, Set<Document> photos, Request reqType);

	ErrorCodes deleteMultipleMerchantFiles(Merchant dbproduct, List<Long> ids, Request reqType);

}
