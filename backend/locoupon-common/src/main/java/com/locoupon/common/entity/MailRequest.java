package com.locoupon.common.entity;

import com.locoupon.common.enums.BilledToType;
import com.locoupon.common.enums.EntryType;
import com.locoupon.common.enums.InvoiceType;
import com.locoupon.common.model.MailContentData;

public class MailRequest {
	private String templateId;
	private String to;
	private String cc;
	private boolean htmlContent = true;
	private MailContentData contentData;

	private InvoiceType.type invoicetype;
	private BilledToType.type billedto;
	private EntryType.type entrytype;
	private String pdfid;
	private String fid;


	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public boolean isHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(boolean htmlContent) {
		this.htmlContent = htmlContent;
	}

	public MailContentData getContentData() {
		return contentData;
	}

	public void setContentData(MailContentData contentData) {
		this.contentData = contentData;
	}


	public String getPdfid() {
		return pdfid;
	}

	public void setPdfid(String pdfid) {
		this.pdfid = pdfid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public InvoiceType.type getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(InvoiceType.type invoicetype) {
		this.invoicetype = invoicetype;
	}

	public BilledToType.type getBilledto() {
		return billedto;
	}

	public void setBilledto(BilledToType.type billedto) {
		this.billedto = billedto;
	}

	public EntryType.type getEntrytype() {
		return entrytype;
	}

	public void setEntrytype(EntryType.type entrytype) {
		this.entrytype = entrytype;
	}

}
