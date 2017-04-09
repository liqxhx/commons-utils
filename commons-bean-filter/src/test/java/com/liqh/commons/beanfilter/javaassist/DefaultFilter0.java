/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.javaassist;

import com.liqh.commons.beanfilter.IFilter;

public class DefaultFilter0 implements IFilter {

	@Override
	public boolean filter(Object bean) {
		try{
			String propertyValue = null ;
			String refValue = null ;
			propertyValue = org.apache.commons.beanutils.BeanUtils.getProperty(bean,"married");
			System.out.println(propertyValue);refValue = "false";
			boolean married5a2c97fc373c403f832cd6722c3f52d1 = (propertyValue==null)?( propertyValue != refValue ):(org.apache.commons.lang3.StringUtils.isBlank(refValue)?propertyValue!=refValue: Boolean.parseBoolean(propertyValue) != Boolean.parseBoolean(refValue) );
			return married5a2c97fc373c403f832cd6722c3f52d1;
		}catch(Exception e) {
			
		}
		return false;
	}

}