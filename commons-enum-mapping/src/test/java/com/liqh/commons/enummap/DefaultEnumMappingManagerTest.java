/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.enummap;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.lang.i18n.I18n;

public class DefaultEnumMappingManagerTest {
	IEnumMappingManager manager = new DefaultEnumMappingManager();
	
	@org.junit.Test
	public void testConvertToEnum(){
		I18n.init(null, "framework/resources/enummap_err");
		Map<String, String> alarm = new HashMap<String, String>();
		alarm.put("severity", "三级告警");
		alarm.put("activeStatus", "活动告警");
		System.out.println(alarm);
		System.out.println(manager.convertToEnum(alarm));
		System.out.println(manager.convertToString(manager.convertToEnum(alarm)));
	}
}
