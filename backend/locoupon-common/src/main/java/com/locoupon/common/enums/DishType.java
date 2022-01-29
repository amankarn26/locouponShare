package com.locoupon.common.enums;

public class DishType {
	
	public enum type {
		VEG("Veg"),
		NON_VEG("Non_Veg"),
		VEGAN("Vegan"),
		MOCKTAIL("Mocktail"),
		COCKTAIL("Cocktail"),
		SOFT_DRINKS("Soft Drinks"),
		HARD_DRINKS("Hard Drinks"),
		 NONE("NONE");

		//VEG/NON_VEG/VEGAN/MOCKTAIL/COCKTAIL/SOFT_DRINKS/HARD_DRINKS
		//Veg/Non-Veg/Vegan/Mocktail/Cocktail/Soft Drinks/Hard Drinks
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
