package com.liqh.commons.datatransform.datavalidate.datetime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.datavalidate.AbstractDataValidator;
import com.liqh.commons.datatransform.datavalidate.DataType;
import com.liqh.commons.datatransform.xml2b.DataValidatorThreadLocal;

public class DatetimeTypeValidator extends AbstractDataValidator {

	private static final Logger logger = LoggerFactory.getLogger(DatetimeTypeValidator.class);
	
	/*
	 * 时间日期模式
	 */
	private static final String PATTERN="pattern";
	
	
	
	/* (non-Javadoc)
	 * @see com.ccb.openframework.datatransform.datavalidate.DataValidator#doValidate(java.lang.String, com.ccb.openframework.datatransform.datavalidate.DataType)
	 */
	@Override
	public boolean doValidate(String value, DataType dataType) {
		// TODO Auto-generated method stub

		//检查模式匹配
		String pattern =((DatetimeType)dataType).getPattern();
		if(pattern!=null){
			if(!checkPattern(value, pattern)){
				setFailRule(pattern, PATTERN, dataType);
				return false;
			}
		}

		String isNull = ((DatetimeType)dataType).getIsNull();
		if(null != isNull){
			if("false".equalsIgnoreCase(isNull.trim())){
				logger.debug("非空校验的节点名称:{}", ((DatetimeType)dataType).getNodeName());

				int validateNumber = DataValidatorThreadLocal.get();
				validateNumber++;
				DataValidatorThreadLocal.put(validateNumber);
			}
		}
		
		return true;
	}
	

	/**
	 * 检查匹配正则表达式
	 * <p>
	 * 
	 * @param value
	 * @param regex
	 * @param flag
	 * @return
	 */
	private boolean checkPattern(String value, String regex) {
		// compile函数中有个标记字段flag,现在没有使用,考虑使用
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		return m.matches();
	}
	

}
