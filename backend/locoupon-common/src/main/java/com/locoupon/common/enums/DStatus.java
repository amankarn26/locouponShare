package com.locoupon.common.enums;

public class DStatus {

	
	public  enum type {
		PENDING("PENDING"),
		APPROVED("APPROVED"),
		REJECTED("REJECTED"),
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
