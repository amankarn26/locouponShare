package com.locoupon.common.enums;

public class CustPriority {
	
	public  enum type {
		P1("P1"),
		P2("P2"),
		P3("P3"),
		P4("P4"),
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
