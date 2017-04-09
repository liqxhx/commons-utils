package com.liqh.commons.fieldvalidator.handler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.liqh.commons.fieldvalidator.FieldValidateException;
import com.liqh.commons.fieldvalidator.anno.ValueIn;

public class ValueInHandler implements IFieldHandler {
	protected static final String MSG = "{0}取值不合法，可选的取值有{1}";

	public void doValidate(Object validateObj, Field field)
			throws FieldValidateException {
		if (field.isAnnotationPresent(ValueIn.class)) {
			String value = (String) FieldHandlerHelper.getFieldValue(
					validateObj, field.getName());
			if (value == null || "".equals(value.trim()))
				return;

			ValueIn anno = field.getAnnotation(ValueIn.class);

			String[] expectValue = anno.value();

			List<String> list = Arrays.asList(expectValue);
			if (!list.contains(value)) {
				throw new FieldValidateException("FV0005", field.getName()+anno.fieldName(), Arrays.toString(anno.valueDesc()));
			}
		}
	}

}
