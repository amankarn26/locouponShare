package com.locoupon.common.enums;

public class HeadType {

	public  enum type {
		SALARYPAYMENT("SALARYPAYMENT"),
		TEAPAYMENT("TEAPAYMENT"),
		ELECTRICITYPAYMENT("ELECTRICITYPAYMENT"),
		OTHEREXPENSES("OTHEREXPENSES"),
		SALEINCOMINGPAYMENT("SALEORDERINCOMINGPAYMENT"),
		PURCHASEOUTGOINGPAYMENT("PURCHASEOUTGOINGPAYMENT"),
		SEVICINGINCOMINGPAYMENT("SEVICINGINCOMINGPAYMENT"),
		BOOKINGINCOMINGPAYMENT("BOOKINGINCOMINGPAYMENT"),
		CANCELBOOKINGINCOMINGPAYMENT("CANCELBOOKINGINCOMINGPAYMENT"), 
		STOCKINCOMING("STOCKINCOMING");
		
		
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "SALARYPAYMENT";
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
