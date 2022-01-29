package com.locoupon.common.entity;

public class Role {

	public enum type {

		SUPERADMIN("SUPERADMIN"), 
		BRANCH("BRANCH"),
		FIRM("FIRM"), 
		DELIVERY_PERSON("DELIVERY_PERSON"), 
		CUSTOMER("CUSTOMER"),
		SUPPLIER("SUPPLIER");

		private type(String value) {
			this.typedesc = value;
		}

		private type() {
			this.typedesc = "NONE";
		}

		private String typedesc = "";

		public String typedesc() {
			return typedesc;
		}

		public void setValue(String codedesc) {
			this.typedesc = codedesc;
		}

	}

}