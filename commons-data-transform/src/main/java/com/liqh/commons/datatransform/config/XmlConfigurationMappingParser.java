package com.liqh.commons.datatransform.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.liqh.commons.datatransform.MappingRegistry;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.i18n.I18n;
import com.liqh.commons.lang.utils.XMLUtils;

/**
 * <pre>
 * Xml映射规则配置解析器实现
 * 以xml方式规则的mapping规则
 * @author qhlee
 *
 */
@SuppressWarnings("unchecked")
public class XmlConfigurationMappingParser implements MappingConfigParser {
	protected static final Logger logger = LoggerFactory.getLogger(XmlConfigurationMappingParser.class);
	// 定义XML标签:mapping
	protected static final String ELM_MAPPING = "mapping";
	protected static final String ATTR_PARSER = "parser";

	// 规则片断，供其它规则引用
	protected static final String ELM_SEGMENT = "segment";

	// 抽象规则，供其它规则继承覆盖
	protected static final String ELM_ABSTRACT = "abstract";

	protected static final String ATTR_ID = "id";

	// 解析出的规则对象注册容器
	private MappingRegistry mappingRegistry;

	// 配置文件路径集合
	private String[] configurations;

	// 虚拟片段节点信息
	Map<String, Node> virtualNodeMap = new HashMap<String, Node>();

	@Override
	public void parse() {
		if (this.configurations == null || this.configurations.length == 0) {
			logger.warn(I18n.getMessage(DTResCode.LDT0001));
			return;
		}
		InputStream in = null;
		String filePath = null;
		List<Element> elements = new ArrayList<Element>();

		// //virtualNodeMap为segment和abstract元素
		// Map<String, Node> virtualNodeMap = new HashMap<String, Node>();
		for (String path : this.configurations) {		 
			Document doc = null;
			try {
				
				URL url = XmlConfigurationMappingParser.class.getClassLoader().getResource(path);
				File file = new File(url.toURI());
//				filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+path;
				filePath = file.getAbsolutePath();
				// 解析映射规则文件={0}
				logger.debug(I18n.getMessage(DTResCode.LDT0002, filePath));
				 
				in = new FileInputStream(file);
				doc = XMLUtils.getDocument(in);
			} catch (Exception e) {
				/*try{
					filePath = XmlConfigurationMappingParser.class.getResource(path).getFile();
					
					in = XmlConfigurationMappingParser.class.getResourceAsStream(path);
					doc = XMLUtils.getDocument(in);
				}catch(Exception ie){
					// 无法完成映射规则文件{0}的解析
					DataTransformConfigParseException ex = new DataTransformConfigParseException(DTResCode.EDT0004, ie, filePath);
					logger.error(ex.getLocalizedMessage(), ex);
					throw ex;
				}*/
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
			if ((doc == null) || (docElement = doc.getDocumentElement()) == null) {
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

	/**
	 * 解析出mapping 再将segment和abstract作为参数传入
	 * 
	 * @param docElement
	 * @param virtualNodeMap
	 */
	private void parseMappings(Element docElement, Map<String, Node> virtualNodeMap) {
		// 获取一个配置文件下的所有mapping
		List<Element> mappingNodeList = XMLUtils.childNodeList(docElement, ELM_MAPPING);
		if (CollectionUtils.isNotEmpty(mappingNodeList)) {
			for (Node mappingNode : mappingNodeList) {				
				String parserName = XMLUtils.getNodeAttributeValue(mappingNode, ATTR_PARSER);
				// 用mapping上配置的解析器parser解析出Mapping规则, 并注册到mappingRegistry中
				this.mappingRegistry.addMapping(MappingRuleXmlParserFactory.getMappingRuleXmlParser(parserName).parseRule(mappingNode, virtualNodeMap));
			}
		}
	}

	/**
	 * 解析出根节点下第一级的segment和abstract节点
	 * 
	 * @param docElement
	 * @return
	 */
	private Map<String, Node> loadVirutalNodeMap(Element docElement) {
		Map<String, Node> virutalNodeMap = new HashMap<String, Node>();
		List<Element> segmentNodeList = XMLUtils.childNodeList(docElement, ELM_SEGMENT);
		if (CollectionUtils.isNotEmpty(segmentNodeList)) {
			for (Node segmentNode : segmentNodeList) {
				String id = XMLUtils.getNodeAttributeValue(segmentNode, ATTR_ID);
				virutalNodeMap.put(id, segmentNode);
			}
		}

		List<Element> abstractNodeList = XMLUtils.childNodeList(docElement, ELM_ABSTRACT); 
		if (CollectionUtils.isNotEmpty(abstractNodeList)) {
			for (Node abstractNode : abstractNodeList) {
				String id = XMLUtils.getNodeAttributeValue(abstractNode, ATTR_ID);
				virutalNodeMap.put(id, abstractNode);
			}
		}

		return virutalNodeMap;
	}

	public MappingRegistry getMappingRegistry() {
		return mappingRegistry;
	}

	public void setMappingRegistry(MappingRegistry mappingRegistry) {
		this.mappingRegistry = mappingRegistry;
	}

	public String[] getConfigurations() {
		return configurations;
	}

	public void setConfigurations(String[] configurations) {
		this.configurations = configurations;
	}

}
