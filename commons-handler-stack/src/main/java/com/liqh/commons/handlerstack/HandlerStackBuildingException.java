package com.liqh.commons.handlerstack;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class HandlerStackBuildingException extends CommonRuntimeException {

	private static final long serialVersionUID = -7865150970765982073L;

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param params
	 *            需要显示的相关参数
	 */
	public HandlerStackBuildingException(String code, Object... params) {
		super(code, params);
	}

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param t
	 *            异常基类
	 * @param params
	 *            需要显示的相关参数
	 */
	public HandlerStackBuildingException(String code, Throwable ex,
			Object... params) {
		super(code, ex, params);
	}

}
