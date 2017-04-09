package com.liqh.commons.datatransform.xml2b;

import java.lang.reflect.Method;

import com.liqh.commons.datatransform.Rule;

/**
 * <pre>
 * XML to bean配置信息接口
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public interface TypeDefinition extends Rule {

	/**
	 * 绑定值
	 * <p>
	 * 
	 * @param context
	 *            转换上下文
	 * @param value
	 *            待绑定值
	 */
	void bind(BindingContext context, String value);

	/**
	 * 获取setter方法
	 * <p>
	 * 
	 * @return setter方法
	 */
	Method getSetterMethod();

	/**
	 * 设置setter方法
	 * <p>
	 * 
	 * @param m
	 *            setter方法
	 */
	void setSetterMethod(Method m);

	/**
	 * 获取getter方法
	 * <p>
	 * 
	 * @return getter方法
	 */
	Method getGetterMethod();

	/**
	 * 设置getter方法
	 * <p>
	 * 
	 * @param m
	 *            getter方法
	 */
	void setGetterMethod(Method m);

	/**
	 * 获取子节点配置
	 * <p>
	 * 
	 * @param nodeName
	 *            子节点名
	 * @return 子节点配置
	 */
	TypeDefinition getChild(String nodeName);

	/**
	 * 获取多个子节点配置
	 * <p>
	 * 
	 * @param nodeName
	 *            子节点名
	 * @return 子节点配置
	 */
	TypeDefinition[] getChildren(String nodeName);

	/**
	 * 是否是包装节点
	 * <p>
	 * 
	 * @return
	 */
	boolean isWraped();

	/**
	 * 获取配置类型
	 * <p>
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 获取配置对应的类
	 * <p>
	 * 
	 * @return
	 */
	Class<?> getActualType();

	/**
	 * 获取属性名
	 * <p>
	 * 
	 * @return
	 */
	String getProperty();

	/**
	 * 获取节点名
	 * <p>
	 * 
	 * @return
	 */
	String getNodeName();

	/**
	 * 设置节点名
	 * <p>
	 * 
	 * @return
	 */
	void setNodeName(String nodeName);

	/**
	 * 设置父节点
	 * <p>
	 * 
	 * @param rule
	 */
	void setParent(TypeDefinition rule);

	/**
	 * 获取父节点
	 * <p>
	 * 
	 * @return
	 */
	TypeDefinition getParent();

	/**
	 * 获取覆盖Id
	 * <p>
	 * 
	 * @return
	 */
	String getOverrideId();

}
