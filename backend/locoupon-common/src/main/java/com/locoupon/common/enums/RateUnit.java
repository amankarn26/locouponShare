package com.locoupon.common.enums;

public class RateUnit {

	public enum type {
		RATEPERPRODUCT("RATEPERPRODUCT");
		//RATEPERPACK("RATEPERPACK");

		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "RATEPERKG";
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
