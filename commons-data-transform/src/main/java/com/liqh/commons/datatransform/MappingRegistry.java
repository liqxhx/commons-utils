package com.liqh.commons.datatransform;


/**
 * 
 * <pre>
 * 数据转换注册接口
 * 
 * @author qhlee
 * @versioin v1.0 2016年6月14日
 * @see
 */
public interface MappingRegistry {
	/**
	 * 获取数据转换映射规则
	 * <p>
	 * 
	 * @param mappingId
	 *            规则Id
	 * @return 映射规则
	 */
	Mapping<?> getMapping(String mappingId);

	/**
	 * 添加数据转换映射规则
	 * <p>
	 * 
	 * @param mapping
	 *            数据转换映射规则
	 */
	void addMapping(Mapping<?> mapping);

	/**
	 * 添加或替换数据转换映射规则
	 * <p>
	 * 
	 * @param mapping
	 *            数据转换映射规则
	 */
	void addOrReplaceMapping(Mapping<?> mapping);
}
