package com.liqh.commons.datatransform.datavalidate.numeric;

import com.liqh.commons.datatransform.datavalidate.AbstractDataType;
/**
 * 数值类型
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public class NumericType extends AbstractDataType {
	public static final String type = "num" ;
	
	/*
	 * 数字位数（包含小数点）
	 */
	private String length ;
	
	/*
	 * 小数点后位数
	 */
	private String decimalLength ;
	
	/*
	 * 数字字符最大长度
	 */
	private String maxLength ;
	
	/*
	 * 数值是否大于等于最小值
	 */
	private String min ;
	
	/*
	 * 数值是否小于等于最大值
	 */
	private String max ;
	
	/*
	 * 数值是否大于最小值
	 */
	private String minL ;
	
	/*
	 *数值是否小于最大值 
	 */
	private String maxR ;
	
	/*
	 * 数据最值范围
	 */
	private String scope ;
	
	/*
	 * 模式
	 */
	private String pattern ;
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(String decimalLength) {
		this.decimalLength = decimalLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMinL() {
		return minL;
	}

	public void setMinL(String minL) {
		this.minL = minL;
	}

	public String getMaxR() {
		return maxR;
	}

	public void setMaxR(String maxR) {
		this.maxR = maxR;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String getType() {
		return type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
