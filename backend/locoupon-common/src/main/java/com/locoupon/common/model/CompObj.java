package com.locoupon.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RateUnit.type;	

public class CompObj implements Serializable {
	private static final long serialVersionUID = -770567063917826084L;

	private Long id;
	private String name;
	private String path;
	private Long count;
	private Address address;
	private String Category;
	private String brand;
	private RateUnit.type rateunit;
	/*private String variant;
	private Double rateperunit;*/
	private String description;
	
	private List<MerchantOffer> pstocklist= new ArrayList<MerchantOffer>();

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RateUnit.type getRateunit() {
		return rateunit;
	}

	public void setRateunit(RateUnit.type rateunit) {
		this.rateunit = rateunit;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	

	public List<MerchantOffer> getPstocklist() {
		return pstocklist;
	}

	public void setPstocklist(List<MerchantOffer> pstocklist) {
		this.pstocklist = pstocklist;
	}



}
