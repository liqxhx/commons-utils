package com.liqh.commons.fieldvalidator;

import java.math.BigDecimal;

 
public class AppTest

{ 
	
	public static void main(String[] args) {
		BigDecimal t = new BigDecimal("1.01");
		
		System.out.println(t.intValue());	//1
		System.out.println(t.toPlainString());//1.01
	}
}
