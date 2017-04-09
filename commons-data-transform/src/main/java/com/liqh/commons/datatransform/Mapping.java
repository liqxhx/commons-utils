package com.liqh.commons.datatransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * 数据转换映射规则
 * 
 * @author qhlee
 * @versioin v1.0 2016年6月14日
 * @see
 */
public class Mapping<R extends Rule> {

	/**
	 * 转换规则Id
	 */
	private String id;

	/**
	 * 对应的转换器
	 */
	private DataTransformer<?, ?, R> transformer;

	/**
	 * 规则集
	 */
	private List<R> rules = new ArrayList<R>();

	/**
	 * 校验标识
	 */
	private boolean validate;


	private int expectedNumber;
	
	public Mapping(String id, DataTransformer<?, ?, R> transformer,boolean validate, int expectedNumber) {
		this.id = id;
		this.transformer = transformer;
		this.validate = validate;
		this.expectedNumber = expectedNumber;
	}
	
	public int getExpectedNumber() {
		return expectedNumber;
	}
	
	public void setExpectedNumber(int expectedNumber) {
		this.expectedNumber = expectedNumber;
	}
	
	/**
	 * @return the validate
	 */
	public boolean isValidate() {
		return validate;
	}

	/**
	 * @param validate the validate to set
	 */
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getId() {
		return id;
	}

	public DataTransformer<?, ?, R> getTransformer() {
		return transformer;
	}

	public List<R> getRules() {
		return rules;
	}

	public void setRules(List<R> rules) {
		this.rules = rules;
	}
}
