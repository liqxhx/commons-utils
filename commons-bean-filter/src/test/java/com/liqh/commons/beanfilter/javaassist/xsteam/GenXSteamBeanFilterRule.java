package com.liqh.commons.beanfilter.javaassist.xsteam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.javaassist.RenderType;
import com.thoughtworks.xstream.XStream;


/**
 * @author liqh
 *
 */
public class GenXSteamBeanFilterRule {
	public static void main(String[] args) {
		BeanFilterRule beanFilterRule = new BeanFilterRule("test","bean filter test",false,"#1 or (#2 and not #3) and #4");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String,PropertyFilterDescriptor>();
		propertyFilterDescriptors.put("#1",new PropertyFilterDescriptor("#1",		"name",			RenderType.STRING,		"qhli",	"equals") ) ;
		propertyFilterDescriptors.put("#2",new PropertyFilterDescriptor("#2",	"age",			RenderType.NUMBERIC,	"20",	"==") ) ;
		propertyFilterDescriptors.put("#3",new PropertyFilterDescriptor("#3",	"married",		RenderType.BOOLEAN,		"false",	"==") ) ;
		propertyFilterDescriptors.put("#4",new PropertyFilterDescriptor("#4", 	"birthDay",	RenderType.DATE,			"1981-06-01 17:30:00","after") ) ;
		
		
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		List<BeanFilterRule> rules = new ArrayList<BeanFilterRule>();
		rules.add(beanFilterRule);
		XStream xs = new XStream();
		
		xs.alias("rules", List.class);
		xs.alias("rule", BeanFilterRule.class);
		xs.alias("property", PropertyFilterDescriptor.class);
		System.out.println(xs.toXML(rules));

	}
}
