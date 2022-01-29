package com.locoupon.common.model;

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.EnumBoolean.type;

public class CompStock {

	private Long id;
	private Double rateperunit;
	private Double gst;
	private Double gstamt;
	private Double ratewithgst;
	private Double purchaseprice;
	private EnumBoolean.type available;
	private EnumBoolean.type hasvariant;
	private String variant;
	private Double weight;
	private Integer volume;
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRateperunit() {
		return rateperunit;
	}

	public void setRateperunit(Double rateperunit) {
		this.rateperunit = rateperunit;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getGstamt() {
		return gstamt;
	}

	public void setGstamt(Double gstamt) {
		this.gstamt = gstamt;
	}

	public Double getRatewithgst() {
		return ratewithgst;
	}

	public void setRatewithgst(Double ratewithgst) {
		this.ratewithgst = ratewithgst;
	}

	public Double getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(Double purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public EnumBoolean.type getAvailable() {
		return available;
	}

	public void setAvailable(EnumBoolean.type available) {
		this.available = available;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public EnumBoolean.type getHasvariant() {
		return hasvariant;
	}

	public void setHasvariant(EnumBoolean.type hasvariant) {
		this.hasvariant = hasvariant;
	}

}
