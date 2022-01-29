package com.locoupon.common.enums;

public class EntryType {
	
	public  enum type {
		QUOTATION("QUOTATION"),
		BOOKING("BOOKING"),
		SALE("SALE"),
		NONE("NONE"),
		PACKING("PACKING"),
		DISPATCH("DISPATCH");
		
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
