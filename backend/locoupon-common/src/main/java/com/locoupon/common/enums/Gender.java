package com.locoupon.common.enums;

public class Gender {
	
	public  enum type {
		TRANSGENDER("TRANSGENDER"),
		MALE("MALE"),
		FEMALE("FEMALE");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "MALE";
		}
		private String codedesc = "";
		public String codedesc() {
			return codedesc;
		}
		public void setValue(String codedesc) {
			this.codedesc = codedesc;
		}
		
	}}
