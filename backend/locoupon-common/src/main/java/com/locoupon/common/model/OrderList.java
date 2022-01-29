package com.locoupon.common.model;

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.EnumBoolean.type;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class OrderList {

	private Long order;
	private Long vid;
	private String vendor;
	private Long did;
	private String dperson;
	private EnumBoolean.type isvendorpaid;
	private EnumBoolean.type isdeliverypaid;
	private StatusType.type orderstatus;
	private String orderdetails;
	private Double transferamt;
	private Double deliveryamt;
	private String status;
	private ErrorCodes error;

	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	public String getDperson() {
		return dperson;
	}

	public void setDperson(String dperson) {
		this.dperson = dperson;
	}

	public EnumBoolean.type getIsvendorpaid() {
		return isvendorpaid;
	}

	public void setIsvendorpaid(EnumBoolean.type isvendorpaid) {
		this.isvendorpaid = isvendorpaid;
	}

	public EnumBoolean.type getIsdeliverypaid() {
		return isdeliverypaid;
	}

	public void setIsdeliverypaid(EnumBoolean.type isdeliverypaid) {
		this.isdeliverypaid = isdeliverypaid;
	}

	public StatusType.type getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(StatusType.type orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorCodes getError() {
		return error;
	}

	public void setError(ErrorCodes error) {
		this.error = error;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(String orderdetails) {
		this.orderdetails = orderdetails;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Double getTransferamt() {
		return transferamt;
	}

	public void setTransferamt(Double transferamt) {
		this.transferamt = transferamt;
	}

	public Double getDeliveryamt() {
		return deliveryamt;
	}

	public void setDeliveryamt(Double deliveryamt) {
		this.deliveryamt = deliveryamt;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

}
