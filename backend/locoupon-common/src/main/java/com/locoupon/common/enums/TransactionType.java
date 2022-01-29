package com.locoupon.common.enums;

public class TransactionType {
	
	public  enum type {
		CREDIT("CREDIT"),
		DEBIT("DEBIT");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "DEBIT";
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
