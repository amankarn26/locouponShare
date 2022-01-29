package com.locoupon.common.enums;

public class PositionType {


	public enum type {
		TOP("TOP"),
		MIDDLE("MIDDLE"),
		BOTTOM("BOTTOM");

		
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
