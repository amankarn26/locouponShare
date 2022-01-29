package com.locoupon.common.model;

import com.locoupon.common.enums.InvoiceType;
import com.locoupon.common.enums.InvoiceType.type;

public class InvoiceObj {
	private Long id;
	private String invoiceid;
	private float transferamt;
	private float servicecharge;
	private float GSTAmt;
	private float totalamount;
	private String todate;
	private String fromdate;
	private InvoiceType.type type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getTransferamt() {
		return transferamt;
	}

	public void setTransferamt(float transferamt) {
		this.transferamt = transferamt;
	}

	public float getServicecharge() {
		return servicecharge;
	}

	public void setServicecharge(float servicecharge) {
		this.servicecharge = servicecharge;
	}

	public float getGSTAmt() {
		return GSTAmt;
	}

	public void setGSTAmt(float gSTAmt) {
		GSTAmt = gSTAmt;
	}

	public float getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(float totalamount) {
		this.totalamount = totalamount;
	}

	public InvoiceType.type getType() {
		return type;
	}

	public void setType(InvoiceType.type type) {
		this.type = type;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}

}
