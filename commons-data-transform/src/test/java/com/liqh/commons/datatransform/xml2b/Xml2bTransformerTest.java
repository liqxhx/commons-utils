/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.xml2b;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.liqh.commons.datatransform.BaseDataTransformerTest;
import com.liqh.commons.datatransform.model.DataBean;

@SuppressWarnings("unchecked")
public class Xml2bTransformerTest extends BaseDataTransformerTest {

	@BeforeClass
	public static void init() throws Exception {
		System.out.println(UUID.randomUUID().toString());
		String configurations[] = new String[1];
		configurations[0] = "framework/datatransform/xml2b/mappings.xml";
		BaseDataTransformerTest.init(new XmlToBeanTransformer(), new XmlToBeanRuleParser(), configurations);
		
	}

	@Test
	public void testXml2b() throws URISyntaxException, IOException{
		URL url = Xml2bTransformerTest.class.getClassLoader().getResource("framework/datatransform/xml2b/bean.xml");
		File file = new File(url.toURI());
		String xml = FileUtils.readFileToString(file, "UTF-8");
		System.out.println(xml);
		System.out.println("=================================================");
		DataBean bean = (DataBean) getTransformer().transform(xml, getMappingRegistry().getMapping("testXml2b"));
		System.out.println(bean);
	}
	
}
