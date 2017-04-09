/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.liqh.commons.datatransform.config.DefaultMappingRegistry;
import com.liqh.commons.datatransform.config.XmlConfigurationMappingParser;
import com.liqh.commons.datatransform.model.DataBean;
import com.liqh.commons.datatransform.model.Student;
import com.liqh.commons.datatransform.xml2b.Xml2bTransformerTest;
import com.liqh.commons.lang.i18n.I18n;

public class DataTransformManagerTest {
	static DefaultDataTransformManager dataTransformManager;
	static MappingRegistry mappingRegistry;
	static XmlConfigurationMappingParser xmlConfigurationMappingParser;

	@BeforeClass
	public static void init() throws Exception {
		// 自定义slf4j的变量，然后在logback.xml使用%X{key} 方式引用
		// slf4j: %X{key} 
		// log4j: %X{['key']}
		org.slf4j.MDC.put("_myId", new Random(System.currentTimeMillis()).nextInt()+""); // 设置一个全局的id
		
		I18n.init(null, "framework/resources/data_transform_err", "framework/resources/data_transform_log");
		
		String configurations[] = new String[4];
		configurations[0] = "framework/datatransform/xml2b/mappings.xml";
		configurations[1] = "framework/datatransform/bean2text/default-common.xml";
		configurations[2] = "framework/datatransform/bean2text/mappings-tojava.xml";
		configurations[3] = "framework/datatransform/bean2text/mappings-toxml.xml";
	
		xmlConfigurationMappingParser = new XmlConfigurationMappingParser();
		xmlConfigurationMappingParser.setConfigurations(configurations);
		
		mappingRegistry = new DefaultMappingRegistry();
		xmlConfigurationMappingParser.setMappingRegistry(mappingRegistry);
		
		xmlConfigurationMappingParser.parse();
		System.out.println(xmlConfigurationMappingParser.getMappingRegistry().getMapping("testXml2b")+"xxxx");;

		dataTransformManager = new DefaultDataTransformManager();
		dataTransformManager.setMappingRegistry(mappingRegistry);
	}

	@Test
	public void testXml2Bean() throws URISyntaxException, IOException{
		URL url = Xml2bTransformerTest.class.getClassLoader().getResource("framework/datatransform/xml2b/bean.xml");
		File file = new File(url.toURI());
		String xml = FileUtils.readFileToString(file, "UTF-8");
		System.out.println(xml);
		
		DataBean bean = (DataBean) dataTransformManager.transform(xml, "testXml2b");
		System.out.println(bean);
	}
	
	@Test
	public void testBean2Xml() {		
		Student student = new Student();
		student.setId(UUID.randomUUID().toString());
		student.setName("sk");
		student.setScore(100);
		
		Map<String, Student> root = new HashMap<String, Student>();
		root.put("_stu", student);

		Object ret = dataTransformManager.transform(root, "test2Xml");
		
		System.out.println(ret.getClass().getName());
		System.out.println(((java.lang.StringBuilder)ret).toString());
		
	}
	
	@Test
	public void testXml2BeanSample() throws Exception{
		URL url = Xml2bTransformerTest.class.getClassLoader().getResource("framework/datatransform/xml2b/bean.xml");
		File file = new File(url.toURI());
		String xml = FileUtils.readFileToString(file, "UTF-8");
		System.out.println(xml);
		
		System.out.println("========================split=========================");
		DataBean bean = (DataBean) dataTransformManager.transform(xml, "testXml2b-sample");
		System.out.println(bean);
	}
	
}
