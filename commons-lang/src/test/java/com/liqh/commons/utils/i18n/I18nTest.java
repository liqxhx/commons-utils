/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.utils.i18n;

import java.util.Locale;

import org.junit.Test;

import com.liqh.commons.lang.i18n.I18n;

public class I18nTest {
	@Test
	public void testGetMessage(){
		I18n.init(null, "framework/i18n/app", "framework/i18n/a", "framework/i18n/err");
		
		
		System.out.println(I18n.getMessage("000000", "hello", 1));
		System.out.println(I18n.getMessage("000000", "x", Locale.US, "hello", 1));
		
		System.out.println(I18n.getMessage("X20161107001", "测试" ));
		
/*		System.out.println(I18n.getMessage(Locale.US, "i18n/app", "000000", "hello", 1));
		System.out.println(I18n.getMessage(Locale.CHINA, "i18n/app", "000000", "hello", 1));
		System.out.println(I18n.getMessage("i18n/app", "000000", "hello", 1));
		System.out.println(I18n.getMessage(Locale.CHINA,"i18n/app", "000001"));
		System.out.println(I18n.getMessage("i18n/app", "000001"));
		System.out.println(I18n.getMessage(Locale.US, "i18n/app", "000001"));
		
		System.out.println(I18n.getMessage("i18n/app", "000002"));
		System.out.println(I18n.getMessage(Locale.CHINA, "i18n/app", "000002"));
		System.out.println(I18n.getMessage(Locale.US, "i18n/app", "000002"));*/
	}
}
