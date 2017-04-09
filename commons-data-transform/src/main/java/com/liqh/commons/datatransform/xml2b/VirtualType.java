package com.liqh.commons.datatransform.xml2b;

import java.lang.reflect.Method;

/**
 * <pre>
 * abstract节点配置信息
 * 
 * eg.
 * default-in-response.xml
 * <structure node-name="TX_BODY" field="msgBody"
 * 				type="datatransform.message.TxRequestMsgBody" is-wrap="true">
 * 				<abstract id="abstract-in-request-common" />
 * 				<abstract id="abstract-in-request-entity" />
 * 			</structure>
 * @author qhlee
 * @versioin v1.0 2015年8月17日
 * @see
 */
public class VirtualType implements TypeDefinition {
	public static final int TYPE = -1;

	private TypeDefinition parent;

	public String abstractId;

	public String getAbstractId() {
		return abstractId;
	}

	public void setAbstractId(String abstractId) {
		this.abstractId = abstractId;
	}

	@Override
	public void bind(BindingContext context, String value) {

	}

	@Override
	public Class<?> getActualType() {

		return null;
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
	public String getNodeName() {
		return this.abstractId;
	}

	@Override
	public TypeDefinition getParent() {
		return this.parent;
	}

	@Override
	public String getProperty() {

		return null;
	}

	@Override
	public Method getSetterMethod() {

		return null;
	}

	@Override
	public int getType() {
		return TYPE;
	}

	@Override
	public boolean isWraped() {
		return false;
	}

	@Override
	public void setParent(TypeDefinition rule) {
		this.parent = rule;
	}

	@Override
	public void setSetterMethod(Method m) {

	}

	@Override
	public String getOverrideId() {
		return null;
	}

	@Override
	public Method getGetterMethod() {
		return null;
	}

	@Override
	public void setGetterMethod(Method m) {

	}

	@Override
	public void setNodeName(String nodeName) {

	}
}
