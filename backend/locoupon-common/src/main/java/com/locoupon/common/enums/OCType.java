package com.locoupon.common.enums;

public class OCType {

	public enum type {
		ADD,
		SUB;
	};

	

	private String codedesc = "";
	public String codedesc() {
		return codedesc;
	}
	
	public void setValue(String codedesc) {
		this.codedesc = codedesc;
	}

	}

