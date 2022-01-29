package com.locoupon.common.enums;

public class ActCat {

	public  enum type {
		CUSTOMER("CUSTOMER"),
		VENDOR("VENDOR"),
		SELF("SELF");

		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "CUSTOMER";
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
