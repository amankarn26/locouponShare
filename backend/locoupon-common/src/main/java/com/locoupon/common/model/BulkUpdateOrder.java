package com.locoupon.common.model;

import java.util.List;

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.EnumBoolean.type;


public class BulkUpdateOrder {

	private List<Long> order;

	private EnumBoolean.type favorite;
	private StatusType.type status;
	private EnumBoolean.type isvendorpaid;
	private EnumBoolean.type isdeliverypaid;
	private String stats;

	public List<Long> getOrder() {
		return order;
	}

	public void setOrder(List<Long> order) {
		this.order = order;
	}

	public EnumBoolean.type getFavorite() {
		return favorite;
	}

	public void setFavorite(EnumBoolean.type favorite) {
		this.favorite = favorite;
	}

	public StatusType.type getStatus() {
		return status;
	}

	public void setStatus(StatusType.type status) {
		this.status = status;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public EnumBoolean.type getIsdeliverypaid() {
		return isdeliverypaid;
	}

	public void setIsdeliverypaid(EnumBoolean.type isdeliverypaid) {
		this.isdeliverypaid = isdeliverypaid;
	}

	public EnumBoolean.type getIsvendorpaid() {
		return isvendorpaid;
	}

	public void setIsvendorpaid(EnumBoolean.type isvendorpaid) {
		this.isvendorpaid = isvendorpaid;
	}
}
