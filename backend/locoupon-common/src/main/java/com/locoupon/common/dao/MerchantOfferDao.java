package com.locoupon.common.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;



@Transactional
@Service
public interface MerchantOfferDao {

	List<MerchantOffer> getAllProdStock();

	List<MerchantOffer> getAllProdStock(Integer index, Integer limit);

	MerchantOffer getProdStockById(Long id);

	ErrorCodes deleteProdStock(MerchantOffer dbproduct, Request reqType);

	ErrorCodes updateProdStock(MerchantOffer dbproduct);

	ErrorCodes addProdStock(MerchantOffer dish);

	List<MerchantOffer> getAllProdStock(Long product, Long firm);

	List<MerchantOffer> getAllProdStock(Long product);

	MerchantOffer checkIfProdStockExists(Long firm, String variant, String pname);

	List<Long> getAllProdList(Long firm);

	MerchantOffer getProductStockByPname(Long firm, String pname, String variant);

	List<Long> getAllProdList();

	MerchantOffer checkIfProdStockExists(Long merchant, Long product);

	List<MerchantOffer> getProdStockByMerchant(Long merchant);
	
	
	
	
	
}