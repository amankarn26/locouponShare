package com.locoupon.common.enums;

public class BranchType {
	
	public enum type {
		MAIN("MAIN"), 
		SUB("SUB");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "PAPER";
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
