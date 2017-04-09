package com.liqh.commons.datatransform.config;

import java.util.Map;

import org.w3c.dom.Node;

import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.Rule;

public interface MappingRuleXmlParser<R extends Rule> {
	Mapping<R> parseRule(Node paramNode , Map<String,Node> paramMap) ;
}
