/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.lang.model.ex;


/**
 * <pre>
 * 统一异常的补充接口，实现异常对代码、参数的支持能力。
 * @author qhlee
 * @versioin v1.0 2016年11月7日
 * @see
 */
public interface IndexedMessage {
	/**
	 * 获取异常代码
	 * @return 异常代码
	 */
	String getCode();

	/**
	 *  获取异常参数
	 * @return 异常参数
	 */
	Object[] getParameters();
}