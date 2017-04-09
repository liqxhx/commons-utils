package com.liqh.commons.fieldvalidator.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 校验字段取值是否在某些范围中
 * @author qhlee
 * @versioin v1.0 2016年6月2日
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueIn {
	String fieldName();
	
	String[] value();
	
	String[]  valueDesc();
}
