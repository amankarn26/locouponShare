package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class OrderUpdate {

	private Long id;
	private String firm;
	private List<OrderList> ordelist = new ArrayList<OrderList>();
	private ErrorCodes errString;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public List<OrderList> getOrdelist() {
		return ordelist;
	}

	public void setOrdelist(List<OrderList> ordelist) {
		this.ordelist = ordelist;
	}

	public ErrorCodes getErrString() {
		return errString;
	}

	public void setErrString(ErrorCodes errString) {
		this.errString = errString;
	}

}
