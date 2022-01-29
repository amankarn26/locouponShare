package com.locoupon.common.enums;

public class VendorStats {
	
	private Long id;
	private int todayordercount;
	private Double todayorderamt;
	private Double lastmonthorder;
	private Double currentmonthorder;
	
	public VendorStats() {
		this.setTodayorderamt((double) 0);
		this.setCurrentMOrder((double) 0);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTodayordercount() {
		return todayordercount;
	}
	public void setTodayordercount(int todayordercount) {
		this.todayordercount = todayordercount;
	}
	public Double getTodayorderamt() {
		return todayorderamt;
	}
	public void setTodayorderamt(Double todayorderamt) {
		this.todayorderamt = todayorderamt;
	}
	public Double getLastmonthorder() {
		return lastmonthorder;
	}
	public void setLastMOrder(Double lastmonthorder) {
		this.lastmonthorder = lastmonthorder;
	}
	public Double getCurrentmonthorder() {
		return currentmonthorder;
	}
	public void setCurrentMOrder(Double currentmonthorder) {
		this.currentmonthorder = currentmonthorder;
	}
	
	
	
	

}
