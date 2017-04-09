package com.liqh.commons.datatransform.xml2b;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.constant.DTResCode;

public class StructureType extends AbstractTypeDefinition {
	public static final int TYPE = 1;
	/**
	 * <pre>
	 * ruleMap保存当前structure下的映射规则
	 * <structure node-name="TEACHER" type="x.y.z.TEACHER" is-wrap="true">
	 * 		<value node-name="ID" field="id" datetype="TextType" is-null="N"/>
	 * 		<value node-name="NAME" field="name" datetype="TextType"/>
	 * 		<collection node-name="STUDENT" type="java.util.ArrayList" is-wrap="true">
	 * 			
	 * 		</collection>
	 * 		<structure node-name="SCHOOL" type="x.y.z.School">
	 * 			<value node-name="ID" field="id" datetype="TextType" is-null="N"/>
	 * 			<value node-name="NAME" field="name" datetype="TextType"/>
	 * 		</structure>
	 * </structure>
	 * 
	 * key:node-name
	 * value: StructureType, ValueType, CollectionType, VirtualType
	 */
	private Map<String, TypeDefinition> ruleMap = new HashMap<String, TypeDefinition>();

	@Override
	public void bind(BindingContext context, String value) {
		Object pValue = context.getNavStack().peek();// just look, without removing 
		try {
			Object theValue = this.getActualType().newInstance();
			context.getNavStack().push(theValue);

			// 绑定到父对象
			super.doBind(pValue, theValue);
		} catch (Throwable t) {
			// 绑定结构类型数据时发生异常, 可能是该属性在对应类中不存在,或者该属性的类型不正确! Class:{0},
			// PropertyType:{1}, Property:{2}
			throw new DataTransformException(DTResCode.EDT0014, pValue.getClass(), getActualType(), getProperty());
		}
	}

	@Override
	public TypeDefinition getChild(String nodeName) {
		return this.ruleMap.get(nodeName);
	}

	@Override
	public TypeDefinition[] getChildren(String nodeName) {
		return null;
	}

	public Map<String, TypeDefinition> getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(Map<String, TypeDefinition> ruleMap) {
		if (ruleMap != null) {
			this.ruleMap.putAll(ruleMap);
		}
	}

	@Override
	public int getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		if (!ruleMap.isEmpty()) {
			for (String subRuleKey : ruleMap.keySet()) {
				TypeDefinition subRule = ruleMap.get(subRuleKey);
				sb.append(new StringBuilder(subRule.toString()).insert(1,
						new StringBuilder("subRuleKey=").append(subRuleKey)
								.append('\n')));
			}
		}
		return sb.toString();
	}
}
