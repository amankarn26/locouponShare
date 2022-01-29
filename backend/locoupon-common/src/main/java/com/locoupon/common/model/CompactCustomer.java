package com.locoupon.common.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class CompactCustomer implements Serializable {
	private static final long serialVersionUID = -770567063917826084L;

	private Long id;
	private Long custid;

	private String name;
	private String city;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
