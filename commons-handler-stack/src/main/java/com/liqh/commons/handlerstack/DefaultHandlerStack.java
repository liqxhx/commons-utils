package com.liqh.commons.handlerstack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认处理链实现类
 * 
 * <pre>
 *
 * @author qhlee
 * @versioin v1.0 2016年7月22日
 * @see
 */
public class DefaultHandlerStack implements HandlerStack {

	/** 处理链ID */
	private String stackId;

	/** 处理器列表 */
	private List<Handler> handlerList = new ArrayList<Handler>();

	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerStack.class);
	public static final String log001 = "调用处理链的目标操作={0}";

	public DefaultHandlerStack(String stackId) {
		this.stackId = stackId;
	}

	/**
	 * 
	 * 按顺序执行处理链中的每个Handler，并在最后执行目标动作
	 * <p>
	 * 
	 * @param input
	 *            处理链执行的输入参数
	 * @param action
	 *            处理链执行完毕后的最终目标处理动作实例
	 * @return 返回TargetAction实例执行的结果值
	 */
	@Override
	public Object doStack(Object input, TargetAction action) {
		StackInvocation invocation = this.prepareStackInvocation(action);
		if (invocation == null) {
			// 处理链为空，无法执行
			throw new HandlerStackExeption("XTLF301100AG", this.stackId);
		}
		return invocation.invoke(input);
	}

	/**
	 * 
	 * 准备处理链执行器
	 * <p>
	 * 
	 * @param action
	 *            处理链执行完前置服务链后调用的操作
	 * @return 处理链执行器对象
	 */
	protected StackInvocation prepareStackInvocation(final TargetAction action) {
		return new StackInvocation() {
			final Iterator<Handler> handlerIterator = handlerList.iterator();

			@Override
			public Object invoke(Object input) {
				if (handlerIterator.hasNext()) {
					return handlerIterator.next().handle(input, this);
				} else if (action != null) {
					logger.debug(log001, action.getClass().getName());
					return action.execute(input);
				}
				return null;
			}
		};
	}

	/**
	 * 向处理链中添加处理器
	 * <p>
	 * 
	 * @param handler
	 *            要添加到处理链中处理器
	 * 
	 */
	@Override
	public void addHandler(Handler handler) {
		this.handlerList.add(handler);
	}

	@Override
	public String getStackId() {
		return stackId;
	}

	public void setStackId(String stackId) {
		this.stackId = stackId;
	}

	public void setHandlerList(List<Handler> handlerList) {
		this.handlerList.addAll(handlerList);
	}

	public List<Handler> getHandlerList() {
		return Collections.unmodifiableList(handlerList);
	}

}
