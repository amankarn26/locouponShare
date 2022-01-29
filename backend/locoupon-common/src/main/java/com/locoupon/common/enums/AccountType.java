package com.locoupon.common.enums;

public class AccountType {

	public enum type {
		BANK("BANK"),
		CASH("CASH"),
		PAYTM("PAYTM"),
		GOOGLE_PAY("GOOGLE_PAY"),
		PHONE_PE("PHONE_PE");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "BANK";
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
