package com.liqh.commons.datatransform.config;

import java.util.List;
import java.util.Locale;

import com.liqh.commons.datatransform.DataTransformException;



public class DataTransformConfigParseException extends DataTransformException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 551907571171215229L;

	public DataTransformConfigParseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataTransformConfigParseException(String code, Object... params) {
		super(code, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformConfigParseException(String code, Throwable e,
			List<Locale> locales, Object... params) {
		super(code, e, locales, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformConfigParseException(String code, Throwable e,
			Object... params) {
		super(code, e, params);
		// TODO Auto-generated constructor stub
	}

	public DataTransformConfigParseException(Throwable e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	

}
