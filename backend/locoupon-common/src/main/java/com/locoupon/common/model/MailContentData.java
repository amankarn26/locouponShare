package com.locoupon.common.model;

import java.util.List;
import java.util.Map;

public class MailContentData {
	private String subject;
	private List table;
	private String body;
	private List<String> attachmentFiles;
	private Map content;

	private List costtable;

	private static int no=1;

	public int getNo() {
		return this.no++;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List getTable() {
		return table;
	}

	public void setTable(List table) {
		this.table = table;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getAttachmentFiles() {
		return attachmentFiles;
	}

	public void setAttachmentFiles(List<String> attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}

	public Map getContent() {
		return content;
	}

	public void setContent(Map content) {
		this.content = content;
	}

	public List getCosttable() {
		return costtable;
	}

	public void setCosttable(List costtable) {
		this.costtable = costtable;
	}
}
