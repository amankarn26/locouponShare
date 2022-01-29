package com.locoupon.common.enums;

public class OrderType {

	public enum type {
		DINE_IN("DINE_IN"), 
		PICK_UP("PICK_UP"), 
		HOME_DELIVERY("HOME_DELIVERY");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "PURCHASE";
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