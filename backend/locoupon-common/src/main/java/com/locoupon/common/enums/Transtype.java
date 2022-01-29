package com.locoupon.common.enums;

public class Transtype {
	
	public  enum type {
		CASH("CASH"),
		BANK("BANK");
		
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
