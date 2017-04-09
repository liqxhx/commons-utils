package com.liqh.commons.handlerstack;

/**
 * 类描述：处理链执行器，通过调用invoke开始执行处理链
 * <p>
 * 
 * @author
 * @version 1.0 2012-4-20
 * @see
 */
public interface StackInvocation {

	/**
	 * 调用处理器执行操作
	 * <p>
	 * 
	 * @param context
	 *            上下文内容，一般为数据交换区
	 * @return 返回执行结果，类型为Object
	 */
	Object invoke(Object context);

}
