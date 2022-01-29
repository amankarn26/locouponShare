package com.locoupon.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RateUnit.type;


public class CompProduct implements Serializable {
	private static final long serialVersionUID = -770567063917826084L;

	private Long id;
	// private Set<Document image = new HashSet<Document>();
	private String name;
	private String prodtype;
	private String image;
	private String brand;
	private RateUnit.type rateunit;

	private List<CompStock> pstock = new ArrayList<CompStock>();
	

	@Id
	@GeneratedValue
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

	public String getProdtype() {
		return prodtype;
	}

	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public RateUnit.type getRateunit() {
		return rateunit;
	}

	public void setRateunit(RateUnit.type rateunit) {
		this.rateunit = rateunit;
	}

	public List<CompStock> getPstock() {
		return pstock;
	}

	public void setPstock(List<CompStock> pstock) {
		this.pstock = pstock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	


}
