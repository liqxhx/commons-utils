package com.liqh.commons.beanfilter.javaassist;

import java.util.Arrays;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;


public class NumbericParser extends AbstractTypeParser {
	public static final String NUMB_EXP_EQ = "=="; // equal
	public static final String NUMB_EXP_NE = "!="; // not equal
	public static final String NUMB_EXP_GE = ">="; // greater equal
	public static final String NUMB_EXP_GT = ">"; // greater than
	public static final String NUMB_EXP_LE= "<="; // less equal
	public static final String NUMB_EXP_LT= "<";	// less than

	@Override
	public String toScript(PropertyFilterDescriptor propertyFilterDescriptor) {
		String propertyName = propertyFilterDescriptor.getPropertyName() ;
		String op = propertyFilterDescriptor.getOp() ; 
		String refValue = String.valueOf(propertyFilterDescriptor.getPropertyValue());
	
		
		if(StringUtils.isBlank(op)) {
			// 过滤运算符不能为空
			throw new BeanFilterException(BFResCode.EBF0004);
		}
		
		if(!StringUtils.equals(NUMB_EXP_EQ, op) 
				&& !StringUtils.equals(NUMB_EXP_NE, op)
				&& !StringUtils.equals(NUMB_EXP_GE, op)
				&& !StringUtils.equals(NUMB_EXP_GT, op)
				&& !StringUtils.equals(NUMB_EXP_LE, op)
				&& !StringUtils.equals(NUMB_EXP_LT, op)
				&& !StringUtils.equals(OP_NULL_EQUAL, op)
				&& !StringUtils.equals(OP_NULL_UNEQUAL, op)
				) {
			// 数值型过滤运算符错误,当前为:{0},有效取值为:{1}
			throw new BeanFilterException(BFResCode.EBF0006, op, Arrays.toString(new String[]{NUMB_EXP_EQ, NUMB_EXP_NE, NUMB_EXP_GE, NUMB_EXP_GT, NUMB_EXP_LE, NUMB_EXP_LT}));
		}
		
 
		 
		StringBuffer script = new StringBuffer();
		script.append("propertyValue = org.apache.commons.beanutils.BeanUtils.getProperty($1,\"").append(propertyName).append("\");").append(CRLF);	 
		script.append("propertyValue = org.apache.commons.lang3.StringUtils.isBlank(propertyValue)?null:propertyValue;").append(CRLF);
		script.append("System.out.println(propertyValue);").append(CRLF);	 
		this.setBooleanVariableName(genBooleanVariableName(propertyName)) ;
 
		if(VAL_NULL.equalsIgnoreCase(refValue)) {
			if(StringUtils.equals(OP_NULL_EQUAL, op) || StringUtils.equals(OP_NULL_UNEQUAL, op)) {// refValue为空
				script.append("refValue = null;").append(CRLF);
				script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue").append(op).append("refValue);").append(CRLF);				
			} else {
				// 空值参与过滤时运算符错误,当前为:{0},有效值值为:{1}
				throw new BeanFilterException(BFResCode.EBF0009, op, Arrays.toString(new String[]{OP_NULL_EQUAL, OP_NULL_UNEQUAL }));
			}
		} else {
			script.append("refValue = \"").append(refValue).append("\";").append(CRLF);
			// Boolean booleanVariableX = (propertyValue == null)?(refValue == null? true : false):(refValue == null?false : Double.parseDouble(propertyValue) ${op} Double.parseDouble(refValue))
			script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue==null)?");
			script.append("(");
			if(StringUtils.equals(OP_NULL_EQUAL, op) || StringUtils.equals(OP_NULL_UNEQUAL, op)) {
				script.append("propertyValue").append(op).append("refValue");
			}else{
				script.append("false");
			}
			script.append(")").
			append(":").
			append("(").
			append("Double.parseDouble(propertyValue)").append(op).append("Double.parseDouble(refValue)").
			append(");").append(CRLF);
			script.append("System.out.println(").append("\"").append(this.getBooleanVariableName()).append("=\"+").append(this.getBooleanVariableName()).append(");").append(CRLF);
		}
		
		
	return script.toString() ;
	}

 
	 
}
