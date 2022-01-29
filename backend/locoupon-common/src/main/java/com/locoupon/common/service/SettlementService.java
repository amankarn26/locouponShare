package com.locoupon.common.service;

import java.util.Date;
import java.util.List;

import com.locoupon.common.entity.OrderEntry;
import com.locoupon.common.enums.Duration;
import com.locoupon.common.model.BookingMonthCount;
import com.locoupon.common.model.BookingStats;
import com.locoupon.common.model.CompObj;
import com.locoupon.common.model.ProductOrderStats;
import com.locoupon.common.model.Settlement;
import com.locoupon.common.model.TopProducts;
import com.locoupon.common.model.TopVendors;

public interface SettlementService {


	Settlement getSettled(Long firm);
	
	Settlement getSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getSettled(Long firm,Duration.type dtype );

	Settlement getNotSettled(Long firm);

	Settlement getNotSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getNotSettled(Long firm,Duration.type dtype );
	
	Settlement getDPSettled(Long firm);
	
	Settlement getDPSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getDPSettled(Long firm,Duration.type dtype );

	Settlement getDPNotSettled(Long firm);

	Settlement getDPNotSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getDPNotSettled(Long firm,Duration.type dtype );

	Settlement getOnlineSettled(Long firm);
	
	Settlement getOnlineSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getOnlineSettled(Long firm,Duration.type dtype );

	Settlement getOnlineNotSettled(Long firm);

	Settlement getOnlineNotSettled(Long firm,Date fromdate,Date todate);
	
	Settlement getOnlineNotSettled(Long firm,Duration.type dtype );
	
}
