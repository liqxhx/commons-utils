package com.liqh.commons.datatransform;

import java.util.List;
import java.util.Locale;

import com.liqh.commons.lang.model.ex.CommonRuntimeException;



public class DataTransformException extends CommonRuntimeException {

	/** */
	private static final long serialVersionUID = -3165023259422282233L;

	public DataTransformException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataTransformException(String code, Object... params) {
		super(code, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformException(String code, Throwable e,
			List<Locale> locales, Object... params) {
		super(code, e, locales, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformException(String code, Throwable e, Object... params) {
		super(code, e, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
	
}
