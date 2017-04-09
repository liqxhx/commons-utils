package com.liqh.commons.datatransform;

import com.liqh.commons.datatransform.constant.DTResCode;

public class DefaultDataTransformManager implements DataTransformManager {
	private MappingRegistry mappingRegistry ;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object transform(Object source, String mappingId) {
		if(source == null)
			return null;
		
		Mapping mapping = getMapping(mappingId) ;
		if(mapping == null) {
			//无法找到此数据适配规则配置，mappingId={0}
			throw new DataTransformException(DTResCode.EDT0001, mappingId) ;
		}
		return mapping.getTransformer().transform(source, mapping) ;
 	}

	@Override
	public Mapping<?> getMapping(String mappingId) {
		return this.mappingRegistry.getMapping(mappingId) ;
	}

	public MappingRegistry getMappingRegistry() {
		return mappingRegistry;
	}

	public void setMappingRegistry(MappingRegistry mappingRegistry) {
		this.mappingRegistry = mappingRegistry;
	}

}
