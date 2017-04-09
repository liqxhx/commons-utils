package com.liqh.commons.datatransform.xml2b;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.datatransform.utils.Utils;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class CollectionType extends AbstractTypeDefinition {

	private Map<String, TypeDefinition> ruleMap = new HashMap<String, TypeDefinition>();

	public static final int TYPE = 2;

	@SuppressWarnings("rawtypes")
	@Override
	public void bind(BindingContext context, String value) {

		Object pValue = context.getNavStack().peek();// Looks at the object at the top of this stack without removing it from the stack.
		try {
			Object theValue = null;
			if (getterMethod != null) {
				theValue = Utils.invokeMethod(pValue, getterMethod); //theValue =  pValue.getXXX()
			} else if (pValue instanceof Map) {
				theValue = ((Map) pValue).get(property); // theValue = pValue.get(property);
			} else {
				// 无法识别对象抽取的方法
				throw new DataTransformException(DTResCode.EDT0012);
			}

			if (theValue == null) {
				theValue = this.getActualType().newInstance();

				// 绑定到父对象
				super.doBind(pValue, theValue);
			}

			context.getNavStack().push(theValue);
		} catch (CommonRuntimeException cre) {
			throw cre;
		} catch (Throwable t) {
			throw new DataTransformException(DTResCode.EDT0013, t, pValue.getClass().getName(), getActualType(), property);
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
