/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.lang.model;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;

/**
 * 生命周期
 * @author liqh
 *
 */
public interface ILifeCycle {
	/**
	 * ping 用来测试某个服务是否处于运行状态
	 * @throws Exception
	 */
	public abstract Object ping(Object req) throws CommonRuntimeException;

	/**
	 * 生成周期方法，服务创建或初始化
	 * @throws Exception
	 */
	public abstract void create() throws CommonRuntimeException;

	/**
	 * 生成周期方法，服务启动
	 * @throws Exception
	 */
	public abstract void start() throws CommonRuntimeException;

	/**
	 * 生成周期方法，服务停止
	 * @throws Exception
	 */
	public abstract void stop() throws CommonRuntimeException;

	/**
	 * 生成周期方法，服务销毁，资源回收
	 * @throws Exception
	 */
	public abstract void destroy() throws CommonRuntimeException;



}
