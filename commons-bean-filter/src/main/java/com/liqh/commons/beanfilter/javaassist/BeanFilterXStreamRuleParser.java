/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.javaassist;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;
import com.thoughtworks.xstream.XStream;

public class BeanFilterXStreamRuleParser {
	private final static XStream xs = new XStream();
	static private Logger logger = LoggerFactory.getLogger(BeanFilterXmlRuleParser.class);
	static{
		xs.alias("rules", List.class);
		xs.alias("rule", BeanFilterRule.class);
		xs.alias("property", PropertyFilterDescriptor.class);
	}
	
	public static List<BeanFilterRule> parse(String... fileName) throws BeanFilterException {
		List<BeanFilterRule> ret = new ArrayList<BeanFilterRule>();
		for (String _fileName : fileName) {
			ret.addAll(parse(_fileName));
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<BeanFilterRule> parse(String fileName) throws BeanFilterException {
	logger.debug("开始解析规则文件:{}", fileName);
		
		if (StringUtils.isBlank(fileName))
			throw new BeanFilterException(BFResCode.EBF0002);
 

		URL url = BeanFilterXmlRuleParser.class.getClassLoader().getResource(fileName);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			throw new BeanFilterException(BFResCode.EBF0003, e);
		}
		
		List<BeanFilterRule> ret = (List<BeanFilterRule>) xs.fromXML(file);
		if(CollectionUtils.isNotEmpty(ret)) {
			logger.debug("rule size:{} {}", ret.size(), ret.toArray());			 
		} else {
			logger.warn("rule empty");
		}
		return ret;
	}
}
