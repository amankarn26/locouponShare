package com.locoupon.common.model;

import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class ReturnObject {

	private Long id;
	private String name;
	private String generatednum;
	private String strval;
	private ErrorCodes error;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeneratednum() {
		return generatednum;
	}

	public void setGeneratednum(String generatednum) {
		this.generatednum = generatednum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrval() {
		return strval;
	}

	public void setStrval(String strval) {
		this.strval = strval;
	}

	public ErrorCodes getError() {
		return error;
	}

	public void setError(ErrorCodes error) {
		this.error = error;
	}

}
