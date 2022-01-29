package com.locoupon.common.model;

import java.util.List;


public class ProductOrderStats {

	private List<TopCustomers> topcustomer;
	private List<TopVendors> topvendor;
	private List<TopProducts> topproduct;

	public List<TopCustomers> getTopcustomer() {
		return topcustomer;
	}

	public void setTopcustomer(List<TopCustomers> topcustomer) {
		this.topcustomer = topcustomer;
	}

	public List<TopVendors> getTopvendor() {
		return topvendor;
	}

	public void setTopvendor(List<TopVendors> topvendor) {
		this.topvendor = topvendor;
	}

	public List<TopProducts> getTopproduct() {
		return topproduct;
	}

	public void setTopproduct(List<TopProducts> topproduct) {
		this.topproduct = topproduct;
	}

}
