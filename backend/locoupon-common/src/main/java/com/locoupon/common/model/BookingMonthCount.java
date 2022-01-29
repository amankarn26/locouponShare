package com.locoupon.common.model;

public class BookingMonthCount {

	private Long count;
	private String month;
	private String monthNo;
	private String year;
	private double qvalue;

	public BookingMonthCount() {
		this.count = (long) 0;
		this.month = "0";
		this.monthNo = "0";
		this.year = "0";
		this.qvalue = 0.0;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}

	public double getQvalue() {
		return qvalue;
	}

	public void setQvalue(double qvalue) {
		this.qvalue = qvalue;
	}

}
