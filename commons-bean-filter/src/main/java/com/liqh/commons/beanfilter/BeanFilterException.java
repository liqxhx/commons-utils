/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter;

import java.util.List;
import java.util.Locale;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class BeanFilterException extends CommonRuntimeException {

	/** */
	private static final long serialVersionUID = -6165899395267931762L;

	public BeanFilterException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanFilterException(String code, Object... params) {
		super(code, params);
		// TODO Auto-generated constructor stub
	}

	public BeanFilterException(String code, Throwable e, List<Locale> locales,
			Object... params) {
		super(code, e, locales, params);
		// TODO Auto-generated constructor stub
	}

	public BeanFilterException(String code, Throwable e, Object... params) {
		super(code, e, params);
		// TODO Auto-generated constructor stub
	}

	public BeanFilterException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
