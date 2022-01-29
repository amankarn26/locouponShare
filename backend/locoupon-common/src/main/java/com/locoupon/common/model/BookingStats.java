package com.locoupon.common.model;

public class BookingStats {

	private Long totalOrders;
	private Long totalPlaced;
	private Long totalPacked;
	private Long totalPickedup;
	private Long totalPaid;
	private Long totalCancelled;
	private Long totalDelivered;

	private double lastmonthAmount;
	private double currentmonthAmount;
	private double todayAmount;

	private Long todayOrder;
	private Long currentmonthOrder;
	private Long lastmonthOrder;

	private double gstcurrentmonth;
	private double gstlastmonth;
	
	public BookingStats() {
		this.totalOrders = (long) 0;
		this.totalPlaced = (long) 0;
		this.totalPacked = (long) 0;
		this.totalPickedup = (long) 0;
		this.totalPaid = (long) 0;
		this.totalCancelled = (long) 0;
		this.totalDelivered = (long) 0;
		this.todayOrder = (long) 0;
		this.currentmonthOrder = (long) 0.0;
		this.lastmonthOrder = (long) 0;
		this.lastmonthAmount = 0.0;
		this.currentmonthAmount = 0.0;
		this.todayAmount = 0.0;
		this.gstcurrentmonth=0.0;
		this.gstlastmonth=0.0;
	}

	public Long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}

	public Long getTotalPlaced() {
		return totalPlaced;
	}

	public void setTotalPlaced(Long totalPlaced) {
		this.totalPlaced = totalPlaced;
	}

	public Long getTotalPacked() {
		return totalPacked;
	}

	public void setTotalPacked(Long totalPacked) {
		this.totalPacked = totalPacked;
	}

	public Long getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Long totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Long getTotalCancelled() {
		return totalCancelled;
	}

	public void setTotalCancelled(Long totalCancelled) {
		this.totalCancelled = totalCancelled;
	}

	public Long getTotalDelivered() {
		return totalDelivered;
	}

	public void setTotalDelivered(Long totalDelivered) {
		this.totalDelivered = totalDelivered;
	}

	public double getLastmonthAmount() {
		return lastmonthAmount;
	}

	public void setLastmonthAmount(double lastmonthAmount) {
		this.lastmonthAmount = lastmonthAmount;
	}

	public double getCurrentmonthAmount() {
		return currentmonthAmount;
	}

	public void setCurrentmonthAmount(double currentmonthAmount) {
		this.currentmonthAmount = currentmonthAmount;
	}

	public double getTodayAmount() {
		return todayAmount;
	}

	public void setTodayAmount(double todayAmount) {
		this.todayAmount = todayAmount;
	}

	public Long getTodayOrder() {
		return todayOrder;
	}

	public void setTodayOrder(Long todayOrder) {
		this.todayOrder = todayOrder;
	}

	public Long getCurrentmonthOrder() {
		return currentmonthOrder;
	}

	public void setCurrentmonthOrder(Long currentmonthOrder) {
		this.currentmonthOrder = currentmonthOrder;
	}

	public Long getLastmonthOrder() {
		return lastmonthOrder;
	}

	public void setLastmonthOrder(Long lastmonthOrder) {
		this.lastmonthOrder = lastmonthOrder;
	}

	public Long getTotalPickedup() {
		return totalPickedup;
	}

	public void setTotalPickedup(Long totalPickedup) {
		this.totalPickedup = totalPickedup;
	}

	public double getGstcurrentmonth() {
		return gstcurrentmonth;
	}

	public void setGstcurrentmonth(double gstcurrentmonth) {
		this.gstcurrentmonth = gstcurrentmonth;
	}

	public double getGstlastmonth() {
		return gstlastmonth;
	}

	public void setGstlastmonth(double gstlastmonth) {
		this.gstlastmonth = gstlastmonth;
	}

	

	
}