package com.locoupon.common.enums;

public class RequestState {
	
	public  enum state {
		SUCCESS("SUCCESS"),
		FAILED("FAILED"),
		NA("NA");
		
		private state(String value) {
			this.codedesc = value;
		}
		private state(){
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
