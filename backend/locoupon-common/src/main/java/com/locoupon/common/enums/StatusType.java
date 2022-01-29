package com.locoupon.common.enums;

public class StatusType {

	public enum type {
		
		//Order 
		PLACED("PLACED"), // --order
		ACCEPTED("ACCEPTED"),
		IN_PROCESS("IN_PROCESS"), 
		PACKED("PACKED"),
		PICKED_UP("PICKED_UP"),
		CANCELLED("CANCELLED"),
		REFUNDED("REFUNDED"), 
		DELIVERED("DELIVERED"),
		
		//Stock
		NOT_AVAILABLE("NOT_AVAILABLE"), // --stock
		AVAILABLE("AVAILABLE"),
		PAID("PAID"), 
		
		OPEN("OPEN"),
		CLOSED("CLOSED"),
		PENDING("PENDING"),
		NOT_SETTELED("NOT_SETTELED"),
		SETTELED("SETTELED"),
		NONE("NONE"),
		
		APPROVED("APPROVED"),
		REJECTED("REJECTED");
		
		
		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "OPEN";
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
