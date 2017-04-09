/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.liqh.commons.beanfilter.Student;

public class ExpressionFilterTest {
	@Test
	public void test() throws ParseException{
		SpelExpressionFilter filter = new SpelExpressionFilter();
		filter.setRulePath(new String[]{"framework/filter/expressions.properties"});
		filter.create();
		
		Student student = new Student();
		student.setName("sk");
		student.setAge(30);
		student.setMarried(true);
		student.setBirthDay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1982-01-01 09:40:00"));
		
		System.out.println(filter.filter(student));
		
		student.setFilteredFlag(true);
		System.out.println(filter.filter(student));
		
		
		Map<String, Object> mapBean = new HashMap<String, Object>();
		System.out.println(filter.filter(mapBean));
	}
}
