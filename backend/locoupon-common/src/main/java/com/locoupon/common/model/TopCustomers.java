package com.locoupon.common.model;

public class TopCustomers {

	private Long count;
	private String name;

	public TopCustomers(){
		this.setCount((long) 0);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}
