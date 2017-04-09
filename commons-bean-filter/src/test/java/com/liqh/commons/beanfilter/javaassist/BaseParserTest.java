/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.javaassist;

import org.junit.BeforeClass;
import org.junit.Test;

import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.javaassist.BooleanParser;
import com.liqh.commons.beanfilter.javaassist.DateParser;
import com.liqh.commons.beanfilter.javaassist.NumbericParser;
import com.liqh.commons.beanfilter.javaassist.StringParser;
import com.liqh.commons.lang.i18n.I18n;


public class BaseParserTest {
	@BeforeClass
	public static void init(){
		I18n.init(null, "framework/resources/bean_filter_err");
	}
	
	@Test
	public void testBooleanParserToScript(){		
		BooleanParser booleanParser = new BooleanParser();
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "married", "Boolean", true, "==");
		System.out.println(booleanParser.toScript(propertyFilterDescriptor));
	}
	
	@Test
	public void testStringParserToScript(){		
		StringParser stringParser = new StringParser();
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "name", "String", "liqh", "equals");
		System.out.println(stringParser.toScript(propertyFilterDescriptor));
	}
	
	@Test
	public void testNumbericParserToScript(){		
		NumbericParser numbericParser = new NumbericParser();
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", "20", ">=");
		System.out.println(numbericParser.toScript(propertyFilterDescriptor));
	}
	
	@Test
	public void testDateParserToScript() {
		DateParser dateParser = new DateParser();
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-19 12:55:00", ">=");
		System.out.println(dateParser.toScript(propertyFilterDescriptor));
	}
}
