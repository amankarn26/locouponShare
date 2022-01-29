package com.locoupon.common.model;

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.EnumBoolean.type;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class DCharge {
	private EnumBoolean.type personfound;
	private EnumBoolean.type distancefound;
	private String status;
	private Long deliverypool;
	private String dperson;
	private String number;
	private String distance;
	private Double charge;
	private ErrorCodes error;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDeliverypool() {
		return deliverypool;
	}

	public void setDeliverypool(Long deliverypool) {
		this.deliverypool = deliverypool;
	}

	public String getDperson() {
		return dperson;
	}

	public void setDperson(String dperson) {
		this.dperson = dperson;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public ErrorCodes getError() {
		return error;
	}

	public void setError(ErrorCodes error) {
		this.error = error;
	}

	public EnumBoolean.type getPersonfound() {
		return personfound;
	}

	public void setPersonfound(EnumBoolean.type personfound) {
		this.personfound = personfound;
	}

	public EnumBoolean.type getDistancefound() {
		return distancefound;
	}

	public void setDistancefound(EnumBoolean.type distancefound) {
		this.distancefound = distancefound;
	}

}
