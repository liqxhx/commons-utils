/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.javaassist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.IFilter;

public class BeanFilterXStreamRuleParserTest {
	@Test
	public void test() throws InstantiationException, IllegalAccessException, Exception{
		List<BeanFilterRule> rules = BeanFilterXStreamRuleParser.parse("framework/filter/xstream-rules.xml");
		IFilter filter = (IFilter) JavaassistFilterBuilder.build(rules.get(0)).newInstance();

		Map<String, Object> bean = new HashMap<String, Object>();
		// #1 or (#2 and (not #3) and #4)
		bean.put("name", "sk"); //  false
		bean.put("age", 20); //true
		bean.put("married", true); // true  
		bean.put("birthDay", "1982-01-01 12:00:00"); // after 1981-06-01 17:30:00
		System.out.println("[filter result]:"+filter.filter(bean)); // true

	}
}
