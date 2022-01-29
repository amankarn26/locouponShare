package com.locoupon.common.enums;

public class LoginType {

	public enum type {
		CUSTOMER("CUSTOMER"), 
		MERCHANT("MERCHANT"),
		EMPLOYEE("EMPLOYEE");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "PAPER";
		}

		private String codedesc = "";

		public String codedesc() {
			return codedesc;
		}

		public void setValue(String codedesc) {
			this.codedesc = codedesc;
		}
	}

}