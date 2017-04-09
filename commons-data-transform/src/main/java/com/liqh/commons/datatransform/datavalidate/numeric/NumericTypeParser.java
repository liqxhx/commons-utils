package com.liqh.commons.datatransform.datavalidate.numeric;

import org.w3c.dom.Node;

import com.liqh.commons.datatransform.datavalidate.DataTypeParser;
import com.liqh.commons.datatransform.xml2b.ValueType;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * 数值类型解析器
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public class NumericTypeParser implements DataTypeParser {

	/*
	 * XML报文标签名
	 */
	private static final String NODE_NAME_ATTR = "node-name";
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
	/*
	 * 是否为空
	 */
	private static final String IS_NULL = "is-null";

	/*
	 * 数值取值范围
	 */
	private static final String SCOPE = "scope";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.openframework.datatransform.datavalidate.config.DataTypeParser
	 * #parseType(org.w3c.dom.Node,
	 * com.ccb.openframework.datatransform.xml2b.ValueType)
	 */
	@Override
	public void parseType(Node node, ValueType valueType) {
		// TODO Auto-generated method stub
		NumericType numericType = new NumericType();
		String nodeName = XMLUtils.getNodeAttributeValue(node, NODE_NAME_ATTR);
		String length = XMLUtils.getNodeAttributeValue(node, FIX_LENGTH);
		String decimalLength = XMLUtils.getNodeAttributeValue(node, DECIMAL_LENGTH);
		String maxLength = XMLUtils.getNodeAttributeValue(node, MAX_LENGTH);
		String min = XMLUtils.getNodeAttributeValue(node, MIN);
		String max = XMLUtils.getNodeAttributeValue(node, MAX);
		String minL = XMLUtils.getNodeAttributeValue(node, MINL);
		String maxR = XMLUtils.getNodeAttributeValue(node, MAXR);
		String isNull = XMLUtils.getNodeAttributeValue(node, IS_NULL);
		String scope = XMLUtils.getNodeAttributeValue(node, SCOPE);

		numericType.setLength(length);
		numericType.setDecimalLength(decimalLength);
		numericType.setMaxLength(maxLength);
		numericType.setMin(min);
		numericType.setMax(max);
		numericType.setMinL(minL);
		numericType.setMaxR(maxR);
		numericType.setNodeName(nodeName);
		numericType.setIsNull(isNull);
		numericType.setScope(scope);

		valueType.setDataType(numericType);

	}

}
