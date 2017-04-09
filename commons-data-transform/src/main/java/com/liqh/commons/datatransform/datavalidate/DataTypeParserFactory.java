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
import com.liqh.commons.datatransform.datavalidate.datetime.DatetimeTypeParser;
import com.liqh.commons.datatransform.datavalidate.numeric.NumericType;
import com.liqh.commons.datatransform.datavalidate.numeric.NumericTypeParser;
import com.liqh.commons.datatransform.datavalidate.text.TextType;
import com.liqh.commons.datatransform.datavalidate.text.TextTypeParser;

public class DataTypeParserFactory {
	private static Map<String, DataTypeParser> dataTypeParserMap = new HashMap<String, DataTypeParser>();
	static String suffix = "Parser";
	static{
		dataTypeParserMap.put(DatetimeType.class.getSimpleName()+suffix, 	new DatetimeTypeParser());
		dataTypeParserMap.put(NumericType.class.getSimpleName()+suffix, 	new NumericTypeParser());
		dataTypeParserMap.put(TextType.class.getSimpleName()+suffix, 		new TextTypeParser());
	}
	public static DataTypeParser getDataTypeParser(String type){
		return dataTypeParserMap.get(type);
	}
}
