package com.liqh.commons.datatransform.xml2b;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.ValueHandler;
import com.liqh.commons.datatransform.config.AbstractMappingRuleXmlParser;
import com.liqh.commons.datatransform.config.DataTransformConfigParseException;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.datatransform.datavalidate.DataTypeParserFactory;
import com.liqh.commons.datatransform.datavalidate.DataValidatorFactory;
import com.liqh.commons.datatransform.handler.HandlerFactory;
import com.liqh.commons.datatransform.utils.Utils;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;
import com.liqh.commons.lang.utils.StringUtils;
import com.liqh.commons.lang.utils.XMLUtils;

public class XmlToBeanRuleParser extends AbstractMappingRuleXmlParser<TypeDefinition> {
 
	private final static String STRUCTURE_ELM = "structure";
	private final static String NODE_NAME_ATTR = "node-name";

	// 值处理器bean-id,用于对值进行加工转换
	private final static String HANDLER_ATTR = "handler";
	private final static String HANDLER_TYPE_ATTR = "handler-type";
	private final static String FIELD_ATTR = "field";
	private final static String TYPE_ATTR = "type";
	private final static String VALUE_ELM = "value";
	private final static String IS_WRAP_ATTR = "is-wrap";
	private final static String COLLECTION_ELM = "collection";

	// 声明当前规则内需子规则覆盖的节点
	private final static String ABSTRACT_ELM = "abstract";

	// 当前规则所继承的父规则MAPPING ID
	private final static String EXTENDS_ATTR = "extends";

	// 覆写的目标子规则ID
	private final static String OVERRIDE_ATTR = "override";

	// 数据类型
	private final static String DATATYPE_ATTR = "datatype";

	@Override
	public Mapping<TypeDefinition> parseRule(Node mappingNode, Map<String, Node> virtualNodeMap) {
		// id, transformer,validate, expected-number
		Mapping<TypeDefinition> mapping = super.parseRule(mappingNode, virtualNodeMap);

		doParse(mappingNode, virtualNodeMap, mapping, new HashMap<String, String>());

		logger.debug("xml2Bean报文适配规则，mapping-id={}，规则={}", 
				mapping.getId(), (CollectionUtils.isEmpty(mapping.getRules()) ? null : mapping.getRules().get(0)));

		return mapping;
	}

	/**
	 * 解析映射规则
	 * <p>
	 * 
	 * @param mappingNode 当前要解析的xml映射
	 * @param virtualNodeMap 所有配置文件中的segment与abstract节点
	 * @param mapping 当前要解析的xml映射对应的java对象
	 */
	private void doParse(Node ruleNode, Map<String, Node> virtualNodeMap, Mapping<TypeDefinition> mapping, Map<String, String> parentIds) {

		List<TypeDefinition> localRules = new ArrayList<TypeDefinition>();
		// 解析当前mapping下的value statucture collection abstract等
		localRules.addAll(parseValues(ruleNode)); 
		localRules.addAll(parseStructures(ruleNode));
		localRules.addAll(parseCollections(ruleNode));
		localRules.addAll(parseVirtuals(ruleNode));

		String extendMappingId = XMLUtils.getNodeAttributeValue(ruleNode, EXTENDS_ATTR);
		if (StringUtils.hasText(extendMappingId)) {
			// 如果有指定继承的映射规则，则先解析父规则
			// 校验父节点是否已经继承过
			if (parentIds.containsKey(extendMappingId)) {
				// 报文-对象规则加载解析失败，当前映射规则出现循环继承，mappingId={0}，extends={1}
				throw new DataTransformConfigParseException(DTResCode.EDT0037, mapping.getId(), extendMappingId);
			}

			Node parentNode = virtualNodeMap.get(extendMappingId);
			if (parentNode == null) {
				// 报文-对象规则加载解析失败，当前映射规则所指定的父规则ID不存在，mappingId={0}，extends={1}
				throw new DataTransformConfigParseException(DTResCode.EDT0038, mapping.getId(), extendMappingId);
			}

			// 记录继承的父规则
			parentIds.put(extendMappingId, extendMappingId);

			doParse(parentNode, virtualNodeMap, mapping, parentIds);

			// 替换父规则中的抽象定义节点
			replaceAbstractRule(null, mapping.getRules(), localRules);
		}
		// 如果没有指定继承,则直接以当前规则内容为准
		else {
			mapping.getRules().addAll(localRules);
		}
	}

	private void replaceAbstractRule(TypeDefinition parent, Collection<TypeDefinition> rules, Collection<TypeDefinition> localRules) {

		Map<String, TypeDefinition> replacedRule = new HashMap<String, TypeDefinition>();

		// 遍历所有规则元素，将定义为abstract的节点替换为真实的规则内容
		for (Iterator<TypeDefinition> ruleIter = rules.iterator(); ruleIter.hasNext();) {
			TypeDefinition theRule = ruleIter.next();
			if (theRule.getType() == VirtualType.TYPE) {
				// 从子规则集合中查找相应的实现，如找到则删除原虚拟节点
				for (TypeDefinition overrideRule : localRules) {
					if (theRule.getNodeName().equals(overrideRule.getOverrideId())) {
						if (parent != null) {
							if (parent.getType() == StructureType.TYPE) {
								overrideRule.setParent(parent);
								overrideRule.setSetterMethod(Utils.toSetterMethod(parent.getActualType(), overrideRule.getProperty()));
								if (overrideRule.getType() == CollectionType.TYPE) {
									overrideRule
											.setGetterMethod(Utils.toGetterMethod(
													parent.getActualType(),
													overrideRule.getProperty()));
								}
							} else if (overrideRule.getType() == CollectionType.TYPE) {
								// 配置错误，collection结构只能包含于structure结构，父节点={0}，子节点={1}
								throw new DataTransformConfigParseException(DTResCode.EDT0039, parent.getNodeName(), overrideRule.getNodeName());
							} else if (parent.getType() == CollectionType.TYPE) {
								overrideRule.setParent(parent);
								if (!parent.isWraped()) {
									// 修改非包装类型的collection为新的nodeName
									parent.setNodeName(overrideRule.getNodeName());
								}
							}
						}
						replacedRule.put(overrideRule.getNodeName(),
								overrideRule);
						// 删除原虚拟节点
						ruleIter.remove();
						break;
					}
				}
			} else if (theRule.getType() == CollectionType.TYPE) {
				replaceAbstractRule(theRule, ((CollectionType) theRule).getRuleMap().values(), localRules);
			} else if (theRule.getType() == StructureType.TYPE) {
				replaceAbstractRule(theRule, ((StructureType) theRule).getRuleMap().values(), localRules);
			}

		}

		// 将实现的规则内容加入到映射规则中
		if (parent == null) {
			rules.addAll(replacedRule.values());
		} else if (parent.getType() == CollectionType.TYPE) {
			((CollectionType) parent).getRuleMap().putAll(replacedRule);
		} else if (parent.getType() == StructureType.TYPE) {
			// 在structure->collection->abstract结构下，如果collection.isWrap=false，
			// 则collection的nodeName会被修改，这时需要用collection的新key（nodeName）
			// 重新把collection加入到structure中
			Map<String, TypeDefinition> subRules = ((StructureType) parent)
					.getRuleMap();
			Set<Entry<String, TypeDefinition>> subRuleEntries = subRules
					.entrySet();
			if (!CollectionUtils.isEmpty(subRuleEntries)) {
				for (Iterator<Entry<String, TypeDefinition>> entryIter = subRuleEntries
						.iterator(); entryIter.hasNext();) {
					Entry<String, TypeDefinition> subRuleEntry = entryIter
							.next();
					TypeDefinition subRule = subRuleEntry.getValue();
					if (!subRuleEntry.getKey().equals(subRule.getNodeName())) {
						replacedRule.put(subRule.getNodeName(), subRule);
						entryIter.remove();
					}
				}
			}
			subRules.putAll(replacedRule);
		}
	}

	private List<TypeDefinition> parseVirtuals(Node parentNode) {
		List<Element> nodeList = XMLUtils.childNodeList(parentNode, ABSTRACT_ELM);

		List<TypeDefinition> virtualList = new ArrayList<TypeDefinition>();
		if (!CollectionUtils.isEmpty(nodeList)) {
			for (Node node : nodeList) {
				String id = XMLUtils.getNodeAttributeValue(node, ATTR_ID);
				if (StringUtils.hasText(id)) {
					VirtualType type = new VirtualType();
					type.setAbstractId(id);
					virtualList.add(type);
				}
			}
		}

		return virtualList;
	}

	private List<TypeDefinition> parseCollections(Node parentNode) {

		List<TypeDefinition> collectionList = new ArrayList<TypeDefinition>();
		List<Element> nodeList = XMLUtils.childNodeList(parentNode,
				COLLECTION_ELM);

		if (!CollectionUtils.isEmpty(nodeList)) {

			for (Node node : nodeList) {
				CollectionType collection = new CollectionType();

				String property = XMLUtils.getNodeAttributeValue(node,
						FIELD_ATTR);
				String nodeName = XMLUtils.getNodeAttributeValue(node,
						NODE_NAME_ATTR);
				String type = XMLUtils.getNodeAttributeValue(node, TYPE_ATTR);
				String isWrap = XMLUtils.getNodeAttributeValue(node,
						IS_WRAP_ATTR);
				String overrideId = XMLUtils.getNodeAttributeValue(node,
						OVERRIDE_ATTR);

				collection.setProperty(property);
				collection.setNodeName(nodeName);
				collection.setWraped(StringUtils.hasText(isWrap) ? Boolean
						.valueOf(isWrap) : true);
				collection.setOverrideId(overrideId);
				try {
					collection.setActualType(Class.forName(type));
				} catch (ClassNotFoundException e) {
					throw new CommonRuntimeException(DTResCode.EDT0033, e, type);
				}

				// 校验是否含有集合
				if (!CollectionUtils.isEmpty(parseCollections(node))) {
					// 配置错误，collection结构只能包含于structure结构，父节点={0}
					throw new DataTransformConfigParseException("XTLF300700BD",
							collection.getNodeName());
				}

				// 添加普通值绑定内容
				for (TypeDefinition def : parseValues(node)) {
					def.setParent(collection);
					collection.getRuleMap().put(def.getNodeName(), def);
				}

				// 添加结构绑定内容
				for (TypeDefinition def : parseStructures(node)) {
					def.setParent(collection);
					collection.getRuleMap().put(def.getNodeName(), def);
				}

				// 添加抽象绑定内容
				for (TypeDefinition def : parseVirtuals(node)) {
					def.setParent(collection);
					collection.getRuleMap().put(def.getNodeName(), def);
				}

				if (!collection.isWraped()) {
					// 非包装的集合，有且只有一个子节点，且nodeName应与子节点nodeName一致
					if (collection.getRuleMap().size() == 1) {
						collection.setNodeName(collection.getRuleMap().values()
								.iterator().next().getNodeName());
					} else {
						// 配置错误，非包装（is-wrap="false"）的collection结构有且只有一个子节点，field={0}
						throw new DataTransformConfigParseException(
								"XTLF300700BE", collection.getProperty());
					}
				}

				collectionList.add(collection);
			}
		}

		return collectionList;
	}

	private List<TypeDefinition> parseStructures(Node parentNode) {
		List<TypeDefinition> structureList = new ArrayList<TypeDefinition>();
		List<Element> nodeList = XMLUtils.childNodeList(parentNode, STRUCTURE_ELM);

		if (!CollectionUtils.isEmpty(nodeList)) {
			for (Node node : nodeList) {
				StructureType struct = parseStructure(node);
				structureList.add(struct);
			}
		}

		return structureList;
	}

	private StructureType parseStructure(Node node) {
		StructureType struct = new StructureType();

		String property = XMLUtils.getNodeAttributeValue(node, FIELD_ATTR);// field
		String nodeName = XMLUtils.getNodeAttributeValue(node, NODE_NAME_ATTR); // node-name
		String type = XMLUtils.getNodeAttributeValue(node, TYPE_ATTR);// type
		// String isWrap = XMLUtils.getNodeAttributeValue(node, IS_WRAP_ATTR);
		String overrideId = XMLUtils.getNodeAttributeValue(node, OVERRIDE_ATTR); // override

		struct.setProperty(property);
		struct.setNodeName(nodeName);
		// struct.setWraped(StringUtils.hasText(isWrap) ?
		// Boolean.valueOf(isWrap)
		// : true);
		// structure结构不能去掉包装
		struct.setWraped(true);
		struct.setOverrideId(overrideId);
		try {
			struct.setActualType(Class.forName(type));
		} catch (ClassNotFoundException e) {
			throw new CommonRuntimeException(DTResCode.EDT0033, e, type);
		}

		/**
		 * 添加普通值绑定内容
		 */
		for (TypeDefinition def : parseValues(node)) {
			Method setter = Utils.toSetterMethod(struct.getActualType(), def.getProperty());
			def.setSetterMethod(setter);
			def.setParent(struct);
			struct.getRuleMap().put(def.getNodeName(), def);
		}

		/**
		 * 添加集合绑定内容
		 */
		for (TypeDefinition def : parseCollections(node)) {

			Method setter = Utils.toSetterMethod(struct.getActualType(), def.getProperty());
			def.setSetterMethod(setter);

			Method getter = Utils.toGetterMethod(struct.getActualType(), def.getProperty());
			def.setGetterMethod(getter);

			def.setParent(struct);

			struct.getRuleMap().put(def.getNodeName(), def);
		}

		/**
		 * 添加结构绑定内容
		 */
		for (TypeDefinition def : parseStructures(node)) {
			Method setter = Utils.toSetterMethod(struct.getActualType(),
					def.getProperty());
			def.setSetterMethod(setter);

			def.setParent(struct);

			struct.getRuleMap().put(def.getNodeName(), def);
		}

		for (TypeDefinition def : parseVirtuals(node)) {
			def.setParent(struct);
			struct.getRuleMap().put(def.getNodeName(), def);
		}

		return struct;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<TypeDefinition> parseValues(Node parentNode) {
		List<TypeDefinition> valueList = new ArrayList<TypeDefinition>();
		List<Element> nodeList = XMLUtils.childNodeList(parentNode, VALUE_ELM);

		if (!CollectionUtils.isEmpty(nodeList)) {
			for (Node node : nodeList) {
				String property = XMLUtils.getNodeAttributeValue(node, FIELD_ATTR);
				String nodeName = XMLUtils.getNodeAttributeValue(node, NODE_NAME_ATTR);
				String type = XMLUtils.getNodeAttributeValue(node, TYPE_ATTR);
				String handlerId = XMLUtils.getNodeAttributeValue(node, HANDLER_ATTR);
				String overrideId = XMLUtils.getNodeAttributeValue(node, OVERRIDE_ATTR);
				String handlerType = XMLUtils.getNodeAttributeValue(node, HANDLER_TYPE_ATTR);

				ValueType value = new ValueType();

				// 报文校验 begin
				// 获取数据类型
				String dataType = XMLUtils.getNodeAttributeValue(node, DATATYPE_ATTR);
				if (StringUtils.hasText(dataType)) {
					String dataTypeParserId = (new StringBuilder(dataType)).append("Parser").toString(); // TextTypeParser/DatetimeTypeParser/NumericTypeParser
					String typeValidatorId = (new StringBuilder(dataType)).append("Validator").toString();// TextTypeValidator/DatetimeTypeValidator/NumericTypeValidator
					// 解析数据类型
					DataTypeParserFactory.getDataTypeParser(dataTypeParserId).parseType(node, value);
					
					// 设置校验器					
					value.setDataValidator(DataValidatorFactory.getDataValidator(typeValidatorId));
				}
				// 报文校验 end

				value.setProperty(property);
				value.setNodeName(nodeName);
				value.setOverrideId(overrideId);
				value.setWraped(true);
				/**
				 * 获取值转换器实例
				 */
				if (StringUtils.hasText(handlerId)) {
//					value.setValueHandler(applicationContext.getBean(handlerId,ValueHandler.class));
					value.setValueHandler(HandlerFactory.getValueHandler(handlerId));
				}else if(StringUtils.hasText(handlerType)){
					logger.debug("use handlerType {}" , handlerType);
					try {
						Class claxx = Class.forName(handlerType);
						if(!HandlerFactory.contains(claxx)){
							HandlerFactory.registerHandler((ValueHandler) claxx.newInstance());
							logger.debug("registerHandler {}" , handlerType);
						}
						value.setValueHandler(HandlerFactory.getValueHandler(claxx.getSimpleName()));
					} catch (Exception e) {
						// 无法找到指定的类型,className={0}
						throw new CommonRuntimeException(DTResCode.EDT0033, e, type);
					}
				}

				try {
					value.setActualType(StringUtils.hasText(type) ? Class.forName(type) : String.class);
				} catch (ClassNotFoundException e) {
					// 无法找到指定的类型,className={0}
					throw new CommonRuntimeException(DTResCode.EDT0033, e, type);
				}
				valueList.add(value);
			}
		}

		return valueList;
	}

}
