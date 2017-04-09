package com.liqh.commons.enummap;

/**
 * <pre>
 * 实现枚举映射
 * 使用场景如：
 * 在Teacher类中
 * 有一个String gender;性别的属性
 * 在程序中它的取值是男、女
 * 而在入库时对应的取值为1、0
 * 
 * 如果要使用IEnumMappingManager完成转换，必须将类的属性定义为String类型
 * 默认的DefaultEnumMappingManager实现，要接合enummap/enumMap.xml使用
 * 
 * @author qhlee
 * @versioin v1.0 2016年11月9日
 * @see
 */
public interface IEnumMappingManager {

	/**
	 * 
	 *<p>
	 * 转成枚举值
	 * @param object
	 * @return
	 */
	public abstract Object convertToEnum(Object object);

	/**
	 * 
	 *<p>
	 *转成字面值
	 * @param object
	 * @return
	 */
	public abstract Object convertToString(Object object);

}