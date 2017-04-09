package com.liqh.commons.datatransform.datavalidate.datetime;

import com.liqh.commons.datatransform.datavalidate.AbstractDataType;

/**
 * 时间日期类型
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public class DatetimeType extends AbstractDataType {
	public static final String type = "datetime" ;
	private String pattern ;
	
	
	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	@Override
	public String getType() {
		return type;
	}

}
