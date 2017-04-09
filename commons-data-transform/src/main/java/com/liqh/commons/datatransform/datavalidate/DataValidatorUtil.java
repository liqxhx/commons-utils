/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.datavalidate;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.constant.DTResCode;

public class DataValidatorUtil {
	public DataValidatorUtil() {
		loadErrCode();
	}
	
	private static final String numPrefix="num-";
	private static final String textPrefix="text-";
	private static final String datetimePrefix="datetime-";
	
	private static final String FIX_LENGTH="fix-length";	
	private static final String MIN_LENGTH="min-length";
	private static final String MAX_LENGTH="max-length";	
	private static final String PATTERN="pattern";
	private static final String SCOPE="scope";
	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String MINL = "minL";
	private static final String MAXR = "maxR";
	private static final String DECIMAL_LENGTH = "decimal-length";
	private static final String IS_NULL = "is-null";
	
	/**
	 * 保存错误码和对应检验规则的映射关系
	 */
	private Map<String,String> errMapping= new HashMap<String,String>();
	
	/**
	 * 
	 * 加载报文校验错误码
	 * <p>
	 * 
	 */
	private void loadErrCode(){
		//数值类错误码
		errMapping.put(numPrefix+FIX_LENGTH, 				DTResCode.EDT0015);
		errMapping.put(numPrefix+DECIMAL_LENGTH, 	DTResCode.EDT0016);
		errMapping.put(numPrefix+MAX_LENGTH,			DTResCode.EDT0018);
		errMapping.put(numPrefix+MIN,							DTResCode.EDT0019);
		errMapping.put(numPrefix+MAX,							DTResCode.EDT0020);
		errMapping.put(numPrefix+MINL,							DTResCode.EDT0021);
		errMapping.put(numPrefix+MAXR,							DTResCode.EDT0022);
		errMapping.put(numPrefix+SCOPE,						DTResCode.EDT0023);
		errMapping.put(numPrefix+IS_NULL,					DTResCode.EDT0024);
		
		//文本类错误码
		errMapping.put(textPrefix+FIX_LENGTH,			DTResCode.EDT0025);
		errMapping.put(textPrefix+MIN_LENGTH,			DTResCode.EDT0026);
		errMapping.put(textPrefix+MAX_LENGTH,			DTResCode.EDT0027);
		errMapping.put(textPrefix+PATTERN,					DTResCode.EDT0028);
		errMapping.put(textPrefix+SCOPE,						DTResCode.EDT0029);
		errMapping.put(textPrefix+IS_NULL,					DTResCode.EDT0030);
		
		//时间日期类错误码
		errMapping.put(datetimePrefix+PATTERN,			DTResCode.EDT0031);
		errMapping.put(datetimePrefix+IS_NULL,			DTResCode.EDT0032);
		
	}

	/**
	 * 获取错误码
	 * <p>
	 * 
	 * @param key  
	 *        报文校验中拼接的key
	 * @return
	 *        错误码
	 */	
	public String getErrCode(String key){
		return errMapping.get(key);
	}
	
	
	
}
