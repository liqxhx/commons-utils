package com.liqh.commons.handlerstack;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * 实现处理链的注册及调用
 * @author qhlee
 * @versioin v1.0 2016年7月5日
 * @see
 */
public class HandlerStackRegistry {

	private Map<String, HandlerStack> handlerStackRegistry = new HashMap<String, HandlerStack>();

	/**
	 * 根据stackId获取对应的处理链
	 * <p>
	 * 
	 * @param stackId
	 *            处理链Id
	 * @return 处理链
	 */
	public HandlerStack getHandlerStack(String stackId) {
		return handlerStackRegistry.get(stackId);
	}

	/**
	 * 添加处理链
	 * <p>
	 * 
	 * @param handlerStack
	 *            处理链
	 */
	public void addHandlerStack(HandlerStack handlerStack) {
		this.handlerStackRegistry.put(handlerStack.getStackId(), handlerStack);
	}

	/**
	 * 处理处理链中添加处理器
	 * <p>
	 * 
	 * @param stackId
	 *            处理链Id
	 * @param handler
	 *            处理器
	 */
	public void addToHandlerStack(String stackId, Handler handler) {
		HandlerStack stack = this.handlerStackRegistry.get(stackId);
		if (stack != null) {
			stack.addHandler(handler);
		} else {
			// throw new HandlerStackBindingException();
			throw new RuntimeException(
					"无法将处理器添加到对应的处理链内，原因处理链容器中无法找到该栈，stackId=" + stackId);
		}
	}

}
