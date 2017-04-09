package com.liqh.commons.beanfilter;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class TestXPath {
	@SuppressWarnings("unused")
	@Test
	public void testFilterBuilder() throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true); // never forget this!  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        URL url = TestXPath.class.getResource("/framework/filter/rule.xml") ; 
        Document doc = builder.parse(url.toURI().toString());   
        
        XPathFactory pathFactory = XPathFactory.newInstance();          
        XPath xpath = pathFactory.newXPath(); 
        
        
        XPathExpression pathExpression = xpath.compile("//rule[@name]");  
        Object result = pathExpression.evaluate(doc, XPathConstants.NODE);
        Node n = (Node)result ;
        NamedNodeMap nameMap= n.getAttributes() ;
      
        System.out.println(n.getAttributes().getLength());
        System.out.println("===");
        /*Object result = pathExpression.evaluate(doc, XPathConstants.NODESET);  
        NodeList nodes = (NodeList) result; 
         
        System.out.println(nodes.getLength());*/
	}
}
