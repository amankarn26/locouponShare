package com.locoupon.common.model;

import java.util.Date;

public class MonthlyAccount {

	private float income;
	private float expense;
	private String month;
	private String year;
	
	private String monthNo;
	
	private Date date;

	public float getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public float getExpense() {
		return expense;
	}

	public void setExpense(float expense) {
		this.expense = expense;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
