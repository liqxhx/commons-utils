/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.drools;

import java.util.Date;

import org.junit.Test;

import com.liqh.commons.beanfilter.Student;
import com.liqh.commons.lang.i18n.I18n;

public class DroolsFilterTest {
	@SuppressWarnings("static-access")
	@Test
	public void test() throws Exception{
		I18n.init(null, "framework/resources/bean_filter_err");
		DroolsFilter filter = new DroolsFilter();
		filter.setForceExecuteFilter(true);
		filter.setDrls(new String[]{"framework/filter/drools-filters.drl"});
		filter.create();
		
		Student student = new Student();
		student.setName("qhli");
		System.out.println(filter.filter(student));
		
		Thread.currentThread().sleep(1000);
		
		System.out.println("============reload==========");
		filter.addRule(null, true);
		

		student.setName("sk");
		student.setAge(19);
		student.setMarried(false);
		student.setBirthDay(new Date());
		System.out.println(filter.filter(student));
	}
}
