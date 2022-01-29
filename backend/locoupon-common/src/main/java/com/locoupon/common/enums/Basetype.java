package com.locoupon.common.enums;

public class Basetype {
	
	public  enum type {
		REAL("REAL"),
		PERSONAL("PERSONAL"),
		NOMINAL("NOMINAL");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "REAL";
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
