package com.locoupon.common.enums;

public class VendorType {
		
		public  enum type {
			INHOUSE("INHOUSE"),
			EXTERNAL("EXTERNAL");
			
			private type(String value) {
				this.codedesc = value;
			}
			private type(){
				this.codedesc = "INHOUSE";
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
