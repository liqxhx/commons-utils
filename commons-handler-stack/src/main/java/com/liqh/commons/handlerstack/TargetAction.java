package com.liqh.commons.handlerstack;

/**
 * 类描述：处理链栈底的操作
 * <p>
 * 
 * @author
 * @version 1.0 2012-4-20
 * @see
 */
public interface TargetAction {
	/**
	 * 
	 * 执行处理链最后操作
	 * <p>
	 * 
	 * @param input
	 *            请求参数
	 * @return 目标动作的执行结果，类型为Object
	 */
	Object execute(Object input);

}