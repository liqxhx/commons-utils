package com.liqh.commons.datatransform.datavalidate.text;

import com.liqh.commons.datatransform.datavalidate.AbstractDataType;

/**
 * 
 *
 * <pre>
 *  文本数据类型
 * eg.
 * <value node-name="TXN_DT" field="txn_dt" datatype="TextType" type="java.lang.String" fix-length="8" is-null="false" pattern="" />
 * 
 *  
 *  @author qhlee
 *  @versioin v1.0 2015年8月17日
 * @see
 */
public class TextType extends AbstractDataType {
	public static final String type = "text";

	/*
	 * 字符串固定长度
	 */
	private String fixLength;

	/*
	 * 字符串最小长度
	 */
	private String minLength;

	/*
	 * 字符串最大长度
	 */
	private String maxLength;

	/*
	 * 字符串取值范围
	 */
	private String scope;

	/*
	 * 字符串模式
	 */
	private String pattern;

	public String getFixLength() {
		return fixLength;
	}

	public void setFixLength(String fixLength) {
		this.fixLength = fixLength;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

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
