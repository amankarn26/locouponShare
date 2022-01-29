package com.locoupon.common.enums;

public class GetEnum {

	public enum type {

		THISMONTH("THISMONTH"),
		THISWEEK("THISWEEK"), 
		TODAY("TODAY"), 
		LASTMONTH("LASTMONTH"),
		RECEIVED("RECEIVED"), 
		PAYABLE("PAYABLE");

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
