/**
 * 
 */
package com.liqh.commons.datatransform;

import com.liqh.commons.datatransform.constant.DTResCode;

public abstract class AbstractTransformer<S, T, R extends Rule> implements DataTransformer<S, T, R> {

	/**
	 * <pre>
	 * 获取唯一的映射规则
	 * 
	 * @param mapping 映射
	 * @return 映射规则
	 */
	public R getSingleMappingRule(Mapping<R> mapping) {
		// 无法完成转换工作，转换规则定义必须有且仅有一个
		// 底层规则，只有一个
		if (mapping.getRules() == null || mapping.getRules().size() != 1) {
			throw new DataTransformException(DTResCode.EDT0005);
		}

		return mapping.getRules().get(0);
	}
}
