package com.liqh.commons.beanfilter.javaassist;

import java.util.Arrays;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;
 
 
/**
 * ==
 * !=
 * @author liqh
 *
 */
public class BooleanParser extends AbstractTypeParser {
	public static final String BOOL_EXP_EQ = "==";
	public static final String BOOL_EXP_NE = "!=";
	
	public String toScript(PropertyFilterDescriptor propertyFilterDescriptor) {
		String propertyName = propertyFilterDescriptor.getPropertyName() ;
		String op = propertyFilterDescriptor.getOp() ;
		String orgValue = String.valueOf(propertyFilterDescriptor.getPropertyValue()); // 字面值
		String refValue = getBooleanValue(orgValue); // true false null
		
		if(StringUtils.isBlank(op)) {
			// 过滤运算符不能为空
			throw new BeanFilterException(BFResCode.EBF0004);
		}
		
		if(!StringUtils.equals(BOOL_EXP_EQ, op) 
				&& !StringUtils.equals(BOOL_EXP_NE, op)
				&& !StringUtils.equals(OP_NULL_EQUAL, op)
				&& !StringUtils.equals(OP_NULL_UNEQUAL, op)) {
			// 布尔型过滤运算符错误,当前为:{0},有效取值为:{1},{2}
			throw new BeanFilterException(BFResCode.EBF0005, op, Arrays.toString(new String[]{BOOL_EXP_EQ, BOOL_EXP_NE}));
		}
		
	 
		StringBuffer script = new StringBuffer();
		script.append("propertyValue = org.apache.commons.beanutils.BeanUtils.getProperty($1,\"").append(propertyName).append("\");").append(CRLF);
		script.append("System.out.println(propertyValue);").append(CRLF);
		this.setBooleanVariableName(genBooleanVariableName(propertyName)) ;
		
		// boolean booleanVariableX = (propertyValue == null)?(refValue == null? true : false):(refValue == null?false : Boolean.parseBoolean(propertyValue) ${op} Boolean.parseBoolean(refValue))
/**		script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue==null)?");
		script.append("(").
		append("refValue == null ? true : false").
		append(")").
		append(":").
		append("(").
		append("refValue == null ? false : Boolean.parseBoolean(propertyValue)").append(op).append("Boolean.parseBoolean(refValue)").
		append(");").append(CRLF);*/
		
		if(VAL_NULL.equalsIgnoreCase(orgValue)) {
			if(StringUtils.equals(OP_NULL_EQUAL, op) || StringUtils.equals(OP_NULL_UNEQUAL, op)) {// refValue为空
				script.append("refValue = null;").append(CRLF);
				script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue").append(op).append("refValue);").append(CRLF);				
			} else {
				// 空值参与过滤时运算符错误,当前为:{0},有效值值为:{1}
				throw new BeanFilterException(BFResCode.EBF0009, op, Arrays.toString(new String[]{OP_NULL_EQUAL, OP_NULL_UNEQUAL }));
			}
		} else {
			script.append("refValue = \"").append(refValue).append("\";").append(CRLF);
			// boolean booleanVariableX = (propertyValue == null)?(propertyValue ${op} refValue):(refValue == null?false : propertyValue.${op}(refValue))
			script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue==null)?");
			script.append("(").
			append(" propertyValue ").append(op).append(" refValue ").
			append(")").
			append(":").
			append("(");
	//		append("refValue == null ? false : propertyValue.").append(op).append("(refValue)").
			
			
			if(StringUtils.equals(OP_NULL_EQUAL, op) || StringUtils.equals(OP_NULL_UNEQUAL, op)){
				script.append("org.apache.commons.lang3.StringUtils.isBlank(refValue)?propertyValue").append(op).append("refValue:")
				.append(" Boolean.parseBoolean(propertyValue) ").append(op).append(" Boolean.parseBoolean(refValue) ");
			}else{
				script.append(" Boolean.parseBoolean(propertyValue) ").append(op).append(" Boolean.parseBoolean(refValue) ");
			}
			
	//		append("refValue == null ? (").append("refValue ").append(op).append(" propertyValue").append(") : propertyValue.").append(op).append("(refValue)").
			script.append(");").append(CRLF);
			script.append("System.out.println(").append("\"").append(this.getBooleanVariableName()).append("=\"+").append(this.getBooleanVariableName()).append(");").append(CRLF);
		}
	
			return script.toString() ;
		}


 	private String getBooleanValue(String value) {
 		if(StringUtils.isBlank(value) || VAL_NULL.equalsIgnoreCase(value)) {
 			return  null;
 		}
 		
 		if("true".equalsIgnoreCase(value) 
 				|| "Y".equalsIgnoreCase(value) 
 				|| "yes".equalsIgnoreCase(value) 
 				|| "1".equalsIgnoreCase(value) 
 				||"affirmtive".equalsIgnoreCase(value) 
 				||"available".equalsIgnoreCase(value)){
 			return String.valueOf(Boolean.TRUE) ;
 		}
 		return String.valueOf(Boolean.FALSE) ; 		
	}

 
}
