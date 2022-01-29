package com.locoupon.common.service;

import java.util.List;

import com.locoupon.common.entity.OrderEntry;
import com.locoupon.common.model.BookingMonthCount;
import com.locoupon.common.model.BookingStats;
import com.locoupon.common.model.CompObj;
import com.locoupon.common.model.ProductOrderStats;
import com.locoupon.common.model.TopProducts;
import com.locoupon.common.model.TopVendors;

public interface StatsService {

	BookingStats getOrdersStatsForVendor(Long firm);

	ProductOrderStats getTopRevenuesStats();

	ProductOrderStats getTopRevenuesStatsForDpool(Long dperson);

	ProductOrderStats getTopRevenuesStatsForVendor(Long firm);

	List<TopProducts> getProductOrderSaleStats();

	List<TopVendors> getProductOrderSaleStats(Long firm);

	List<BookingMonthCount> getMonthlyOrderCount();

	List<BookingMonthCount> getMonthlyOrderCountForDPool(Long dperson);

	List<BookingMonthCount> getMonthlyOrderCountForVendor(Long firm);

	BookingStats getOrdersStats();

	BookingStats getOrdersStatsForDpool(Long dperson);

	
	
}
