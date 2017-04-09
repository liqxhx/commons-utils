package com.liqh.commons.lang.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLUtils {
//	public static final String log001 = "无法生成Document对象，文件名={0}";
//	public static final String log002 = "无法生成Document对象";
//	public static final String log003 = "无法输出XML流";

	public static final String XML_CDATA_START = "<![CDATA[";
	public static final String XML_CDATA_END = "]]>";

	public static Element[] ZERO_LENGTH_ELEMENT = new Element[0];

	public static final DummyErrorHandler DUMMY_ERROR_HANDLER = new DummyErrorHandler();

	public static class DummyErrorHandler implements ErrorHandler {
		@Override
		public void fatalError(SAXParseException err) throws SAXException {
			throw err;
		}

		@Override
		public void error(SAXParseException err) throws SAXException {
			throw err;
		}

		@Override
		public void warning(SAXParseException err) throws SAXException {
			throw err;
		}
	}

	/**
	 * 功能说明：根据文件生成Document对象
	 * 
	 * @return org.w3c.dom.Document
	 * @param f
	 *            java.io.File
	 */
	public static Document getDocument(File f, boolean validate) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			// Use validating parser.
			docBuilderFactory.setValidating(validate);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(DUMMY_ERROR_HANDLER);
			doc = docBuilder.parse(f);
			// Normalize text representation
			doc.getDocumentElement().normalize();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return doc;
	}

	public static Document getDocument(File f) {
		return getDocument(f, true);
	}

	public static Document getDocument(InputStream is) {
		return getDocument(new InputSource(is));
	}

	public static Document getDocument(Reader r) {
		return getDocument(new InputSource(r));
	}

	public static Document getDocument(String data) {
		return getDocument(new StringReader(data));
	}

	public static Document getDocument(InputSource is) {
		return getDocument(is, false);
	}

	public static Document getDocument(InputSource is, boolean validate) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			// Use validating parser.
			docBuilderFactory.setValidating(validate);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(DUMMY_ERROR_HANDLER);
			doc = docBuilder.parse(is);
			// Normalize text representation
			doc.getDocumentElement().normalize();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return doc;
	}

	public static OutputStream writeDocument(Document doc, OutputStream os) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return os;
	}

	/**
	 * 得到XML文件中某个Element的第一个子Element。
	 * 
	 * @return org.w3c.dom.Node
	 * @param node
	 *            org.w3c.dom.Node
	 */
	public static Element getNextChildElement(Node node) {
		Node n = node.getFirstChild();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}

	/**
	 * 得到XML文件中某个Element的下一个兄弟Element。
	 * 
	 * @param node
	 *            org.w3c.dom.Node
	 * @return org.w3c.dom.Element
	 */
	public static Element getNextSiblingElement(Node node) {
		Node n = node.getNextSibling();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return java.lang.String
	 * @param element
	 *            org.w3c.dom.Node
	 * @param attributeName
	 *            java.lang.String
	 */
	public static String getNodeAttributeValue(Node element,
			String attributeName) {
		if (element == null) {
			return null;
		}

		Node tmpNode = element.getAttributes().getNamedItem(attributeName);
		String tmp = null;
		if (tmpNode != null)
			tmp = tmpNode.getNodeValue();
		return tmp;
	}

	/**
	 * 得到XML文件中某个Node下的第一个Text子节点的内容。
	 * 
	 * @return java.lang.String
	 * @param node
	 *            org.w3c.dom.Node
	 */
	public static String getTextData(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		Node child = node.getFirstChild();
		while (child != null && child.getNodeType() != Node.TEXT_NODE
				&& child.getNodeType() != Node.CDATA_SECTION_NODE) {
			child = child.getNextSibling();
		}

		if (child == null) {
			return null;
		}

		return ((Text) child).getData();
	}

	/**
	 * 得到XML文件中某个Node下的第一个CDATA子节点的内容。
	 * 
	 * @return java.lang.String
	 * @param node
	 *            org.w3c.dom.Node
	 */
	public static String getCDATATextData(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		Node child = node.getFirstChild();
		while (child != null && child.getNodeType() != Node.CDATA_SECTION_NODE) {
			child = child.getNextSibling();
		}

		if (child == null) {
			return null;
		}

		return ((CDATASection) child).getData();
	}

	/**
	 * 得到XML文件中某个Node下的所有child节点，限定名字为给定的名字
	 * 
	 * @param node
	 * @param childNodeName
	 * @return
	 */
	public static List<Element> childNodeList(Node node, String childNodeName) {
		if (node == null || StringUtils.isBlank(childNodeName)) {
			return null;
		}

		List<Element> children = new LinkedList<Element>();
		Node childNode = node.getFirstChild();
		if (childNode != null) {
			do {
				if (childNode.getNodeType() == Node.ELEMENT_NODE && childNodeName.equals(childNode.getNodeName())) {
					children.add((Element) childNode);
				}
			} while ((childNode = childNode.getNextSibling()) != null);
		}

		return children;
	}

	/**
	 * 得到XML文件中某个Node下的所有child节点
	 * 
	 * @param node
	 * @return
	 */
	public static List<Element> childNodeList(Node node) {
		if (node == null)
			return null;

		List<Element> children = new LinkedList<Element>();
		Node childNode = node.getFirstChild();
		if (childNode != null) {
			do {
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					children.add((Element) childNode);
				}
			} while ((childNode = childNode.getNextSibling()) != null);
		}

		return children;
	}

	/**
	 * 得到XML文件中某个Node下的指定名称的child节点，限定名字为给定的名字
	 * 
	 * @param node
	 * @param childNodeName
	 * @return
	 */
	public static Element childNodeByTag(Node node, String childNodeName) {
		if (node == null || StringUtils.isBlank(childNodeName)) {
			return null;
		}

		Node childNode = node.getFirstChild();
		if (childNode != null) {
			do {
				if (childNode.getNodeType() == Node.ELEMENT_NODE
						&& childNodeName.equals(childNode.getNodeName())) {
					return (Element) childNode;
				}
			} while ((childNode = childNode.getNextSibling()) != null);
		}

		return null;
	}

	/**
	 * 用遍历字符串的方式获取XML中第一个指定标签的值，支持CDATA类型的值
	 * <p>
	 * 
	 * @param xml
	 *            待检索的XML
	 * @param elementName
	 *            指定标签
	 * @return 标签值
	 */
	public static String getElementText(String xml, String elementName) {
		if (StringUtils.isBlank(xml) || StringUtils.isBlank(elementName)) {
			return null;
		}

		elementName = StringUtils.trimToEmpty(elementName);

		String elemStartTag = new StringBuilder("<").append(elementName)
				.append('>').toString();
		String elemEndTag = new StringBuilder("</").append(elementName)
				.append('>').toString();

		int pos = xml.indexOf(elemStartTag);
		if (pos == -1) {
			return null;
		}
		pos += elemStartTag.length();

		int pos2 = xml.indexOf(elemEndTag);
		if (pos2 == -1 || pos2 <= pos) {
			return null;
		}

		String elemValue = StringUtils.trimToEmpty(xml.substring(pos, pos2));

		// 本字段是CDATA
		if (elemValue.startsWith(XML_CDATA_START)
				&& elemValue.endsWith(XML_CDATA_END)) {
			elemValue = elemValue.substring(XML_CDATA_START.length(),
					elemValue.length() - XML_CDATA_END.length());
		}

		return elemValue;
	}

	/**
	 * 用遍历字符串的方式设置XML中第一个指定标签的值，支持CDATA类型的值
	 * <p>
	 * 
	 * @param xml
	 *            待检索的XML
	 * @param elementName
	 *            指定标签
	 * @param value
	 *            设置值
	 * @param isCDATA
	 *            是否是CDATA类型
	 */
	public static String setElementText(String xml, String elementName,
			String value, boolean isCDATA) {
		if (StringUtils.isBlank(xml) || StringUtils.isBlank(elementName)) {
			return xml;
		}

		elementName = StringUtils.trimToEmpty(elementName);

		String elemStart = "<" + elementName;
		int elemStartLength = elemStart.length();
		String elemEndTag = new StringBuilder("</").append(elementName)
				.append('>').toString();
		int elemEndTagLength = elemEndTag.length();

		// 指向节点开始
		int pos = xml.indexOf(elemStart);
		// 指向开始节点末尾
		int pos2 = -1;
		// 指向节点结束后一位
		int pos3 = -1;
		while (pos != -1) {
			int idx = pos + elemStartLength;
			if (xml.charAt(idx) != '>' && xml.charAt(idx) != ' '
					&& !xml.substring(idx, idx + 2).equals("/>")) {
				// 不是要找的节点，继续往前查找
				pos = xml.indexOf(elemStart, idx);
			} else {
				// 找到节点的开始位置
				int idx2 = xml.indexOf(">", idx);
				if (idx2 == -1) {
					// 找不到标签结束，报文错误
					pos = -1;
				} else if (xml.charAt(idx2 - 1) == '/') {
					// 空标签
					// 指向'/'
					pos2 = idx2 - 1;
					// 指向'>'后面一位
					pos3 = idx2 + 1;
				} else {
					// 非空标签
					// 指向'>'
					int idx3 = xml.indexOf(elemEndTag, idx2 + 1);
					if (idx3 == -1) {
						// 找不到结束标签，报文错误
						pos = -1;
					} else {
						pos2 = idx2;
						pos3 = idx3 + elemEndTagLength;
					}
				}
				break;
			}
		}

		if (pos == -1) {
			// 没有找到指定的节点或报文格式错误
			return xml;
		}

		// 返回结果的第一段，至开始标签结束（不包含'>'）
		StringBuilder sbXml = new StringBuilder(StringUtils.trimToEmpty(xml
				.substring(0, pos2)));
		if (value != null) {
			sbXml.append('>');
			if (isCDATA) {
				sbXml.append(XML_CDATA_START).append(value)
						.append(XML_CDATA_END);
			} else {
				sbXml.append(value);
			}
			// 结束标签
			sbXml.append(elemEndTag);
		} else {
			sbXml.append(" />");
		}

		return sbXml.append(xml.substring(pos3)).toString();
	}
}
