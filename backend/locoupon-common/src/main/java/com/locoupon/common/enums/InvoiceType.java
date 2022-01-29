package com.locoupon.common.enums;

public class InvoiceType {
	
	public enum type
	{
		CUSTOMER("CUSTOMER"),
		DELIVERY_PERSON("DELIVERY_PERSON");		
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "DELIVERY_PERSON";
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
