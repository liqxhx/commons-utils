package com.liqh.commons.beanfilter.javaassist;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 

public abstract class AbstractTypeParser implements ITypeParser {
	private String booleanVariableName ;
	public static final String CRLF = "\r\n";
	
	public static final String VAL_NULL = "$null";
	
	public static final String OP_NULL_EQUAL= "==";
	public static final String OP_NULL_UNEQUAL= "!=";
	
	protected Logger logger = LoggerFactory.getLogger(AbstractTypeParser.class);
	
	public String getBooleanVariableName() {
		return booleanVariableName;
	}

	protected void setBooleanVariableName(String booleanVariableName) {
		this.booleanVariableName = booleanVariableName;
	}

	protected String genBooleanVariableName(String propertyName) {
		return propertyName+UUID.randomUUID().toString().replaceAll("-", "");
	}

}
