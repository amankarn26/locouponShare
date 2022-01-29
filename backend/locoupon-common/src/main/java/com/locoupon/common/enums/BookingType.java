package com.locoupon.common.enums;

public class BookingType {

	public enum type {

		EVENT("EVENT"), CONFERENCE("CONFERENCE"), // --order
		TABLE("TABLE"),
		PUB("PUB"), NONE("NONE");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "NONE";
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
