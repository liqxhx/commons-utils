/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.enummap;

import java.util.List;
import java.util.Locale;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class EnumMappingException extends CommonRuntimeException {

	/** */
	private static final long serialVersionUID = 7305044311617896766L;

	public EnumMappingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnumMappingException(String code, Object... params) {
		super(code, params);
		// TODO Auto-generated constructor stub
	}

	public EnumMappingException(String code, Throwable e, List<Locale> locales,
			Object... params) {
		super(code, e, locales, params);
		// TODO Auto-generated constructor stub
	}

	public EnumMappingException(String code, Throwable e, Object... params) {
		super(code, e, params);
		// TODO Auto-generated constructor stub
	}

	public EnumMappingException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
