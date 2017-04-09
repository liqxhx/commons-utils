package com.liqh.commons.datatransform.datavalidate.numeric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.datavalidate.AbstractDataValidator;
import com.liqh.commons.datatransform.datavalidate.DataType;
import com.liqh.commons.datatransform.xml2b.DataValidatorThreadLocal;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class NumericTypeValidator extends AbstractDataValidator {

	private static final Logger logger = LoggerFactory.getLogger(NumericTypeValidator.class);


	/*
	 * 数值长度
	 */
	private static final String FIX_LENGTH = "fix-length";
	/*
	 * 小数点后位数
	 */
	private static final String DECIMAL_LENGTH = "decimal-length";
	/*
	 * 数字字符最大长度
	 */
	private static final String MAX_LENGTH = "max-length";
	/*
	 * 大于等于最小值
	 */
	private static final String MIN = "min";
	/*
	 * 小于等于最大值
	 */
	private static final String MAX = "max";
	/*
	 * 大于最小值
	 */
	private static final String MINL = "minL";
	/*
	 * 小于最大值
	 */
	private static final String MAXR = "maxR";

	private static final String SCOPE = "scope";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.openframework.datatransform.datavalidate.DataValidator#doValidate
	 * (java.lang.String,
	 * com.ccb.openframework.datatransform.datavalidate.DataType)
	 */
	@Override
	public boolean doValidate(String value, DataType dataType) {
		// TODO Auto-generated method stub
		try {
			// 判断固定长度
			String fixLength = ((NumericType) dataType).getLength();
			if (fixLength != null) {
				if (!checkLength(value, fixLength)) {
					// 设置失败规则
					setFailRule(fixLength, FIX_LENGTH, dataType);
					return false;
				}
			}
			// 检查小数点后位数
			String decimalLength = ((NumericType) dataType).getDecimalLength();
			if (decimalLength != null) {
				if (!checkDecimalLength(value, decimalLength)) {

					setFailRule(decimalLength, DECIMAL_LENGTH, dataType);
					return false;
				}
			}
			// 判断最大长度
			String maxLength = ((NumericType) dataType).getMaxLength();
			if (maxLength != null) {
				if (!checkMaxLength(value, maxLength)) {
					setFailRule(maxLength, MAX_LENGTH, dataType);
					return false;
				}
			}
			// 检查min
			String min = ((NumericType) dataType).getMin();
			if (min != null) {
				if (!checkMin(value, min)) {
					setFailRule(min, MIN, dataType);
					return false;
				}
			}
			// 检查max
			String max = ((NumericType) dataType).getMax();
			if (max != null) {
				if (!checkMax(value, max)) {
					setFailRule(max, MAX, dataType);
					return false;
				}
			}
			// 检查minL
			String minL = ((NumericType) dataType).getMinL();
			if (minL != null) {
				if (!checkMinL(value, minL)) {
					setFailRule(minL, MINL, dataType);
					return false;
				}
			}
			// 检查maxR
			String maxR = ((NumericType) dataType).getMaxR();
			if (maxR != null) {
				if (!checkMaxR(value, maxR)) {
					setFailRule(maxR, MAXR, dataType);
					return false;
				}
			}
			// 检查数值范围
			String scope = ((NumericType) dataType).getScope();
			if (scope != null) {
				if (!checkScope(value, scope)) {
					setFailRule(scope, SCOPE, dataType);
					return false;
				}
			}

			String isNull = ((NumericType) dataType).getIsNull();
			if (null != isNull) {
				if ("false".equalsIgnoreCase(isNull.trim())) {
					String nodeName = ((NumericType) dataType).getNodeName();
					logger.debug("非空校验的节点名称:{}", nodeName);
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
	 * 检查数字长度
	 * <p>
	 * 
	 * @param value
	 * @param fixLength
	 * @return
	 */
	private boolean checkLength(String value, String fixLength) {

		return (Integer.parseInt(fixLength) == value.getBytes().length);

	}

	/**
	 * 检查小数点后的位数是否等于给定的位数
	 * <p>
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	private boolean checkDecimalLength(String value, String length) {

		String[] s = value.trim().split("\\.");
		if (s.length == 1) {
			return false;
		}
		return (Integer.parseInt(length) == s[1].getBytes().length);
	}

	/**
	 * 检查数值字符的长度是否小于等于最大长度
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
	 * 检查最小值
	 * <p>
	 * 
	 * @param value
	 * @param min
	 * @return
	 */
	private boolean checkMin(String value, String min) {
		// 是不是需要区分整数和浮点数,在判断等于的时候，可能又问题吧？
		return (Double.parseDouble(value) >= Double.parseDouble(min));
	}

	/**
	 * 检查是否小于等于最大值
	 * <p>
	 * 
	 * @param value
	 * @param max
	 * @return
	 */
	private boolean checkMax(String value, String max) {
		// 是不是需要区分整数和浮点数,在判断等于的时候，可能又问题吧？
		return (Double.parseDouble(value) <= Double.parseDouble(max));
	}

	/**
	 * 检查是否大于最小值
	 * <p>
	 * 
	 * @param value
	 * @param minL
	 * @return
	 */
	private boolean checkMinL(String value, String minL) {
		// 是不是需要区分整数和浮点数,在判断等于的时候，可能又问题吧？
		return (Double.parseDouble(value) > Double.parseDouble(minL));
	}

	private boolean checkMaxR(String value, String maxR) {
		// 是不是需要区分整数和浮点数,在判断等于的时候，可能又问题吧？
		return (Double.parseDouble(value) < Double.parseDouble(maxR));
	}

	/**
	 * 检查数值是否在指定的数值集合中
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
}
