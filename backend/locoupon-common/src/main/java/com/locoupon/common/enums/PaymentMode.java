package com.locoupon.common.enums;

public class PaymentMode {
	public  enum type {
		ADVANCE("ADVANCE"),
		CREDIT("CREDIT"),
		BANK("BANK"),
		CASH("CASH"),
		NONE("NONE");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
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
