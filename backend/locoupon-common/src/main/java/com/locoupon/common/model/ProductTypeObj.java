package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

import com.locoupon.common.entity.Product;


public class ProductTypeObj {
	
	public String prodtype;
	public List<Product> product= new ArrayList<Product>();
	
	
	
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	
	

}
