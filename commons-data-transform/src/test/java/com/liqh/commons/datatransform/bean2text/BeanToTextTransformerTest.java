/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.bean2text;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.liqh.commons.datatransform.BaseDataTransformerTest;
import com.liqh.commons.datatransform.model.Gender;
import com.liqh.commons.datatransform.model.JavaBean;
import com.liqh.commons.datatransform.model.Student;

@SuppressWarnings("unchecked")
public class BeanToTextTransformerTest extends BaseDataTransformerTest{

	@Test
	public void testBeanToText() {
		Student b1 = new Student();
		b1.setId(UUID.randomUUID().toString());
		b1.setName("lqh");
		b1.setGender(Gender.MALE);
		b1.setAccount(new BigDecimal("4500000.123456789"));
		b1.setWeight(58.525f);
		b1.setHeight(165.00d);
		try {
			b1.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1981-06-01"));
		} catch (ParseException e) {
			logger.error(e.getMessage() , e);
		}
		b1.setScore(100);
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("_stu", b1);

		
		StringBuilder ret = (StringBuilder) getTransformer().transform(root, getMapping("test2Xml"));
		System.out.println(ret.toString());

	}
	
	@Test
	public void testToJava(){
		JavaBean java = new JavaBean();
//		java.setClassDescrption("This is a test, Code genernated by freemarker auto.");
		java.setClassName("Student");
		java.setImplementions(new String[]{"java.io.Serializable"});
//		java.setSuperClass("java.lang.Object");
		java.setPkg("com.liqh.commons.datatransform.model");
		java.setImports(new String[]{"java.math.BigDecimal","java.util.*"});
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("java", java);
		StringBuilder javacode = (StringBuilder) getTransformer().transform(root, getMapping("toJava"));
		System.out.println(javacode.toString());
	}

	@Before
	public void setup() {
	}

	@After
	public void teardown() {

	}

	@BeforeClass
	public static void init() throws Exception {		
		String cfg[] = new String[3];
		cfg[0] = "framework/datatransform/bean2text/default-common.xml";
		cfg[1] = "framework/datatransform/bean2text/mappings-toxml.xml";
		cfg[2] = "framework/datatransform/bean2text/mappings-tojava.xml";
		BaseDataTransformerTest.init(new BeanToTextTransformer(),  new BeanToTextTemplateRuleParser(), cfg);		
	}
}
