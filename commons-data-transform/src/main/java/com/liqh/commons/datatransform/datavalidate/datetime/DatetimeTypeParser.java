package com.liqh.commons.datatransform.datavalidate.datetime;

import org.w3c.dom.Node;

import com.liqh.commons.datatransform.datavalidate.DataTypeParser;
import com.liqh.commons.datatransform.xml2b.ValueType;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * 
 * 日期类型配置解析器
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月14日
 * @see
 */
public class DatetimeTypeParser implements DataTypeParser {

	/*
	 * XML报文标签名
	 */
	private static final String NODE_NAME_ATTR = "node-name";
	/*
	 * 字符串模式
	 */
	private static final String PATTERN = "pattern";
	/*
	 * 是否为空
	 */
	private static final String IS_NULL = "is-null";

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
		DatetimeType datetimeType = new DatetimeType();
		String nodeName = XMLUtils.getNodeAttributeValue(node, NODE_NAME_ATTR);
		String pattern = XMLUtils.getNodeAttributeValue(node, PATTERN);
		String isNull = XMLUtils.getNodeAttributeValue(node, IS_NULL);

		datetimeType.setPattern(pattern);
		datetimeType.setNodeName(nodeName);
		datetimeType.setIsNull(isNull);

		valueType.setDataType(datetimeType);

	}

}
