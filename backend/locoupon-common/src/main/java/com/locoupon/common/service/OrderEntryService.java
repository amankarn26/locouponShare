package com.locoupon.common.service;

import java.util.List;

import com.locoupon.common.entity.OrderEntry;
import com.locoupon.common.model.CompObj;

public interface OrderEntryService {

	List<CompObj> getAllPopularProduct(Long firm);

	List<CompObj> getAllPopularProduct();

	List<OrderEntry> getOrderEntryByOrder(Long quote);

	List<CompObj> getAllFrequentOrder(Long firm);

	List<CompObj> getAllFrequentOrderForCustomer(Long customer);

	List<CompObj> getAllPopularProduct(Integer index, Integer limit);

	List<CompObj> getAllPopularProductByLimt(Long firm, Integer index, Integer limit);

	List<CompObj> getAllPopularProduct(Long firm, String firmname);

	OrderEntry addOrderEntry(OrderEntry detail);

	List<OrderEntry> getOrderEntryByProduct(String dish);

	List<CompObj> getAllFrequentOrder(Long firm, String doctype);

	List<CompObj> getAllFrequentOrderForCustomer(Long customer, String doctype);
	
}
