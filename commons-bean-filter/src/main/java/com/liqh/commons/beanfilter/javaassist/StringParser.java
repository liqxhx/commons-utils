package com.liqh.commons.beanfilter.javaassist;

import java.util.Arrays;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;

/**
 * equals matches contains
 * 
 * @author liqh
 * 
 */
public class StringParser extends AbstractTypeParser {
	public static final String OP_STR_EXP_EQ = "equals";
	public static final String OP_STR_EXP_EQI = "equalsIgnoreCase";
	public static final String OP_STR_EXP_CONTAINS = "contains";
	public static final String OP_STR_EXP_MATCHES = "matches";

	public String toScript(PropertyFilterDescriptor propertyFilterDescriptor) {	
		String propertyName = propertyFilterDescriptor.getPropertyName();
		String op = propertyFilterDescriptor.getOp(); // equals matchs contains
		String value = String.valueOf(propertyFilterDescriptor.getPropertyValue());		
		
		if(StringUtils.isBlank(op)) {
			// 过滤运算符不能为空
			throw new BeanFilterException(BFResCode.EBF0004);
		}
		
		if(!StringUtils.equals(OP_STR_EXP_EQ, op) 
				&& !StringUtils.equals(OP_STR_EXP_EQI, op)
				&& !StringUtils.equals(OP_STR_EXP_CONTAINS, op)
				&& !StringUtils.equals(OP_STR_EXP_MATCHES, op)
				&& !StringUtils.equals(OP_NULL_EQUAL, op)
				&& !StringUtils.equals(OP_NULL_UNEQUAL, op)) {
			// 文本型过滤运算符错误,当前为:{0},有效取值为:{1}
			throw new BeanFilterException(BFResCode.EBF0007, op, Arrays.toString(new String[]{OP_STR_EXP_EQ, OP_STR_EXP_EQI, OP_STR_EXP_CONTAINS, OP_STR_EXP_MATCHES, OP_NULL_EQUAL, OP_NULL_UNEQUAL }));
		}
		
		StringBuffer script = new StringBuffer();
		script.append("propertyValue = org.apache.commons.beanutils.BeanUtils.getProperty($1,\"").append(propertyName).append("\");").append(CRLF);
		//script.append("propertyValue = org.apache.commons.lang3.StringUtils.isBlank(propertyValue)?null:propertyValue;").append(CRLF);
		this.setBooleanVariableName(genBooleanVariableName(propertyName));
			
//		if(NULL.equalsIgnoreCase(value)){
//		 	script.append("refValue = null;").append(CRLF);		 
//		}else{
//			script.append("refValue = \"").append(value).append("\";").append(CRLF);
//		}
		if(VAL_NULL.equalsIgnoreCase(value)) {
				if(StringUtils.equals(OP_NULL_EQUAL, op) || StringUtils.equals(OP_NULL_UNEQUAL, op)) {// refValue为空
					script.append("refValue = null;").append(CRLF);
					script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue").append(op).append("refValue);").append(CRLF);				
				}else{
					// 空值参与过滤时运算符错误,当前为:{0},有效值值为:{1}
					throw new BeanFilterException(BFResCode.EBF0009, op, Arrays.toString(new String[]{OP_NULL_EQUAL, OP_NULL_UNEQUAL }));
			}
		} else {
			script.append("refValue = \"").append(value).append("\";").append(CRLF);
			// boolean booleanVariableX = (propertyValue == null)?(propertyValue ${op} refValue):(refValue == null?false : propertyValue.${op}(refValue))
			script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue==null)?");
			script.append("(").
//			append("refValue == null ? true : false").
			append("false").
//			append(" propertyValue ").append(op).append(" refValue ").
			append(")").
			append(":").
			append("(").
//			append("refValue == null ? false : propertyValue.").append(op).append("(refValue)").
			append("propertyValue.").append(op).append("(refValue)").
//			append("refValue == null ? (").append("refValue ").append(op).append(" propertyValue").append(") : propertyValue.").append(op).append("(refValue)").
			append(");").append(CRLF);
			script.append("System.out.println(").append("\"").append(this.getBooleanVariableName()).append("=\"+").append(this.getBooleanVariableName()).append(");").append(CRLF);
		}
		

		return script.toString();
	}
	
	

}
