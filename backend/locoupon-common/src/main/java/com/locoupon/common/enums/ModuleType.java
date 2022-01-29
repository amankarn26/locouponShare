package com.locoupon.common.enums;

public class ModuleType {

	
	public  enum type {
		ENABLE_PRODUCT("ENABLE_PRODUCT"),

		ENABLE_JOBCARD("ENABLE_JOBCARD"),
		ENABLE_CAMPAIGN("ENABLE_CAMPAIGN"),
		ENABLE_VOUCHER("ENABLE_VOUCHER"),
		ENABLE_CUSTOMER("ENABLE_CUSTOMER"),
		ENABLE_SPAREPART("ENABLE_SPAREPART"),
		ENABLE_WARRENTY("ENABLE_WARRENTY"),
		ENABLE_INSURANCE("ENABLE_INSURANCE"),
		;
		
		private type(String value) {
			this.codedesc = value;
		}
		private type(){
			this.codedesc = "ENABLE_PRODUCT";
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
