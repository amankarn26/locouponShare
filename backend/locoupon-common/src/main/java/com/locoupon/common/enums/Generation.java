package com.locoupon.common.enums;

public class Generation {

	public  enum type {
		SALE("SALE"),
		PURCHASEORDER("PURCHASEORDER"), 
		BOOKING("BOOKING"),
		VOUCHER("VOUCHER"),
		INVOICE("INVOICE"),
		SERVICING("SERVICING"),
		CHALLAN("CHALLAN"),
		INSURANCE("INSURANCE"),
		PACKING("PACKING"),
		DISPATCH("DISPATCH");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "PURCHASE";
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
