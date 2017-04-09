package com.liqh.commons.fieldvalidator.handler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Pattern;

import com.liqh.commons.fieldvalidator.FieldValidateException;
import com.liqh.commons.fieldvalidator.anno.Regex;

public class RegexHandler implements IFieldHandler {

	public void doValidate(Object validateObj, Field field) throws FieldValidateException {
		if (field.isAnnotationPresent(Regex.class)) {
			Object value = FieldHandlerHelper.getFieldValue(validateObj, field.getName());
			if (value == null || "".equals(value.toString().trim())) {
				return;
			}

			Regex anno = field.getAnnotation(Regex.class);

			String[] ptnArr = anno.pattern();
			if (ptnArr == null || ptnArr.length == 0) {
				return;
			}
			for (String ptnStr : ptnArr) {
				Pattern pattern = Pattern.compile(ptnStr);
				if (pattern.matcher(value.toString()).matches()) {
					return;
				}
			}

			throw new FieldValidateException("FV0003", field.getName()+anno.fieldName(), Arrays.toString(ptnArr));
		}
	}
}
