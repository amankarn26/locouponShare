package com.locoupon.common.enums;

public class PaymentStatus {

	public enum type {
		PAID("PAID"),
		UNPAID("UNPAID"),
		ADMIN_APPROVED("ADMIN_APPROVED"),
		CREDIT_USED ("CREDIT_USED");
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "UNPAID";
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
