/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.config;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.bean2text.BeanToTextTemplateRuleParser;
import com.liqh.commons.datatransform.xml2b.XmlToBeanRuleParser;

@SuppressWarnings("rawtypes")
public class MappingRuleXmlParserFactory {
	
	private static final Map<String, MappingRuleXmlParser> parsers = new HashMap<String, MappingRuleXmlParser>();
	static {
		parsers.put("bean2TextParser", new BeanToTextTemplateRuleParser());
		parsers.put("xml2BeanParser", new XmlToBeanRuleParser());
	}
	
	public static MappingRuleXmlParser getMappingRuleXmlParser(String parserId) {
		return parsers.get(parserId);
	}
}
