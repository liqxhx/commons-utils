/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.utils;

import java.util.Map;

import org.junit.Test;

public class SystemTest {
	@Test
	public void testGetEnv(){
		Map env = System.getenv();
		System.out.println(env);
	}
	@Test
	public void testGetProperty(){
		System.out.println(System.getProperties());
	}
	
	/**
	 * -Dhello=liqinghui
	 *<p>
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.getProperties());
	}
}
