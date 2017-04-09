package com.liqh.commons.datatransform;

/**
 * 数据转换管理器接口，数据转换统一接口
 * 
 * @author qhlee
 * @version 1.0 2015-08-14
 * @see
 */
public interface DataTransformManager {
	/**
	 * 转换数据
	 * <p>
	 * 
	 * @param source
	 *            原始数据
	 * @param mappindId
	 *            转换规则Id
	 * @return 转换后的数据对象
	 */
	Object transform(Object source, String mappindId);

	/**
	 * 根据Id获取数据转换规则
	 * <p>
	 * 
	 * @param mappindId
	 *            数据转换规则Id
	 * @return 数据转换规则
	 */
	Mapping<?> getMapping(String mappindId);
}
