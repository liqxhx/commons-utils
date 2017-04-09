package com.liqh.commons.fieldvalidator.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 *
 * <pre>
 * 如果在一个类上标注了NeedValidate表示该类可以使用本FieldValidator进行校验
 * @author qhlee
 * @versioin v1.0 2016年6月2日
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NeedValidate {

}
