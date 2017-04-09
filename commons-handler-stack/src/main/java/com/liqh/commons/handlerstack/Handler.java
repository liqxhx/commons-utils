package com.liqh.commons.handlerstack;

/**
 * 处理器接口
 * 
 * <pre>
 *
 * @author qhlee
 * @versioin v1.0 2016年7月22日
 * @see
 */
public interface Handler {

	/**
	 * 执行处理器
	 * <p>
	 * 
	 * @param input
	 *            交易请求参数
	 * @param invocation
	 *            处理链执行器
	 * @return Object 处理链处理完后返回的对象，一般为第一个处理器的后置服务所返回的对象
	 */
	Object handle(Object input, StackInvocation invocation);

}
