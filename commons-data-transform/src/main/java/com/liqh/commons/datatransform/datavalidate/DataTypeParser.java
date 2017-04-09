package com.liqh.commons.datatransform.datavalidate;

import org.w3c.dom.Node;

import com.liqh.commons.datatransform.xml2b.ValueType;
/**
 * 数据类型解析器
 * @author qhlee
 * @version 1.0 2015-08-14
 * 
 */
public interface DataTypeParser {
	/**
	 * 解析数据类型配置信息
	 * <p>
	 * @param node
	 * 			解析节点
	 * @param valueType
	 * 			值类型
	 */
	void parseType(Node node , ValueType valueType) ;
}
