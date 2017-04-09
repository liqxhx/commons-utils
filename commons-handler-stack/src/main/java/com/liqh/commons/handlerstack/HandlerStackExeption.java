package com.liqh.commons.handlerstack;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;


public class HandlerStackExeption extends CommonRuntimeException {

	private static final long serialVersionUID = 654609553291295987L;

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param params
	 *            需要显示的相关参数
	 */
	public HandlerStackExeption(String code, Object... params) {
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
	public HandlerStackExeption(String code, Throwable ex, Object... params) {
		super(code, ex, params);
	}

}
