package com.locoupon.common.service;

import java.util.List;
import java.util.Set;

import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.CompObj;
import com.locoupon.common.model.DishDetailStats;
import com.locoupon.common.model.ReturnObject;


public interface MerchantOfferService {

	List<MerchantOffer> getAllProdStock();

	List<MerchantOffer> getAllProdStock(Integer index, Integer limit);
	
	List<MerchantOffer> getAllProdStock(Long product, Long firm);
	
	List<MerchantOffer> getAllProdStock(Long product);

	MerchantOffer getProdStockById(Long id);

	ErrorCodes validateAddProdStock(MerchantOffer dish, Request reqType);

	ErrorCodes deleteProdStock(MerchantOffer dbproduct, Request reqType);

	ErrorCodes updateProdStock(MerchantOffer dbproduct, Request reqType);

	MerchantOffer validateUpdateProdStock(MerchantOffer dish, Request reqType, ErrorCodes reqError);

	List<Long> getAllProdList(Long firm);

	MerchantOffer getProductStockByPname(Long firm,String pname, String variant);

	List<Long> getAllProdList();

	ErrorCodes addProdStock(MerchantOffer prodstk, Request reqType);

	List<MerchantOffer> getProdStockByMerchant(Long merchant);


	
	//ErrorCodes addProdStock(ProdStock dish, Request reqType);
	
	
	
}
