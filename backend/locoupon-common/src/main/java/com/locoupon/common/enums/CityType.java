package com.locoupon.common.enums;

public class CityType {

	public  enum type {
		METRO("METRO"),
		NON_METRO("NON_METRO"),
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
