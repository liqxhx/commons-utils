package com.liqh.commons.datatransform.xml2b;

import java.util.Stack;
/**
 * Xml to Bean转换上下文
 * <p>
 * 
 * @author qhlee
 *
 */
public class BindingContext {

	// 遍历XML报文时生成对象栈，栈顶为当前解析节点对应对象的容器
	private Stack<Object> navStack = new Stack<Object>();

	// 遍历XML报文时生成规则栈，栈顶为当前解析节点对应规则的父规则
	private Stack<TypeDefinition> ruleStack = new Stack<TypeDefinition>();

	// value结构入navStack栈标志
	private boolean valueTypePushFlag = false;

	//报文校验开关
	private boolean validateFlag=true;
	
	/**
	 * @return the validateFlag
	 */
	public boolean isValidateFlag() {
		return validateFlag;
	}

	/**
	 * @param validateFlag the validateFlag to set
	 */
	public void setValidateFlag(boolean validateFlag) {
		this.validateFlag = validateFlag;
	}

	public Stack<Object> getNavStack() {
		return navStack;
	}

	public Stack<TypeDefinition> getRuleStack() {
		return ruleStack;
	}

	public boolean isValueTypePushFlag() {
		return valueTypePushFlag;
	}

	public void setValueTypePushFlag(boolean valueTypePushFlag) {
		this.valueTypePushFlag = valueTypePushFlag;
	}

}
