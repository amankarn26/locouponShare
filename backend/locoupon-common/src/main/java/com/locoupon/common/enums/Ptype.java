package com.locoupon.common.enums;

public class Ptype {

		
		public  enum type {
			PRODUCT("PRODUCT"),
			SPAREPARTS("SPAREPARTS"),
			NONE("NONE");
			
			private type(String value) {
				this.codedesc = value;
			}
			private type(){
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
