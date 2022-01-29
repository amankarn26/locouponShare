package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

public class Amount {

	private Double totalservice;
	private Double totalamount;
	private List<AmountObj> amtobj = new ArrayList<AmountObj>();

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public List<AmountObj> getAmtobj() {
		return amtobj;
	}

	public void setAmtobj(List<AmountObj> amtobj) {
		this.amtobj = amtobj;
	}

	public Double getTotalservice() {
		return totalservice;
	}

	public void setTotalservice(Double totalservice) {
		this.totalservice = totalservice;
	}

}