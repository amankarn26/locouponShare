package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.EWallet;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


public interface EWalletDao {

	ErrorCodes add(EWallet qpr);

	EWallet get(Long id);

	ErrorCodes update(EWallet dbobj);

	ErrorCodes delete(EWallet dbobj);

	List<EWallet> getAll();

}
