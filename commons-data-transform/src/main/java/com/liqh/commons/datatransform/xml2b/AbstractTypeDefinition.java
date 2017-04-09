package com.liqh.commons.datatransform.xml2b;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.datatransform.utils.Utils;

/**
 * 转换规则的抽象实现
 *
 * <p>
 *
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public abstract class AbstractTypeDefinition implements TypeDefinition {

	/**
	 * bean的属性名
	 */
	protected String property;

	/**
	 * 报文节点名
	 */
	protected String nodeName;

	/**
	 * 转换规则对应的类
	 */
	protected Class<?> actualType;

	/**
	 * 属性setter方法
	 */
	protected Method setterMethod;

	/**
	 * 属性getter方法
	 */
	protected Method getterMethod;

	/**
	 * 是否是包装节点
	 */
	protected boolean isWraped;

	/**
	 * 父节点对应的映射规则
	 */
	protected TypeDefinition parent;

	/**
	 * 覆盖Id
	 */
	protected String overrideId;

	/**
	 * 
	 *<p>
	 * @param needBindBean 为要绑定值的javabean对象
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doBind(Object needBindBean, Object value) {
		// System.out.println("=== bind object:" + target + "  class:" +
		// target.getClass());

		if (setterMethod != null) {
			Utils.invokeMethod(needBindBean, getSetterMethod(), value); 
		} else if (needBindBean instanceof Map) {
			((Map) needBindBean).put(property, value);
		} else if (needBindBean instanceof Collection) {
			((Collection) needBindBean).add(value);
		} else {
			// 无法识别对象绑定的方法
			throw new DataTransformException(DTResCode.EDT0010);
		}
	}

	@Override
	public Method getSetterMethod() {
		return setterMethod;
	}

	@Override
	public void setSetterMethod(Method setterMethod) {
		this.setterMethod = setterMethod;
	}

	@Override
	public Method getGetterMethod() {
		return getterMethod;
	}

	@Override
	public void setGetterMethod(Method getterMethod) {
		this.getterMethod = getterMethod;
	}

	@Override
	public TypeDefinition getChild(String nodeName) {
		return null;
	}

	@Override
	public TypeDefinition[] getChildren(String nodeName) {
		return null;
	}

	@Override
	public boolean isWraped() {
		return isWraped;
	}

	public void setWraped(boolean isWraped) {
		this.isWraped = isWraped;
	}

	@Override
	public TypeDefinition getParent() {
		return parent;
	}

	@Override
	public void setParent(TypeDefinition parent) {
		this.parent = parent;
	}

	@Override
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public Class<?> getActualType() {
		return actualType;
	}

	public void setActualType(Class<?> actualType) {
		this.actualType = actualType;
	}

	@Override
	public String getOverrideId() {
		return overrideId;
	}

	public void setOverrideId(String overrideId) {
		this.overrideId = overrideId;
	}

	@Override
	public String toString() {
		return new StringBuilder("\ntype=").append(getType()).append('\n')
				.append("property=").append(property).append('\n')
				.append("nodeName=").append(nodeName).append('\n')
				.append("actualType=").append(actualType).append('\n')
				.append("setterMethod=").append(setterMethod).append('\n')
				.append("getterMethod=").append(getterMethod).append('\n')
				.append("isWraped=").append(isWraped).append('\n')
				.append("parent=")
				.append(parent == null ? null : parent.getNodeName())
				.append('\n').append("overrideId=").append(overrideId)
				.append('\n').toString();
	}
}
