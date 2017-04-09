/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.liqh.commons.datatransform.config.DataTransformConfigParseException;
import com.liqh.commons.datatransform.config.DefaultMappingRegistry;
import com.liqh.commons.datatransform.config.MappingRuleXmlParser;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.i18n.I18n;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * 
 * <pre>
 *
 * @author qhlee
 * @versioin v1.0 2016年11月15日
 * @see
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class BaseDataTransformerTest {
	protected static Logger logger = LoggerFactory.getLogger(BaseDataTransformerTest.class);
	
	static DataTransformer transformer ;
	static MappingRuleXmlParser parser ;
	static MappingRegistry mappingRegistry = new DefaultMappingRegistry();
	static String configurations[];
	

	protected static void init(DataTransformer dataTransformer, MappingRuleXmlParser mappingRuleXmlParser,String[] cfg){
		configurations = cfg;
		transformer = dataTransformer;
		parser = mappingRuleXmlParser;
		
		initSlf4j();
		initI18n();
		initMappingRegistry();		
	}	
	
	static void initMappingRegistry() {		
		List<Element> elements = new ArrayList<Element>();
		Map<String, Node> virtualNodeMap = new HashMap<String, Node>();

		String filePath = null;
		InputStream in = null;
		for (String path : configurations) {
			Document doc = null;
			try {
				URL url = BaseDataTransformerTest.class.getClassLoader().getResource(path);
				File file = new File(url.toURI());
				// filePath =
				// System.getProperty("user.dir")+System.getProperty("file.separator")+path;
				filePath = file.getAbsolutePath();
				// 解析映射规则文件={0}
				logger.debug(I18n.getMessage(DTResCode.LDT0002, filePath));

				in = new FileInputStream(file);
				doc = XMLUtils.getDocument(in);
			} catch (Exception e) {
				DataTransformConfigParseException ex = new DataTransformConfigParseException(DTResCode.EDT0004, e, filePath);
				logger.error(ex.getLocalizedMessage(), ex);
				throw ex;

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// 关闭映射规则文件{0}发生异常
						logger.error(I18n.getMessage(DTResCode.LDT0003, filePath), e);
					}
				}
			}

			Element docElement = null;
			if ((doc == null)
					|| (docElement = doc.getDocumentElement()) == null) {
				continue;
			}

			// 暂存docuemnt对象，先解析所有虚拟节点
			elements.add(docElement);
			// 解析所有document下一级节点为abstract和segment的节点，放到virtualNodeMap中
			virtualNodeMap.putAll(loadVirutalNodeMap(docElement));
		}

		// 解析数据转换映射规则
		for (Element docElement : elements) {
			// 遍历解析所有配置文件
			parseMappings(docElement, virtualNodeMap);
		}
	}


	static void initI18n(){
		I18n.init(null, "framework/resources/data_transform_err", "framework/resources/data_transform_log");
	}
	static void initSlf4j(){
		// 自定义slf4j的变量，然后在logback.xml使用%X{key} 方式引用
		// slf4j: %X{key}
		// log4j: %X{['key']}
		org.slf4j.MDC.put("_myId", new Random(System.currentTimeMillis()).nextInt() + ""); // 设置一个全局的id
	}
	
 
	static void parseMappings(Element docElement, Map<String, Node> virtualNodeMap) {
		// 获取一个配置文件下的所有mapping
		List<Element> mappingNodeList = XMLUtils.childNodeList(docElement, "mapping");
		if (CollectionUtils.isNotEmpty(mappingNodeList)) {
			for (Node mappingNode : mappingNodeList) {
//				String parserName = XMLUtils.getNodeAttributeValue(mappingNode, "parser");
//				logger.info(parserName);
				// 用mapping上配置的解析器parser解析出Mapping规则, 并注册到mappingRegistry中
				mappingRegistry.addMapping(parser.parseRule(mappingNode, virtualNodeMap));
			}
		}
	}

	static Map<String, Node> loadVirutalNodeMap(Element docElement) {
		Map<String, Node> virutalNodeMap = new HashMap<String, Node>();
		List<Element> segmentNodeList = XMLUtils.childNodeList(docElement, "segment");
		if (CollectionUtils.isNotEmpty(segmentNodeList)) {
			for (Node segmentNode : segmentNodeList) {
				String id = XMLUtils.getNodeAttributeValue(segmentNode, "id");
				virutalNodeMap.put(id, segmentNode);
			}
		}

		List<Element> abstractNodeList = XMLUtils.childNodeList(docElement,
				"abstract");
		if (CollectionUtils.isNotEmpty(abstractNodeList)) {
			for (Node abstractNode : abstractNodeList) {
				String id = XMLUtils.getNodeAttributeValue(abstractNode, "id");
				virutalNodeMap.put(id, abstractNode);
			}
		}

		return virutalNodeMap;
	}

	public static DataTransformer getTransformer() {
		return transformer;
	}

	
	public static MappingRuleXmlParser getParser() {
		return parser;
	}


	public static MappingRegistry getMappingRegistry() {
		return mappingRegistry;
	}

	
	public static Mapping getMapping(String mappingId) {
		return mappingRegistry.getMapping(mappingId);
	}
	
}
