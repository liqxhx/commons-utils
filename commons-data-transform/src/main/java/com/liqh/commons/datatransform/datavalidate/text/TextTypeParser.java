package com.liqh.commons.datatransform.datavalidate.text;

import org.w3c.dom.Node;

import com.liqh.commons.datatransform.datavalidate.DataTypeParser;
import com.liqh.commons.datatransform.xml2b.ValueType;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * 文本类型解析器
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public class TextTypeParser implements DataTypeParser {

	/*
	 * XML报文标签名
	 */
	private static final String NODE_NAME_ATTR = "node-name";
	/*
	 * 字符串固定长度值
	 */
	private static final String FIX_LENGTH = "fix-length";
	/*
	 * 字符串最小长度
	 */
	private static final String MIN_LENGTH = "min-length";
	/*
	 * 字符串最大长度
	 */
	private static final String MAX_LENGTH = "max-length";
	/*
	 * 是否为空
	 */
	private static final String IS_NULL = "is-null";

	/*
	 * 字符串取值范围
	 */
	private static final String SCOPE = "scope";
	/*
	 * 字符串模式
	 */
	private static final String PATTERN = "pattern";

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
		// 解析类型中的校验规则
		TextType textType = new TextType();
		String nodeName = XMLUtils.getNodeAttributeValue(node, NODE_NAME_ATTR);
		String fixLength = XMLUtils.getNodeAttributeValue(node, FIX_LENGTH);
		String minLength = XMLUtils.getNodeAttributeValue(node, MIN_LENGTH);
		String maxLength = XMLUtils.getNodeAttributeValue(node, MAX_LENGTH);
		String isNull = XMLUtils.getNodeAttributeValue(node, IS_NULL);
		String scope = XMLUtils.getNodeAttributeValue(node, SCOPE);
		String pattern = XMLUtils.getNodeAttributeValue(node, PATTERN);

		// 设置数据类型中的内容
		textType.setFixLength(fixLength);
		textType.setMinLength(minLength);
		textType.setMaxLength(maxLength);
		textType.setIsNull(isNull);
		textType.setScope(scope);
		textType.setPattern(pattern);
		textType.setNodeName(nodeName);

		valueType.setDataType(textType);

	}

}
