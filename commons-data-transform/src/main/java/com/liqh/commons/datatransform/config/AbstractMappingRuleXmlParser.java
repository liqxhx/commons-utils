package com.liqh.commons.datatransform.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.liqh.commons.datatransform.DataTransformer;
import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.Rule;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.utils.StringUtils;
import com.liqh.commons.lang.utils.XMLUtils;

public abstract class AbstractMappingRuleXmlParser<R extends Rule> implements MappingRuleXmlParser<R> {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractMappingRuleXmlParser.class); 

	protected final static String ATTR_ID= "id";
	protected final static String ATTR_TRANSFORMER_ID = "transformer";


	// 校验开关
	protected static final String ATTR_VALIDATE = "validate";

	protected static final String ATTR_EXPECTED_NUMBER = "expected-number";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.openframework.datatransform.config.MappingRuleXmlParser#parseRule
	 * (org.w3c.dom.Node, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Mapping<R> parseRule(Node mappingNode, Map<String, Node> segmentNodeMap) {
		// mapping规则唯一编号id
		String id = XMLUtils.getNodeAttributeValue(mappingNode, ATTR_ID);
		// 转换器类型transformer
		String transfomerId = XMLUtils.getNodeAttributeValue(mappingNode, ATTR_TRANSFORMER_ID);
		// 是否要非空校验validate
		String validate = XMLUtils.getNodeAttributeValue(mappingNode, ATTR_VALIDATE); 
		// 期望非空校验次数expected-number
		String expectedNumberAttr = XMLUtils.getNodeAttributeValue(mappingNode, ATTR_EXPECTED_NUMBER);

		// 转换为数值
		int expectedNumber = 0;
		if (StringUtils.isNotBlank(expectedNumberAttr)) {
			try {
				expectedNumber = Integer.parseInt(expectedNumberAttr);
			} catch (Exception e) {
				// 期望的非空校验次数数字转换失败，输入值为@@{0}@@!
				DataTransformConfigParseException ex = new DataTransformConfigParseException(DTResCode.EDT0002, expectedNumberAttr) ;
				logger.error(ex.getLocalizedMessage(), e);
				throw ex;
			}
		}

		DataTransformer<?, ?, R> transformer = DataTransformerFactory.getDataTransformer(transfomerId);

		return new Mapping<R>(id, transformer, Boolean.parseBoolean(validate), expectedNumber);
	}

}
