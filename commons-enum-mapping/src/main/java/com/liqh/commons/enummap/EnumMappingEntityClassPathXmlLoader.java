/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.enummap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class EnumMappingEntityClassPathXmlLoader implements IEnumMapingEntityLoader {
	static final Logger logger = LoggerFactory.getLogger(EnumMappingEntityClassPathXmlLoader.class);
	
	static final String DEFAULT_MAPPING_FILE = "application/enummap/enumMap.xml";
	private String enumMapFileName ;
	final static XStream xs = new XStream();
	static {
		xs.alias("entity", EnumMappingEntity.class);
		xs.alias("map", EnumMap.class);
	}
	
	public EnumMappingEntityClassPathXmlLoader(String file) {
		if(file == null || file.trim().length() == 0) {
			file = DEFAULT_MAPPING_FILE;
		}
		this.enumMapFileName = file;
		logger.debug(enumMapFileName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EnumMappingEntity> load() throws EnumMappingException {
/*		File file = new File(enumMapFileName);
		if (!file.exists()) {
			// 映射配置文件{0}不存在
			throw new EnumMappingException("EEM0002", enumMapFileName);
		}*/
		
		InputStream in = null;
		try {
//			EnumMappingEntityXstreamFileLoader.class.getResource(name)
//			in = EnumMappingEntityXstreamFileLoader.class.getResourceAsStream(enumMapFileName.startsWith("/")? enumMapFileName : "/"+enumMapFileName);
			in = EnumMappingEntityClassPathXmlLoader.class.getClassLoader().getResourceAsStream(enumMapFileName.startsWith("/") ? enumMapFileName.substring(1) : enumMapFileName);
//			EnumMappingEntityXstreamFileLoader.class.getResource(enumMapFileName).
//			in = new FileInputStream(new File(enumMapFileName));
		} catch (Exception e) {
			// 找不到映射配置文件{0}
			throw new EnumMappingException("EEM0001", enumMapFileName);
		} 
		
		InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));

		List<EnumMappingEntity> ret =  (List<EnumMappingEntity>) xs.fromXML(reader);
		
		if(in != null)
			try {
				in.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		return ret ;
	}

}
