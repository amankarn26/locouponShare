package com.locoupon.common.model;

import java.sql.Timestamp;


public class CompOrder {

	private Timestamp expdeliverytime;
	private Timestamp day;

	public Timestamp getExpdeliverytime() {
		return expdeliverytime;
	}

	public void setExpdeliverytime(Timestamp expdeliverytime) {
		this.expdeliverytime = expdeliverytime;
	}

	public Timestamp getDay() {
		return day;
	}

	public void setDay(Timestamp day) {
		this.day = day;
	}

}
