package com.liqh.commons.fieldvalidator;

import com.liqh.commons.fieldvalidator.handler.IFieldHandler;

/**
 * 
 *
 * <pre>
 * 基于注解的字段校验
 * 非空校验
 * 长度校验
 * 取值有效性校验
 * @author qhlee
 * @versioin v1.0 2016年6月2日
 * @see
 */
public interface IFieldValidator {

	/** 
	 * @param validateObj 要校验的对象
	 * @throws FieldValidateException
	 */
	public void validate(Object validateObj) throws FieldValidateException ;
	
	public void regist(String type, IFieldHandler handler) throws FieldValidateException ;
}
