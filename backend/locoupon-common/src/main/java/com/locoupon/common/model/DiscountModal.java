package com.locoupon.common.model;

import java.util.Date;

import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.EnumBoolean;

public class DiscountModal {
	
	private String name;
	private String couponcode;
	private Double discount;
	private Date validtill;
	private Double cartvalue;
	private Double disvalue;
	private EnumBoolean.type valid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCouponcode() {
		return couponcode;
	}
	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Date getValidtill() {
		return validtill;
	}
	public void setValidtill(Date validtill) {
		this.validtill = validtill;
	}
	public Double getCartvalue() {
		return cartvalue;
	}
	public void setCartvalue(Double cartvalue) {
		this.cartvalue = cartvalue;
	}
	public Double getDisvalue() {
		return disvalue;
	}
	public void setDisvalue(Double disvalue) {
		this.disvalue = disvalue;
	}
	public EnumBoolean.type getValid() {
		return valid;
	}
	public void setValid(EnumBoolean.type valid) {
		this.valid = valid;
	}
	
	

}
