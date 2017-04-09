/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.datavalidate;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.datavalidate.datetime.DatetimeType;
import com.liqh.commons.datatransform.datavalidate.datetime.DatetimeTypeValidator;
import com.liqh.commons.datatransform.datavalidate.numeric.NumericType;
import com.liqh.commons.datatransform.datavalidate.numeric.NumericTypeValidator;
import com.liqh.commons.datatransform.datavalidate.text.TextType;
import com.liqh.commons.datatransform.datavalidate.text.TextTypeValidator;

public class DataValidatorFactory {
	private static Map<String, DataValidator> dataTypeParserMap = new HashMap<String, DataValidator>();
	static String suffix = "Validator";
	static{
		dataTypeParserMap.put(DatetimeType.class.getSimpleName()+suffix, 	new DatetimeTypeValidator());
		dataTypeParserMap.put(NumericType.class.getSimpleName()+suffix, 	new NumericTypeValidator());
		dataTypeParserMap.put(TextType.class.getSimpleName()+suffix, 		new TextTypeValidator());
	}
	
	public static DataValidator getDataValidator(String type) {
		return dataTypeParserMap.get(type);
	}
}
