package com.liqh.commons.fieldvalidator;

import java.util.List;
import java.util.Locale;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class FieldValidateException extends CommonRuntimeException {

	/** */
	private static final long serialVersionUID = 1L;

	public FieldValidateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FieldValidateException(String code, Object... params) {
		super(code, params);
		// TODO Auto-generated constructor stub
	}

	public FieldValidateException(String code, Throwable e,
			List<Locale> locales, Object... params) {
		super(code, e, locales, params);
		// TODO Auto-generated constructor stub
	}

	public FieldValidateException(String code, Throwable e, Object... params) {
		super(code, e, params);
		// TODO Auto-generated constructor stub
	}

	public FieldValidateException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
