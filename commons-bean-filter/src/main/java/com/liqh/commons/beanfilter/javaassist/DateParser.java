package com.liqh.commons.beanfilter.javaassist;

import java.util.Arrays;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.utils.StringUtils;


public class DateParser extends AbstractTypeParser {
	public static final String DATE_EXP_EQ = "equals";
	public static final String DATE_EXP_BEFORE= "before";
	public static final String DATE_EXP_AFTER = "after";
	
	public static final String DATE_EXP_LE = "<="; // before or equals
	public static final String DATE_EXP_GE = ">=";// after or equals
	
	public DateParser(){super();}
	@Override
	public String toScript(PropertyFilterDescriptor propertyFilterDescriptor) {

		String propertyName = propertyFilterDescriptor.getPropertyName() ;
		String op =validateOp(propertyFilterDescriptor.getOp() ); //before after
		String refValue = String.valueOf(propertyFilterDescriptor.getPropertyValue());
		
		if(StringUtils.isBlank(op)) {
			// 过滤运算符不能为空
			throw new BeanFilterException(BFResCode.EBF0004);
		}
		
		if(!StringUtils.equals(DATE_EXP_EQ, op) 
				&& !StringUtils.equals(DATE_EXP_BEFORE, op)
				&& !StringUtils.equals(DATE_EXP_AFTER, op)
				&& !StringUtils.equals(OP_NULL_EQUAL, op)
				&& !StringUtils.equals(OP_NULL_UNEQUAL, op)
				&& !StringUtils.equals(DATE_EXP_LE, op)
				&& !StringUtils.equals(DATE_EXP_GE, op)) {
			// 日期型过滤运算符错误,当前为:{0},有效取值为:{1}
			throw new BeanFilterException(BFResCode.EBF0008, op, Arrays.toString(new String[]{DATE_EXP_EQ, DATE_EXP_BEFORE, DATE_EXP_AFTER, DATE_EXP_LE, DATE_EXP_GE}));
		}
	
	 
		StringBuffer script = new StringBuffer();
		script.append("propertyValue = org.apache.commons.beanutils.BeanUtils.getProperty($1,\"").append(propertyName).append("\");").append(CRLF);
		script.append("System.out.println(propertyValue);");
		//script.append("if(propertyValue instanceof java.util.Date){propertyValue = ;}");
		script.append("String[] pattern = {\"yyyy-MM-dd HH:mm:ss\", \"yyyy/MM/dd HH:mm:ss\"};").append(CRLF);
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
		
		// boolean booleanVariableX = (propertyValue == null)?(refValue == null? true : false):(refValue == null?false : Boolean.parseBoolean(propertyValue) ${op} Boolean.parseBoolean(refValue))
		script.append("boolean ").append(this.getBooleanVariableName()).append(" = (propertyValue==null)?");
		script.append("(").
		append(" false ").
		append(")").
		append(":").
		append("(");
		if(DATE_EXP_LE.equals(op)) {
			script.append("(org.apache.commons.lang3.time.DateUtils.parseDate(propertyValue, pattern)).before(org.apache.commons.lang3.time.DateUtils.parseDate(refValue, pattern)) ||").
			append("(org.apache.commons.lang3.time.DateUtils.parseDate(propertyValue, pattern)).equals(org.apache.commons.lang3.time.DateUtils.parseDate(refValue, pattern))");
	
		}else if(DATE_EXP_GE.equals(op)){
			script.append("(org.apache.commons.lang3.time.DateUtils.parseDate(propertyValue, pattern)).after(org.apache.commons.lang3.time.DateUtils.parseDate(refValue, pattern)) ||").
			append("(org.apache.commons.lang3.time.DateUtils.parseDate(propertyValue, pattern)).equals(org.apache.commons.lang3.time.DateUtils.parseDate(refValue, pattern))");
		}else{
			script.append("(org.apache.commons.lang3.time.DateUtils.parseDate(propertyValue, pattern)).").append(op).append("(org.apache.commons.lang3.time.DateUtils.parseDate(refValue, pattern))");
		}
		
		script.append(");").append(CRLF);
		script.append("System.out.println(").append("\"").append(this.getBooleanVariableName()).append("=\"+").append(this.getBooleanVariableName()).append(");").append(CRLF);
		}
		return script.toString() ;
	}

	private String validateOp(String op) {
		String ret = op ;
		if("<".equalsIgnoreCase(op)){
			ret = "before";
		}else if(">".equalsIgnoreCase(op)){
			ret = "after" ;
		}else if("=".equalsIgnoreCase(op)){
			ret = "equals";
		}
		return ret ;
	}
	
	

}
