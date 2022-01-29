package com.locoupon.common.model;

public class QuoteMonthCount {
	
	private Long count;
	private String month;
	private String monthNo;
	private String year;
	private Double sale;
	private Double saleprod;
	private Double saleptype;
	
	public QuoteMonthCount()
	{
		this.setSale(0.0);
		this.setSaleprod(0.0);
		this.setSaleptype(0.0);
	}
	public Double getSaleptype() {
		return saleptype;
	}
	public void setSaleptype(Double saleptype) {
		this.saleptype = saleptype;
	}
	public Double getSaleprod() {
		return saleprod;
	}
	public void setSaleprod(Double saleprod) {
		this.saleprod = saleprod;
	}
	public Double getSale() {
		return sale;
	}
	public void setSale(Double sale) {
		this.sale = sale;
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
	public void setQvalue(double totalQvaluesPM) {
		// TODO Auto-generated method stub
		
	}
	
	
	



}
