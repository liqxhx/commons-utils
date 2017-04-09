package com.liqh.commons.datatransform.datavalidate;

/**
 * 
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public abstract class AbstractDataType implements DataType {
	/*
	 * 校验时用于保存失败的规则
	 */
	private String failRule;

	/*
	 * XML报文标签名
	 */
	private String nodeName;

	/*
	 * 是否为空
	 */
	private String isNull;
	
	/**
	 * @return the failRule
	 */
	public String getFailRule() {
		return failRule;
	}

	/**
	 * @param failRule the failRule to set
	 */
	public void setFailRule(String failRule) {
		this.failRule = failRule;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the isNull
	 */
	public String getIsNull() {
		return isNull;
	}

	/**
	 * @param isNull the isNull to set
	 */
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	
	
}
