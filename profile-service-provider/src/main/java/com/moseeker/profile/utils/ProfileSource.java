package com.moseeker.profile.utils;

public enum ProfileSource {

	MOSEEKER_MOBILE, PROFILE_PC, EMAIL,IMPORT;

	@Override
	public String toString() {
		switch(this) {
			case MOSEEKER_MOBILE : 
				return "1";
			case PROFILE_PC : 
				return "2";
			case EMAIL :
				return "3";
			case IMPORT : 
				return "4";
			default:
				return "0";
			
		}
	}
	
	
}
