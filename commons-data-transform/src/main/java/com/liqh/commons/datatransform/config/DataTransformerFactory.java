/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.config;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.DataTransformer;
import com.liqh.commons.datatransform.bean2text.BeanToTextTransformer;
import com.liqh.commons.datatransform.xml2b.XmlToBeanTransformer;

@SuppressWarnings("rawtypes")
public final class DataTransformerFactory {
	
	private final static Map<String, DataTransformer> transformers = new HashMap<String, DataTransformer>();

	static {
		transformers.put("bean2TextTransformer", new BeanToTextTransformer());
		transformers.put("xml2BeanTransformer", new XmlToBeanTransformer());
	}
	
	public static DataTransformer getDataTransformer(String id) {
		return transformers.get(id) ;
	}
}
