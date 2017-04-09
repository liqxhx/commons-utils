package com.liqh.commons.datatransform.datavalidate;

public interface DataValidator {

	/**
	 * 
	 * 报文校验
	 * <p>
	 * 
	 * @param value
	 *            报文字段值
	 * @param dataType
	 *                数据类型
	 * @return true 校验通过 false 校验未通过
	 */
	public boolean doValidate(String value,DataType dataType);
	
	/**
	 * 校验报文字段是否为空
	 * <p>
	 * 
	 * @param dataType
	 *                数据类型
	 * @return
	 */
	public boolean checkNull(DataType dataType);
}
