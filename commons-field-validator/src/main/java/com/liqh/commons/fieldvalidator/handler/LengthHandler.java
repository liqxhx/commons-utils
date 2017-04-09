package com.liqh.commons.fieldvalidator.handler;

import java.lang.reflect.Field;

import com.liqh.commons.fieldvalidator.FieldValidateException;
import com.liqh.commons.fieldvalidator.anno.Length;

public class LengthHandler implements IFieldHandler {

	public void doValidate(Object validateObj, Field field)
			throws FieldValidateException {
		if (field.isAnnotationPresent(Length.class)) {
			String value = (String)FieldHandlerHelper.getFieldValue(validateObj, field.getName());
			if (value != null && !"".equals(value.trim())) {
				Length anno = field.getAnnotation(Length.class);

				int max = anno.max();
				int min = anno.min();
				
				int len = value.trim().length();
				System.out.println(String.format("%d %d %d", len, min, max));
				if (len > max || len < min) {
					throw new FieldValidateException("FV0002", field.getName()+anno.fieldName(), min, max);
				}
			}
		}
	}

}
