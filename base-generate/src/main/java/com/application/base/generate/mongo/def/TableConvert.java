package com.application.base.generate.mongo.def;

/**
 * 
 * 表的转换判断.
 * @author bruce.
 */

public class TableConvert {
	
	static String YES="YES",Y="Y",NO="NO",N="N";
	
	public static String getNullAble(String nullable) {
		if ((YES.equalsIgnoreCase(nullable)) || (Y.equalsIgnoreCase(nullable))) {
			return Y;
		}
		
		if ((NO.equalsIgnoreCase(nullable)) || (N.equalsIgnoreCase(nullable)) ) {
			return N;
		}
		
		return null;
	}

}
