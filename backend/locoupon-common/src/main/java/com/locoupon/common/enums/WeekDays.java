package com.locoupon.common.enums;

public class WeekDays {
	public enum type {
		MONDAY("MONDAY"),
		TUSESDAY("TUSEDAY"),
		WEDNESDAY("WEDNESDAY"),
		THURSDAY("THURSDAY"),
		FRIDAY("FRIDAY"),
		SATURDAY("SATURDAY"),
		 NONE("NONE");

		
		private type(String value) {
			this.codedesc = value;
		}

		private type() {
			this.codedesc = "NONE";
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
