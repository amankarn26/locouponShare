package com.locoupon.common.enums;

import java.util.Date;

public class DateValue {

	private Date startdate;
	private Date enddate;
	// private DurationType.type type;
	private int numdays;

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getNumdays() {
		return numdays;
	}

	public void setNumdays(int numdays) {
		this.numdays = numdays;
	}

	public String toPrint() {
		return "DateValue [startdate=" + startdate + ", enddate=" + enddate + ", numdays=" + numdays + "]";
	}

}
