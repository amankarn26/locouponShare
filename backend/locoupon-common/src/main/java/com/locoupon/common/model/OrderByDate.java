package com.locoupon.common.model;

import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.StatusType.type;

public class OrderByDate {

	private Long id;
	private String orderno;
	private StatusType.type status;

	public StatusType.type getStatus() {
		return status;
	}

	public void setStatus(StatusType.type status) {
		this.status = status;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
