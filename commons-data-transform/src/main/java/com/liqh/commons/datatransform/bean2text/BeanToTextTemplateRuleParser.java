package com.liqh.commons.datatransform.bean2text;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.config.AbstractMappingRuleXmlParser;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.utils.StringUtils;
import com.liqh.commons.lang.utils.XMLUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public class BeanToTextTemplateRuleParser extends AbstractMappingRuleXmlParser<TemplateContentRule> {

	private final static String ELE_TEMPLATE = "template";

	// 包括元素, 可引用外部的一个segment片断
	private final static String ELE_INCLUDE = "include";

	// 当前模板所引用的一个 segment片断ID
	private final static String ATTR_SEGMENT_ID = "segment-id";

	/**
	 * freemarker模板配置对象
	 */
	private Configuration fmConfig;
	/**
	 * freemarker基于字符串的模板内容装载器
	 */
	private StringTemplateLoader tplLoader;

	public BeanToTextTemplateRuleParser() {
		fmConfig = new Configuration();
		tplLoader = new StringTemplateLoader();

		fmConfig.setTemplateLoader(tplLoader);
	}

	public BeanToTextTemplateRuleParser(String numberFormat) {
		fmConfig = new Configuration();
		tplLoader = new StringTemplateLoader();
		if (StringUtils.isNoneBlank(numberFormat))
			fmConfig.setNumberFormat(numberFormat);

		fmConfig.setTemplateLoader(tplLoader);
	}

	@Override
	public Mapping<TemplateContentRule> parseRule(Node mappingNode, Map<String, Node> segmentNodeMap) {

		Mapping<TemplateContentRule> mapping = super.parseRule(mappingNode, segmentNodeMap);

		StringBuilder tplBuffer = new StringBuilder();// 用于放segment中template的内容

		parseTemplateRule(mappingNode, tplBuffer, segmentNodeMap);

		TemplateContentRule tplRule = new TemplateContentRule();
		tplLoader.putTemplate(mapping.getId(), tplBuffer.toString());
		try {
			tplRule.setTemplate(fmConfig.getTemplate(mapping.getId()));
			mapping.getRules().add(tplRule);
		} catch (IOException e) {
			DataTransformException ex = new DataTransformException(DTResCode.EDT0006, e, mapping.getId());
			logger.error(ex.getLocalizedMessage());
			// 装载Bean到Text的适配规则配置失败,模板内容无法正确解析! mapping-id:{0}
			throw ex;
		}

		return mapping;
	}

	private void parseTemplateRule(Node node, StringBuilder tplBuffer, Map<String, Node> segmentNodeMap) {
		List<Element> tplNodeList = XMLUtils.childNodeList(node);

		if (CollectionUtils.isNotEmpty(tplNodeList)) {
			for (Node tplNode : tplNodeList) {
				if (ELE_INCLUDE.equals(tplNode.getNodeName())) {// <include segment-id="xxx"/>
					// 解析include内容
					String segmentId = XMLUtils.getNodeAttributeValue(tplNode, ATTR_SEGMENT_ID);

					Node segmentNode = segmentNodeMap.get(segmentId);

					if (segmentNode == null) {
						// 装载Bean到Text的适配规则配置失败,嵌套的模板片段不存在! segment-id:{0}
						throw new DataTransformException(DTResCode.EDT0007 , segmentId);
					}
					// 解析内嵌模板
					parseTemplateRule(segmentNode, tplBuffer, segmentNodeMap);
				} else if (ELE_TEMPLATE.equals(tplNode.getNodeName())) {
					// 解析template内容
					String templateText = XMLUtils.getCDATATextData(tplNode);
					if (StringUtils.isBlank(templateText)) {
						templateText = XMLUtils.getTextData(tplNode);
					}
					if (StringUtils.isNotBlank(templateText)) {
						tplBuffer.append(templateText);
					}
				}
			}
		}
	}

}
