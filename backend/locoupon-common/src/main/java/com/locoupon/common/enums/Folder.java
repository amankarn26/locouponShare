package com.locoupon.common.enums;

public class Folder {
		
		public  enum type {
			PRODUCT("DISH"),
			EMPLOYEE("EMPLOYEE"),
			CAMPAIGN("CAMPAIGN"),
			VOUCHER("VOUCHER"),
			CUSTOMER("CUSTOMER"),
			SPAREPART("SPAREPART");
			
			private type(String value) {
				this.codedesc = value;
			}
			private type(){
				this.codedesc = "PRODUCT";
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
