/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.lang.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18n {
	static final Logger logger = LoggerFactory.getLogger(I18n.class);
	// private static Map<String,ResourceBundle> rbs = new Hashtable<String,
	// ResourceBundle>();
	static Map<Locale, Map<String, String>> locales2Codes = new Hashtable<Locale, Map<String, String>>();
	
	static Locale[] locales;
	static String[] properties;
	public static void init(){
		init(locales, properties);
	}
	
	public static void init(Locale[] locales, String... properties) {
		if (properties == null || properties.length == 0) {
			logger.warn("初始化国际化组件时, 未传资源配置文件!");
			return;
		}
		Locale[] localesArr = locales;
		if (localesArr == null || localesArr.length == 0) {
			localesArr = Locale.getAvailableLocales();
		}

		for (Locale locale : localesArr) {
			Map<String, String> codes = locales2Codes.get(locale);
			if (codes == null) {
				codes = new Hashtable<String, String>();
				locales2Codes.put(locale, codes);
			}
			for (String property : properties) {
				try {
					ResourceBundle rb = ResourceBundle.getBundle(property, locale);
					// rbs.put(locale.toString()+property, rb);

					Enumeration<String> keys = rb.getKeys();
					while (keys.hasMoreElements()) {
						String code = keys.nextElement();
						codes.put(code, rb.getString(code));
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		logger.debug(locales2Codes.toString());
	}

	public static String getMessage(String code, Object... params) {
		return getMessage(code, code, Locale.getDefault(), params);
	}

	public static String getMessage(String code) {
		return getMessage(code, code);
	}

	public static String getMessage(String code, String defaultMessage, Locale locale, Object... params) {
		Locale rtLocale = locale;
		if (rtLocale == null)
			rtLocale = Locale.getDefault();

		if (locales2Codes.containsKey(rtLocale)) {
			if (locales2Codes.get(rtLocale).containsKey(code)) {
				if (params != null && params.length != 0) {
					return MessageFormat.format(locales2Codes.get(rtLocale).get(code), params);
				} else {
					return locales2Codes.get(rtLocale).get(code);
				}
			}
		}
		return defaultMessage;
	}

	/*
	 * public static String getMessage(Locale locale, String resfile, String
	 * code){ return getMessage(locale, resfile, code, new Object[]{}); }
	 * 
	 * public static String getMessage(String resfile, String code){ return
	 * getMessage(Locale.getDefault(), resfile, code, new Object[]{}); }
	 * 
	 * public static String getMessage(String resfile, String code,
	 * Object...params){ return getMessage(Locale.getDefault(), resfile, code,
	 * params); }
	 * 
	 * public static String getMessage(Locale locale, String resfile, String
	 * code, Object...params) { String mkey = locale.toString()+resfile;
	 * ResourceBundle rb = rbs.get(mkey);
	 * 
	 * // if(rb == null) { // rb = ResourceBundle.getBundle(file, locale); //
	 * rbs.put(mkey, rb); // }
	 * 
	 * if(rb != null) { if(params != null) return
	 * MessageFormat.format(rb.getString(code), params);
	 * 
	 * return rb.getString(code); }else{ return code; } }
	 */
}
