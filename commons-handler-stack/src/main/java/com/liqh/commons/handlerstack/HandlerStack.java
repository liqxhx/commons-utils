package com.liqh.commons.handlerstack;

/**
 * 
 * <pre>
 * 处理链组件
 * @author qhlee
 * @versioin v1.0 2016年7月5日
 * @see
 */
public interface HandlerStack {
	/**
	 * 按顺序执行处理器链中的第个Handler，并在最后执行TargetAction
	 * <P>
	 * 
	 * @param input
	 * @param action
	 * @return
	 */
	Object doStack(Object input, TargetAction action);

	/**
	 * <pre>
	 * 获取处理链Id
	 * @return
	 */
	String getStackId();

	/**
	 * <pre>
	 * 新增处理器
	 * @param handler
	 */
	void addHandler(Handler handler);
}
