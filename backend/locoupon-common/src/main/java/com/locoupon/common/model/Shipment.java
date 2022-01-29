package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

public class Shipment {

	
	private String courier_name;
	private String rate;
	
	private long status;
	private String message;
	
	
	public Shipment() {
	}


	public String getCourier_name() {
		return courier_name;
	}


	public void setCourier_name(String courier_name) {
		this.courier_name = courier_name;
	}


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public long getStatus() {
		return status;
	}


	public void setStatus(long status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
	
	
}
