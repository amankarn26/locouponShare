package com.locoupon.common.model;

import java.util.Date;

import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class OrderObj {

	private Long orderid;
	private String invoice;
	private Document invoicedoc;
	private String status;
	private String number;
	private Long vid;
	private String vendor;
	private String custnumber;
	private Long custid;
	private String customer;
	private String dpnumber;
	private Long dpid;
	private String dperson;
	private Date expdelivery;
	private ErrorCodes error;
	private NotificationMap notification;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorCodes getError() {
		return error;
	}

	public void setError(ErrorCodes error) {
		this.error = error;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public NotificationMap getNotification() {
		return notification;
	}

	public void setNotification(NotificationMap scm) {
		this.notification = scm;
	}

	public String getCustnumber() {
		return custnumber;
	}

	public void setCustnumber(String custnumber) {
		this.custnumber = custnumber;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getDpnumber() {
		return dpnumber;
	}

	public void setDpnumber(String dpnumber) {
		this.dpnumber = dpnumber;
	}

	public Long getDpid() {
		return dpid;
	}

	public void setDpid(Long dpid) {
		this.dpid = dpid;
	}

	public String getDperson() {
		return dperson;
	}

	public void setDperson(String dperson) {
		this.dperson = dperson;
	}

	public Date getExpdelivery() {
		return expdelivery;
	}

	public void setExpdelivery(Date expdelivery) {
		this.expdelivery = expdelivery;
	}

	public Document getInvoicedoc() {
		return invoicedoc;
	}

	public void setInvoicedoc(Document invoicedoc) {
		this.invoicedoc = invoicedoc;
	}

}