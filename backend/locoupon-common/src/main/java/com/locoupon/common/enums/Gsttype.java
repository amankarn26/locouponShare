package com.locoupon.common.enums;

public class Gsttype {

	public enum type {
		EXCLUSIVE("EXCLUSIVE"), INCLUSIVE("INCLUSIVE"), NONE("NONE");

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
