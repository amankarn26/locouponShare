package com.locoupon.common.model;

public class CustomerType {

	public enum type {
		CUSTOMER("CUSTOMER"), 
		RETAILER("RETAILER"),



		WHOLESALER("WHOLESALER"); /// for discount



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
