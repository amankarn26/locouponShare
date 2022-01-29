package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.EWallet;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
@Service
public interface EWalletService {

	ErrorCodes add(EWallet qpr, Request reqType);

	EWallet update(Long id, EWallet qpr);

	ErrorCodes delete(EWallet qcost, Request reqType);

	List<EWallet> getAll();

	EWallet get(Long id);

	ErrorCodes addBookingAmtToEWallet(EWallet ewallet, QCost qcost);

	ErrorCodes updateWallet(EWallet ewallet, QCost qcost);

}
