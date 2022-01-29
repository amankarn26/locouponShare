package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

import com.locoupon.common.entity.Order;
import com.locoupon.common.enums.EnumBoolean;

public class Settlement {
	
	private List<Long> cashcollectedOid= new ArrayList<Long>();
	private List<Long> cashNotcollectedOid = new ArrayList<Long>();
	private List<Order> cashcollectedorder = new ArrayList<Order>();
	private List<Order> cashNotcollectedorder = new ArrayList<Order>();
	private List<Order> allorder = new ArrayList<Order>();
	private Integer ordercount;
	private Double codamount;
	private Double onlineamount; //non codamount
	private Double delievrycharge;
	private Double totalpayble;
	private Double totalreceived;
	private Double totalamt;
	
	public Integer getOrdercount() {
		return ordercount;
	}
	public void setOrdercount(Integer ordercount) {
		this.ordercount = ordercount;
	}
	public Double getCodamount() {
		return codamount;
	}
	public void setCodamount(Double codamount) {
		this.codamount = codamount;
	}
	public Double getDelievrycharge() {
		return delievrycharge;
	}
	public void setDelievrycharge(Double delievrycharge) {
		this.delievrycharge = delievrycharge;
	}
	
	public Double getTotalpayble() {
		return totalpayble;
	}
	public void setTotalpayble(Double totalpayble) {
		this.totalpayble = totalpayble;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
	
	public Double getOnlineamount() {
		return onlineamount;
	}
	public void setOnlineamount(Double onlineamount) {
		this.onlineamount = onlineamount;
	}
	public List<Order> getCashcollectedorder() {
		return cashcollectedorder;
	}
	public void setCashcollectedorder(List<Order> cashcollectedorder) {
		this.cashcollectedorder = cashcollectedorder;
	}
	public List<Order> getCashNotcollectedorder() {
		return cashNotcollectedorder;
	}
	public void setCashNotcollectedorder(List<Order> cashNotcollectedorder) {
		this.cashNotcollectedorder = cashNotcollectedorder;
	}
	public List<Long> getCashcollectedOid() {
		return cashcollectedOid;
	}
	public void setCashcollectedOid(List<Long> cashcollectedOid) {
		this.cashcollectedOid = cashcollectedOid;
	}
	public List<Long> getCashNotcollectedOid() {
		return cashNotcollectedOid;
	}
	public void setCashNotcollectedOid(List<Long> cashNotcollectedOid) {
		this.cashNotcollectedOid = cashNotcollectedOid;
	}
	public Double getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(Double totalamt) {
		this.totalamt = totalamt;
	}
	public List<Order> getAllorder() {
		return allorder;
	}
	public void setAllorder(List<Order> allorder) {
		this.allorder = allorder;
	}
	
	
	
	

}
