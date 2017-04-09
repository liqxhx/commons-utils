package com.liqh.commons.datatransform.datavalidate.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.datavalidate.AbstractDataValidator;
import com.liqh.commons.datatransform.datavalidate.DataType;
import com.liqh.commons.datatransform.xml2b.DataValidatorThreadLocal;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class TextTypeValidator extends AbstractDataValidator {
	private static final Logger logger = LoggerFactory.getLogger(TextTypeValidator.class);


	private static final String FIX_LENGTH = "fix-length";

	private static final String MIN_LENGTH = "min-length";

	private static final String MAX_LENGTH = "max-length";

	private static final String PATTERN = "pattern";

	private static final String SCOPE = "scope";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.openframework.datatransform.datavalidate.DataValidator#doValidate
	 * (java.lang.String)
	 */
	@Override
	public boolean doValidate(String value, DataType dataType) {
		// TODO Auto-generated method stub

		try {
			// 判断固定长度
			String fixLength = ((TextType) dataType).getFixLength();
			if (fixLength != null) {
				if (!checkFixLength(value, fixLength)) {
					// 设置失败规则
					setFailRule(fixLength, FIX_LENGTH, dataType);
					return false;
				}
			}
			// 检查最小长度
			String minLength = ((TextType) dataType).getMinLength();
			if (minLength != null) {
				if (!checkMinLength(value, minLength)) {
					setFailRule(minLength, MIN_LENGTH, dataType);
					return false;
				}
			}
			// 检查最大长度
			String maxLength = ((TextType) dataType).getMaxLength();
			if (maxLength != null) {
				if (!checkMaxLength(value, maxLength)) {
					setFailRule(maxLength, MAX_LENGTH, dataType);
					return false;
				}
			}

			// 检查范围
			String scope = ((TextType) dataType).getScope();
			if (scope != null) {
				if (!checkScope(value, scope)) {
					setFailRule(scope, SCOPE, dataType);
					return false;
				}
			}

			// 检查模式匹配
			String pattern = ((TextType) dataType).getPattern();
			if (pattern != null) {
				if (!checkPattern(value, pattern)) {
					setFailRule(pattern, PATTERN, dataType);
					return false;
				}
			}

			String isNull = ((TextType) dataType).getIsNull();
			if (null != isNull) {
				if ("false".equalsIgnoreCase(isNull.trim())) {
					logger.debug("非空校验的节点名称:{0}", ((TextType) dataType).getNodeName());
					
					int validateNumber = DataValidatorThreadLocal.get();
					validateNumber++;
					DataValidatorThreadLocal.put(validateNumber);
				}
			}

		} catch (NumberFormatException nfe) {
			logger.error("报文校验配置错误，请检查报文校验规则配置", nfe);
			throw new CommonRuntimeException("EDT0011");
		}

		return true;
	}

	/**
	 * 
	 * 检查文本是否为固定长度
	 * <p>
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkFixLength(String value, String fixLength) {
		// TODO:需要判空
		return (Integer.parseInt(fixLength) == value.getBytes().length);
	}

	/**
	 * 检查是否大于等于最小长度值
	 * <p>
	 * 
	 * @param value
	 * @param minLength
	 * @return
	 */
	private boolean checkMinLength(String value, String minLength) {
		return (Integer.parseInt(minLength) <= value.getBytes().length);
	}

	/**
	 * 检查是否小于等于最大长度值
	 * <p>
	 * 
	 * @param value
	 * @param maxLength
	 * @return
	 */
	private boolean checkMaxLength(String value, String maxLength) {
		return (Integer.parseInt(maxLength) >= value.getBytes().length);
	}

	/**
	 * 测试报文中字段值是否在指定的域(值的集合)中
	 * <p>
	 * 
	 * @param value
	 * @param scope
	 * @return
	 */
	private boolean checkScope(String value, String scope) {
		String[] s = scope.split(",");
		int length = s.length;
		for (int i = 0; i < length; i++) {
			if (value.equals(s[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查匹配正则表达式
	 * <p>
	 * 
	 * @param value
	 * @param regex
	 * @param flag
	 * @return
	 */
	private boolean checkPattern(String value, String regex) {
		// compile函数中有个标记字段flag,现在没有使用,考虑使用
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		return m.matches();
	}

}
