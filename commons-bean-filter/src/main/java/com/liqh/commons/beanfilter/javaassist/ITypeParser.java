package com.liqh.commons.beanfilter.javaassist;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
 

 
public interface ITypeParser {
	public String toScript(PropertyFilterDescriptor propertyFilterDescriptor) throws BeanFilterException;
	public String getBooleanVariableName() ;
}
