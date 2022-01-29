package com.locoupon.common.enums;

public class BilledToType {
	
	public enum type
	{
		CUSTOMER("CUSTOMER"),
		INSURER("INSURER");
		
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "CUSTOMER";
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
