package com.locoupon.common.enums;

public class ReviewStatus {

	public enum type {
		ADDED("ADDED"),
		APPROVED("APPROVED"),
		REJECTED("REJECTED");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "ADDED";
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
