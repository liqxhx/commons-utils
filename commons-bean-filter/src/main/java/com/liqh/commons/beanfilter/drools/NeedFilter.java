/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.drools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * <pre>
 * 要使用drools过滤，javabean应该用@NeedFilter(value="xxx")进行标注
 * 通过value指定一个boolean类型的字段名，这样以便drools设置是否(true/false)过滤状态
 * @author qhlee
 * @versioin v1.0 2016年11月29日
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NeedFilter {
	String value();
}
