package com.liqh.commons.datatransform;

/**
 * 
 * <pre>
 * 将S按规则R转换为T
 * @author qhlee
 * @versioin v1.0 2016年6月14日
 * @see
 * @param
 * <S> 输入参数类型
 * @param <T> 输出参数类型
 * @param <R> 规则类型
 */
public interface DataTransformer<S, T, R extends Rule> {
	/**
	 * 
	 *<P>
	 * @param source 要转换的对象
	 * @param mapping 转换映射规则
	 * @return 转换后数据对象
	 */
	public T transform(S source, Mapping<R> mapping);
}
