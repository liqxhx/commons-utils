package com.liqh.commons.datatransform.utils;

import static java.util.Locale.ENGLISH;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.liqh.commons.datatransform.config.DataTransformConfigParseException;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.utils.StringUtils;

public class Utils {

	public static final String[] EXPR_STARTS = { "${", "%{", "#{" };
	public static final String EXPR_END = "}";

	public static String fillCharsToStringLeft(String s, char c, int length) {
		return fillCharsToString(s, c, length, "LEFT");
	}

	public static String fillCharsToStringRight(String s, char c, int length) {
		return fillCharsToString(s, c, length, "RIGHT");
	}

	private static String fillCharsToString(String s, char c, int length,
			String flag) {
		if (s == null) {
			s = "";
		}

		if (s.length() >= length) {
			return s;
		}

		int fillLength = length - s.length();
		char[] fillChars = new char[fillLength];
		for (int i = 0; i < fillLength; i++) {
			fillChars[i] = c;
		}

		String fillString = new String(fillChars);
		if ("LEFT".equalsIgnoreCase(flag)) {
			return fillString + s;
		} else if ("RIGHT".equalsIgnoreCase(flag)) {
			return s + fillString;
		} else {
			return s;
		}
	}

	public static Method toSetterMethod(Class<?> clazz, String property) {

		// Map和Collection类型不生成set方法
		if (Map.class.isAssignableFrom(clazz) || Collection.class.isAssignableFrom(clazz)) {
			return null;
		}

		try {
			return new PropertyDescriptor(property, clazz).getWriteMethod();
		} catch (IntrospectionException e) {
			//无法获取指定类型的属性，类型={0}，属性={1}
			throw new DataTransformConfigParseException(DTResCode.EDT0040, e, clazz.getName(), property);
		}
	}

	public static Method toGetterMethod(Class<?> clazz, String property) {

		// Map和Collection类型不生成get方法
		if (Map.class.isAssignableFrom(clazz)
				|| Collection.class.isAssignableFrom(clazz)) {
			return null;
		}

		try {
			return new PropertyDescriptor(property, clazz).getReadMethod();
		} catch (IntrospectionException e) {
			//无法获取指定类型的属性，类型={0}，属性={1}
			throw new DataTransformConfigParseException(DTResCode.EDT0040, e, clazz.getName(), property);
		}
	}

	public static String capitalize(String name) {
		if (StringUtils.isBlank(name)) {
			return name;
		}
		return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
	}

	public static Object invokeMethod(Object obj, Method method, Object... args) {
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			String argsString = null;
			if (args != null) {
				StringBuilder sbArgs = new StringBuilder(args.getClass()
						.getName()).append(" {");
				if (args.length > 0) {
					for (Object arg : args) {
						sbArgs.append(arg).append(',');
					}
					// 删除最后一个','
					sbArgs.deleteCharAt(sbArgs.length() - 1);
				}
				argsString = sbArgs.append('}').toString();
			}
			//调用对象方法失败，对象类型={0}，方法={1}，调用参数={2}
			throw new DataTransformConfigParseException(DTResCode.EDT0041, e, 
					obj != null ? obj.getClass().getName() : null,
					method != null ? method.getName() : null, argsString);
		}
	}

	public static Object getInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			//无法创建类型实例，类型={0}
			throw new DataTransformConfigParseException(DTResCode.EDT0042, e , clazz != null ? clazz.getName() : null);
		}
	}

	public static boolean isExpression(String plainExpr) {
		if (StringUtils.isBlank(plainExpr)) {
			return false;
		}

		int start = -1;
		int end = -1;
		if ((end = plainExpr.lastIndexOf(EXPR_END)) != -1) {
			for (String expStart : EXPR_STARTS) {
				if ((start = plainExpr.indexOf(expStart)) != -1 && start < end) {
					return true;
				}
			}
		}

		return false;
	}

	public static String parseExpression(String plainExpr) {
		if (StringUtils.isBlank(plainExpr)) {
			return plainExpr;
		}

		int start = -1;
		int end = -1;
		if ((end = plainExpr.lastIndexOf(EXPR_END)) != -1) {
			for (String expStart : EXPR_STARTS) {
				if ((start = plainExpr.indexOf(expStart)) != -1 && start < end) {
					return plainExpr.substring(start + expStart.length(), end);
				}
			}
		}

		return plainExpr;
	}

}
