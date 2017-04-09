package com.liqh.commons.fieldvalidator.handler;

import java.lang.reflect.Field;

import com.liqh.commons.fieldvalidator.FieldValidateException;
import com.liqh.commons.fieldvalidator.anno.Require;

public class RequireHandler implements IFieldHandler {

	public void doValidate(Object validateObj, Field field)
			throws FieldValidateException {
		if (field.isAnnotationPresent(Require.class)) {
			Object value = FieldHandlerHelper.getFieldValue(validateObj,
					field.getName());
			if (value == null || "".equals(value.toString().trim())) {
				Require anno = field.getAnnotation(Require.class);
				throw new FieldValidateException("FV0004", field.getName()+anno.fieldName());
			}
		}
	}

}
