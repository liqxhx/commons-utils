package com.liqh.commons.fieldvalidator.handler;

import java.lang.reflect.Field;

import com.liqh.commons.fieldvalidator.FieldValidateException;

/**
 * <pre>
 * 具体的校验者
 * @author qhlee
 * @versioin v1.0 2016年6月2日
 * @see
 */
public interface IFieldHandler {
	/**
	 * <pre>
	 * @param validateObj 要校验的对象
	 * @param field 要校验的字段
	 * @throws FieldValidateException
	 */
	public void doValidate(Object validateObj, Field field)
			throws FieldValidateException;
}
