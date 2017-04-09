/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter;

public class BooleanTest {
	public static void main(String[] args) {
		System.out.println(Boolean.parseBoolean(null));// false
		System.out.println(Boolean.parseBoolean(""));// false
		System.out.println(Boolean.parseBoolean("  "));// false
		System.out.println(Boolean.parseBoolean("0"));// false
		System.out.println(Boolean.parseBoolean("y"));// false
	}
}
