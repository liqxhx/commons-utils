package com.liqh.commons.datatransform.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.MappingRegistry;
import com.liqh.commons.lang.i18n.I18n;

/**
 * 数据转换注册器默认实现
 * <p>
 * 
 * @author qhlee
 * @version 1.0 2015-08-14
 * @see
 */
@SuppressWarnings({ "rawtypes"})
public class DefaultMappingRegistry implements MappingRegistry {
	protected static final Logger logger = LoggerFactory.getLogger(DefaultMappingRegistry.class);
	private Map<String, Mapping> mappingContainer = new HashMap<String, Mapping>(600);

	@Override
	public void addMapping(Mapping mapping) {
		if (!mappingContainer.containsKey(mapping.getId())) {
			this.mappingContainer.put(mapping.getId(), mapping);
		} else {
			// 报文转换规则中存在重复mapping-id，重复的mapping-id为@@{0}@@
			throw new DataTransformConfigParseException("DT0003", mapping.getId());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Mapping getMapping(String mappingId) {
		return this.mappingContainer.get(mappingId);
	}

	public void addOrReplaceMapping(Mapping mapping) {
		if (mappingContainer.containsKey(mapping.getId())) {
			// 报文转换规则中存在重复mapping-id，重复的mapping-id为@@{0}@@
			logger.debug(I18n.getMessage("DT0003", mapping.getId()));
		} 
		this.mappingContainer.put(mapping.getId(), mapping);
	}

}
