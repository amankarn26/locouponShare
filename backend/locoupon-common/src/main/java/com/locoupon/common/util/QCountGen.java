package com.locoupon.common.util;

public class QCountGen implements Comparable<QCountGen>{

	
	private String  name;
	private Long count;
	
	
	@Override
	public int compareTo(QCountGen o) {
		return count < o.count ? -1 : count > o.count ? 1 : 0;
	}
	

	public QCountGen() {
		// TODO Auto-generated constructor stub
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
