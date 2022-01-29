package com.locoupon.common.enums;

public class PriceType {
	
	public  enum type {
		RETAIL("RETAIL"),
		CSD("CSD"),
		GOVT("GOVT"),
		INSTITUTIONAL("INSTITUTIONAL"),
		DGS_D("DGS_D"),
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
