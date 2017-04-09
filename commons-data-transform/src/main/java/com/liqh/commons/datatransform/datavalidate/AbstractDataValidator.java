package com.liqh.commons.datatransform.datavalidate;

public abstract class AbstractDataValidator implements DataValidator {

	/*
	 * 是否为空
	 */
	protected static final String IS_NULL = "is-null";
	
	/**
	 * 设置失败规则
	 * <p>
	 * 
	 * @param value
	 * @param rule
	 * @param dataType
	 */
	public void setFailRule(String value, String rule, DataType dataType) {
		((AbstractDataType)dataType).setFailRule((new StringBuilder(rule)).append('=')
				.append(value).toString());
	}
	/**
	 * 判断字段是否为空
	 * <p>
	 * 
	 * @param dataType
	 * @return
	 */
	public boolean checkNull(DataType dataType){
		String isNull=((AbstractDataType)dataType).getIsNull();
		if(isNull!=null){
			if(Boolean.parseBoolean(isNull)){//is-null=true,允许为空
				return true;
			}else{
				setFailRule(isNull, IS_NULL, dataType);
				return false;
			}			
		}else{
			return true;
		}
	}
}
