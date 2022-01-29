package com.locoupon.common.enums;

public class AdvtType {
	
	public enum type {
		IMAGE("IMAGE"),
		VIDEO("VIDEO");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "IMAGE";
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
