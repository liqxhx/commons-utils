package com.liqh.commons.beanfilter.javaassist;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * <pre>
 * 过滤规则解析器
 * 将一个按要求编写的xml文件文件解析成BeanFilterRule
 * 如rule.xml
 * <rules>
 * 	<rule name="test" description="bean filter test" isSimple="false" relationOp="#1 or (#2 and (not #3) and #4)">
 * 		<property name="name" 		type="String" 	value="qhli" 	op="equals" />
 * 		<property name="age" 		type="Numberic" value="20" 		op=">=" />
 * 		<property name="married" 	type="Boolean" 	value="true" 	op="==" />
 * 		<property name="birthDay" 	type="Date" 	value="1981-06-01 17:30:00" op="after" />
 * 	</rule>
 * </rules>
 * 也可以自己写一个解析类解析你自己的规则文件
 * @author qhli
 *
 */
public class BeanFilterXmlRuleParser {
	static protected Logger logger = LoggerFactory.getLogger(BeanFilterXmlRuleParser.class);
	public static final String INDEX_PREFIX = "#";

	public static List<BeanFilterRule> parse(String... fileName) throws BeanFilterException {
		List<BeanFilterRule> ret = new ArrayList<BeanFilterRule>();
		for (String _fileName : fileName) {
			ret.addAll(parse(_fileName));
		}
		return ret;
	}

	public static List<BeanFilterRule> parse(String fileName) throws BeanFilterException {
		logger.debug("开始解析规则文件:{}", fileName);
		
		if (StringUtils.isBlank(fileName))
			throw new BeanFilterException(BFResCode.EBF0002);
		List<BeanFilterRule> ret = new ArrayList<BeanFilterRule>();

		URL url = BeanFilterXmlRuleParser.class.getClassLoader().getResource(fileName);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			throw new BeanFilterException(BFResCode.EBF0003, e);
		}
		Document doc = XMLUtils.getDocument(file, false);

		Element root = doc.getDocumentElement();
		List<Element> eleList = XMLUtils.childNodeList(root, "rule");
		logger.debug("filter rule absoluate path:{}", file.getAbsolutePath());

		for (Element ele : eleList) {
			BeanFilterRule rule = new BeanFilterRule();
			String ruleIdstr = XMLUtils.getNodeAttributeValue(ele, "id");
			String ruleName = XMLUtils.getNodeAttributeValue(ele, "name");
			String ruledescription = XMLUtils.getNodeAttributeValue(ele, "description");
			String ruleIsSimple = XMLUtils.getNodeAttributeValue(ele, "isSimple");
			String rulerelationOp = XMLUtils.getNodeAttributeValue(ele, "relationOp");
			rule.setId(StringUtils.isBlank(ruleIdstr) ? java.util.UUID.randomUUID().toString() : ruleIdstr);
			rule.setName(ruleName);
			rule.setDescription(ruledescription);
			rule.setSimple("true".equalsIgnoreCase(ruleIsSimple) ? true : false);
			rule.setRelationOp(rulerelationOp);

			Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
			
			List<Element> propertyEleList = XMLUtils.childNodeList(ele, "property");
			int i = 0 ;
			for (; i < propertyEleList.size() ; i++) {
				Element propEle = propertyEleList.get(i);
				String pId = XMLUtils.getNodeAttributeValue(propEle, "id");
				String pIndex = XMLUtils.getNodeAttributeValue(propEle, "index");
				String pName = XMLUtils.getNodeAttributeValue(propEle, "name");
				String pType = XMLUtils.getNodeAttributeValue(propEle, "type");
				String pOP = XMLUtils.getNodeAttributeValue(propEle, "op");
				String pValue = XMLUtils.getNodeAttributeValue(propEle, "value");
				
				if(StringUtils.isBlank(pId)) {
					pId = String.valueOf(i+1);
				}
				if(StringUtils.isBlank(pIndex)) {
					pIndex = INDEX_PREFIX + pId;
				}
				
				// bug bug bug !!!!
				// 如果配置了index, 且不以#开头，可能有问题
				// index不能为and or not等关键字，最好自动生成
				
				logger.debug("{} {} {} {} {} {}", pId, pIndex, pName, pType, pValue, pOP);
				PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor(pId, pIndex, pName, pType, pValue, pOP);

				propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
			}
			rule.setPropertyFilterDescriptors(propertyFilterDescriptors);
			ret.add(rule);
		}
	
		return ret;
	}

}
