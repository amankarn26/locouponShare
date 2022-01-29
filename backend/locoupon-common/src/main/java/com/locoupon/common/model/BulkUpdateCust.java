package com.locoupon.common.model;

import java.util.List;



public class BulkUpdateCust {

	private List<Long> customers;

	private String ratelabel; // ratelabel drop down in UI

	private int maxquotesperday;

	private Long relationshipmgr;

	private String relationshipmgrname;

	public List<Long> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Long> customers) {
		this.customers = customers;
	}

	public int getMaxquotesperday() {
		return maxquotesperday;
	}

	public void setMaxquotesperday(int maxquotesperday) {
		this.maxquotesperday = maxquotesperday;
	}

	public String getRatelabel() {
		return ratelabel;
	}

	public void setRatelabel(String ratelabel) {
		this.ratelabel = ratelabel;
	}

	public Long getRelationshipmgr() {
		return relationshipmgr;
	}

	public void setRelationshipmgr(Long relationshipmgr) {
		this.relationshipmgr = relationshipmgr;
	}

	public String getRelationshipmgrname() {
		return relationshipmgrname;
	}

	public void setRelationshipmgrname(String relationshipmgrname) {
		this.relationshipmgrname = relationshipmgrname;
	}

	

}
