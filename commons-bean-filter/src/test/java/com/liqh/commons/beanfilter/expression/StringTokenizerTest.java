/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import java.util.StringTokenizer;

import org.junit.Test;

public class StringTokenizerTest {
	@Test
	public void test(){
		String string = "filter.beanname.ruleN";
		StringTokenizer token = new StringTokenizer(string, "\\.");
		System.out.println(token.hasMoreTokens() +" "+token.hasMoreElements());
		System.out.println(token.nextToken());
		System.out.println(token.hasMoreTokens() +" "+token.hasMoreElements());
		System.out.println(token.nextToken());
		System.out.println(token.hasMoreTokens() +" "+token.hasMoreElements());
		System.out.println(token.nextToken());
		System.out.println(token.hasMoreTokens() +" "+token.hasMoreElements());
		 
	}
}
