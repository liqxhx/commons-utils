package com.liqh.commons.datatransform.xml2b;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.AbstractTransformer;
import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.lang.utils.StringUtils;

public class XmlToBeanTransformer extends
		AbstractTransformer<String, Object, TypeDefinition> {
	private static final Logger logger = LoggerFactory.getLogger(XmlToBeanTransformer.class);
	private static final String log001 = "当前解析接收报文中标签[{}]，其父标签为[{}]";

	private static final String log002 = "非空校验次数:{}!";
	
	private static final String log003 = "非空校验期望次数:{}!";
	
	private static final String log004 = "非空校验次数小于期望的校验次数,报文中缺少必须为非空的节点,非空校验次数:{},期望的非空校验次数:{}!";
	
	protected XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();



	@Override
	public Object transform(String source, Mapping<TypeDefinition> mapping) {

		TypeDefinition rootRule = getSingleMappingRule(mapping);
		if (rootRule.getType() != StructureType.TYPE) {
			// 无法完成从XML到Bean的适配转换工作，转换规则必须是structure结构，mapping-id={0}
			throw new DataTransformException(DTResCode.EDT0034, mapping.getId());
		}

		try {
			// 构建XML流读取器
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(new StringReader(source));

			if(mapping.isValidate()){// 非空校验
				DataValidatorThreadLocal.put(0);
			}
			
			Object obj = null;
			
//			return navBinding(reader, rootRule,mapping.isValidate());
			try{
				obj = navBinding(reader, rootRule, mapping.isValidate());
				
				if(mapping.isValidate()){
//					int validateNumber = SwapAreaUtils.getValidateNumber();
					int validateNumber = DataValidatorThreadLocal.get();
					logger.debug(log002, validateNumber);
					
					int expectedNumber = mapping.getExpectedNumber();
					logger.debug(log003, expectedNumber);
					if(validateNumber < expectedNumber){
						// 非空校验次数小于期望的校验次数,报文中缺少必须为非空的节点,非空校验次数:{0},期望的非空校验次数:{1}!
						logger.error(log004, validateNumber, expectedNumber);
						throw new DataTransformException(DTResCode.EDT0035, validateNumber, expectedNumber);
					}					
				}
			}finally{
				if(mapping.isValidate()){
					DataValidatorThreadLocal.removeValidateCounter();
				}
			}
			
			return obj;

		} catch (XMLStreamException e) {
			// 无法完成从XML到Bean的适配转换工作!
			throw new DataTransformException(DTResCode.EDT0036, e);
		}
	}

	/**
	 * 遍历解析XML报文
	 * <p>
	 * 
	 * @param reader
	 *            XML报文输入
	 * @param context
	 *            解析过程的上下文
	 * @throws XMLStreamException
	 */
	protected Object navBinding(XMLStreamReader reader, TypeDefinition rootRule, boolean validateFlag) throws XMLStreamException {

		BindingContext context = new BindingContext();
		context.setValueTypePushFlag(false);
		
		//TODO:设置校验开关
		//boolean validateFlag = SwapAreaUtils.getValidate();
		// boolean validateFlag = true;
		context.setValidateFlag(validateFlag);
		
		// 处理报文根节点，为根节点初始化一个Map容器
		context.getNavStack().push(new HashMap<String, Object>());
		context.getRuleStack().push(rootRule);
		logger.debug("rootRule property:{} nodeName:{} type:{}", rootRule.getProperty(), rootRule.getNodeName(), rootRule.getActualType());
		rootRule.bind(context, null);

		// 当前所处的XML层次数，根节点为第1层
		int elementDepth = 0;
		// 当前所处的被忽略的XML层级数
		int ignoredDepth = 0;
		int eventType = reader.getEventType();
		while (true) {
			// System.out.println("eventType ================ " + eventType +
			// " "
			// + elementDepth);
			switch (eventType) {
			case XMLStreamConstants.START_ELEMENT:
				elementDepth++;
				if (elementDepth > 1) {
					// 跳过已经处理的根节点
					if (ignoredDepth == 0) {
						if (!handleStartElement(reader, context)) {
							// 当前节点无效，则XML忽略层级数加1
							ignoredDepth++;
						}
					} else {
						// 处于被忽略的层级，直接返回并把忽略层级数加1
						ignoredDepth++;
					}
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (elementDepth > 1) {
					// 跳过已经处理的根节点
					if (ignoredDepth == 0) {
						if (!handleEndElement(context)) {
							// 退出一个无效节点，则XML忽略层级数减1
							ignoredDepth--;
						}
					} else {
						// 处于被忽略的层级，直接返回并把忽略层级数减1
						ignoredDepth--;
					}
				}
				elementDepth--;
				break;
			case XMLStreamConstants.CHARACTERS:
			case XMLStreamConstants.CDATA:
				if (elementDepth > 1 && ignoredDepth == 0) {
					handleCharacters(reader, context);
				}
				break;
			// 以下节点忽略
			case XMLStreamConstants.START_DOCUMENT:
			case XMLStreamConstants.END_DOCUMENT:
			case XMLStreamConstants.SPACE:
			case XMLStreamConstants.COMMENT:
			case XMLStreamConstants.DTD:
			case XMLStreamConstants.ENTITY_REFERENCE:
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				break;
			default:
				break;
			}
			if (reader.hasNext()) {
				eventType = reader.next();
			} else {
				break;
			}
		}

		return context.getNavStack().pop();
	}

	/**
	 * 处理XML报文开始标签
	 * <p>
	 * 
	 * @param reader
	 *            XML读取器
	 * @param context
	 *            解析上下文
	 * @return 当前节点是否有效：true-有效，false-无效（被忽略）
	 */
	private boolean handleStartElement(XMLStreamReader reader, BindingContext context) {

		String name = toQualifiedName(reader.getName());
		 
		//if(log.isDebugEnabled())
			 logger.debug(log001, name, context.getRuleStack().peek().getNodeName());

		TypeDefinition rule = context.getRuleStack().peek().getChild(name);
		// 没有配置的节点，忽略，并标记
		if (rule == null) {
			return false;
		}

		// System.out.println(".....start-- processing:" + rule.isWraped());

		context.getRuleStack().push(rule);
		// 仅处理structure和collection这两种包装规则，value规则交由handleCharacters方法处理
		if (rule.getType() != ValueType.TYPE) {
			rule.bind(context, null);

			// 解析当前元素的所有属性
			handleAttributes(reader, context, rule);

			// System.out.println(".....start-- isWraped:"
			// + rule.isWraped()
			// + "  has same child:"
			// + (rule.getChild(name) == null ? null : rule.getChild(name)
			// .getNodeName()));

			// 如果是无包装标签的集合映射，则继续下钻一层处理
			if (!rule.isWraped()) {
				TypeDefinition child = rule.getChild(name);
				if (child != null) {
					context.getRuleStack().push(child);

					// System.out.println(" >>>>start inner:" + name);

					if (child.getType() != ValueType.TYPE) {
						child.bind(context, null);

						// 解析当元素的所有属性
						handleAttributes(reader, context, rule);

						// System.out.println(".....start bind!");
					}
				} else {
					context.getRuleStack().pop();
					context.getNavStack().pop();
					return false;
				}
			}
		}

		return true;
	}

	private void handleAttributes(XMLStreamReader reader, BindingContext context, TypeDefinition rule) {
		int attriSize = reader.getAttributeCount();
		for (int i = 0; i < attriSize; i++) {
			String value = reader.getAttributeValue(i);

			if (value != null) {
				String name = reader.getAttributeName(i).toString();
				TypeDefinition attriRule = rule.getChild(name);

				if (attriRule != null && attriRule.getType() == ValueType.TYPE) {
					context.getRuleStack().push(attriRule);
					attriRule.bind(context, value);
					context.getRuleStack().pop();
					context.getNavStack().pop();
				}
			}
		}
	}

	/**
	 * 处理XML报文结束标签
	 * <p>
	 * 
	 * @param context
	 *            解析上下文
	 * @return 当前节点是否有效：true-有效，false-无效（被忽略）
	 */
	private boolean handleEndElement(BindingContext context) {

		TypeDefinition rule = context.getRuleStack().pop();
		TypeDefinition parent = context.getRuleStack().peek();
		if (rule.getType() == ValueType.TYPE) {
			if (context.isValueTypePushFlag()) {
				// 非空标签
				context.getNavStack().pop();
				context.setValueTypePushFlag(false);
			} else {
				//调用是否为空的校验
				((ValueType)rule).validateNull(context);
				if (parent.getType() == CollectionType.TYPE) {
					// 在空标签的情况下，如果父节点是List，绑定null值以使List内的对象位数对齐
					rule.bind(context, null);
					context.getNavStack().pop();
					context.setValueTypePushFlag(false);
				}
			}
		} else {
			context.getNavStack().pop();
		}

		// System.out.println("<<<< end node:" + rule.getNodeName());
		// System.out.println("<<<< end value:" + value);

		if (parent != null && !parent.isWraped()) {
			context.getNavStack().pop();
			context.getRuleStack().pop();

			// System.out.println("   <<<< end:" + parent.getNodeName() +
			// "  left:" + context.getRuleStack().size());
		}

		return true;
	}

	private void handleCharacters(XMLStreamReader reader, BindingContext context) {
		//TODO:处理空值的情况
		if (!reader.isWhiteSpace()) {
			context.getRuleStack()
					.peek()
					.bind(context,
							new String(reader.getTextCharacters(), reader
									.getTextStart(), reader.getTextLength()));
		}
	}

	/**
	 * Convert a <code>QName</code> to a qualified name, as used by DOM and SAX.
	 * The returned string has a format of <code>prefix:localName</code> if the
	 * prefix is set, or just <code>localName</code> if not.
	 * 
	 * @param qName
	 *            the <code>QName</code>
	 * @return the qualified name
	 */
	protected String toQualifiedName(QName qName) {
		StringBuilder name = new StringBuilder(10);

		String prefix = qName.getPrefix();
		if (StringUtils.isBlank(prefix)) {
			name.append(qName.getLocalPart());
		} else {
			name.append(prefix).append(':').append(qName.getLocalPart());
		}

		return name.toString();
	}


}
