package com.locoupon.common.enums;

public class Duration {

	public enum type {

		THISMONTH("THISMONTH"),
		THISWEEK("THISWEEK"), 
		TODAY("TODAY"), 
		CASH("CASH"), 
		YESTERDAY("YESTERDAY"), 
		MONDAY("MONDAY"), 
		THURSDAY("THURSDAY"), 
		LASTMONTH("LASTMONTH"), 
		RECEIVED("RECEIVED"),
		PAYABLE("PAYABLE"),
		WITHIN_2_HRS("WITHIN_2_HRS"),
		WITHIN_4_HRS("WITHIN_4_HRS"),
		WITHIN_24_HRS("WITHIN_24_HRS");

		private type(String value) {
			this.typedesc = value;
		}

		private type() {
			this.typedesc = "NONE";
		}

		private String typedesc = "";

		public String typedesc() {
			return typedesc;
		}

		public void setValue(String codedesc) {
			this.typedesc = codedesc;
		}

	}
}