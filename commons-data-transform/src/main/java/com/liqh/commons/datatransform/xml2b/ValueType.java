package com.liqh.commons.datatransform.xml2b;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.datatransform.ValueHandler;
import com.liqh.commons.datatransform.constant.DTResCode;
import com.liqh.commons.datatransform.datavalidate.AbstractDataType;
import com.liqh.commons.datatransform.datavalidate.DataType;
import com.liqh.commons.datatransform.datavalidate.DataValidator;
import com.liqh.commons.datatransform.datavalidate.DataValidatorUtil;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;
import com.liqh.commons.lang.utils.StringUtils;

/**
 * 
 * 
 * <pre>
 * Value节点配置信息
 * eg.
 *  default-in-response.xml
 * <mappings>
 * 	<!-- 作为服务请求方报文，需要应用自配common和entity域 -->
 * 	<abstract id="abstract_in_request_msg_without_common">
 * 		<structure node-name="TX" field="root"
 * 			type="datatransform.message.TxRequestMsg"
 * 			is-root="true" is-wrap="ture">
 * 			<structure node-name="TX_HEADER" field="msgHeader"
 * 				type="datatransform.message.TxRequestMsgHead" is-warp="true">
 * 				<value node-name="SYS_HDR_LEN" field="sys_hdr_len"/>
 * 				<value node-name="SYS_PKG_VRSN" field="sys_pkg_vrsn"/>
 * 		
 *  @author qhlee
 *  @versioin v1.0 2016年6月28日
 * @see
 */
public class ValueType extends AbstractTypeDefinition {
	private static final Logger logger = LoggerFactory.getLogger(ValueType.class);
	public static final int TYPE = 0;

	/**
	 * 值处理器，可将输入的字符串值进行加工处理，并转换成所期望的类型
	 */
	private ValueHandler valueHandler;

	/**
	 * 业务数据类型,用于报文校验
	 */
	private DataType dataType;

	/*
	 * 数据校验器
	 */
	private DataValidator dataValidator = null;

	@Override
	public void bind(BindingContext context, String value) {

			Object theValue = value;
			if (valueHandler != null) {
				theValue = valueHandler.handle(value);
			}
			// 如果已经有值处理器，则不再进行自动 类型转换
			else if (this.getActualType() != String.class) {
//				theValue = context.getConvertService().convert(value, getActualType());
				theValue = ConvertUtils.convert(value, getActualType());
			}
			
			if(logger.isTraceEnabled())
				logger.trace("当前执行转换规则明细信息：[{}] 报文中取值：[{}]", context.getRuleStack().peek(), value);
			// 报文校验
			validate(context, value);

			super.doBind(context.getNavStack().peek(), theValue);
			context.getNavStack().push(value);
			context.setValueTypePushFlag(true);
		
	}

	/**
	 * 报文校验
	 * <p>
	 * 
	 * @param context
	 * @param value
	 */
	private void validate(BindingContext context, String value) {
		// 在context中取出是否校验标识
		if (context.isValidateFlag()) {
			if (dataValidator != null) {
				long start = System.currentTimeMillis();
				try {
					logger.debug("报文校验开始,请求字段{}={}", ((AbstractDataType) dataType).getNodeName(), value);
					if (!dataValidator.doValidate(value, dataType)) {
						// 报文校验条件不满足,请求报文字段{0}={1},不满足的校验规则{2}
						String failRule=((AbstractDataType) dataType).getFailRule();
						String nodeName=((AbstractDataType) dataType).getNodeName();
						logger.error("报文校验条件不满足,请求报文字段{}={},不满足的校验规则{}",nodeName,value,failRule);
						
						//获取错误码
						String[] rule = failRule.split("=");
						String errCode = getErrCode(dataType.getType(), rule[0]);		
						if(StringUtils.hasText(errCode)){
							throw new CommonRuntimeException(errCode,nodeName,rule[1]);
						}else{
							throw new CommonRuntimeException(DTResCode.EDT0017, nodeName,value,failRule);
						}

					}
				} finally {
					logger.debug("报文校验结束，耗时={} ms", (System.currentTimeMillis() - start));
				}
			}

		}
	}


	

	public void validateNull(BindingContext context) {
		// TODO:进行报文校验，如果未通过，抛出异常
		// 在context中取出是否校验标识
		if (context.isValidateFlag()) {
			if (dataValidator != null) {
				long start = System.currentTimeMillis();
				try {
					logger.debug("报文校验开始,请求字段{}=空", ((AbstractDataType) dataType).getNodeName());
					if (!dataValidator.checkNull(dataType)) {
						
						String failRule=((AbstractDataType) dataType).getFailRule();
						String nodeName=((AbstractDataType) dataType).getNodeName();
						logger.error("报文校验条件不满足,请求报文字段{}={},不满足的校验规则{}",nodeName,"null",failRule);
						
						//获取错误码
						String[] rule = failRule.split("=");
						String errCode = getErrCode(dataType.getType(), rule[0]);
						if (StringUtils.hasText(errCode)) {
							throw new CommonRuntimeException(errCode, nodeName,rule[1]);
						} else {
							//报文校验条件不满足,请求报文字段{0}={1},不满足的校验规则{2}
							throw new CommonRuntimeException(DTResCode.EDT0017, nodeName,"null", failRule);
						}
					}
				} finally {
					logger.debug("报文校验结束，耗时={} ms", (System.currentTimeMillis() - start));
				}
			}

		}
	}
	
	/**
	 * 获取错误码
	 * <p>
	 * 
	 * @param rule 
	 *           校验规则
	 * @param value
	 *           数据类型
	 */
	private String getErrCode( String type,String ruleLeft) {
		DataValidatorUtil dv = new DataValidatorUtil();
		String key = "";
		key = (new StringBuilder(type)).append('-').append(ruleLeft).toString();
		String ret = dv.getErrCode(key);
		logger.debug("从框架获取的规则校验错误码对照表中，键：[{}] 错误码：[{}]", key, ret);
		return ret;
	}
	
	
	public ValueHandler getValueHandler() {
		return valueHandler;
	}

	public void setValueHandler(ValueHandler valueHandler) {
		this.valueHandler = valueHandler;
	}

	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	@Override
	public int getType() {
		return TYPE;
	}

	/**
	 * @return the dataValidator
	 */
	public DataValidator getDataValidator() {
		return dataValidator;
	}

	/**
	 * @param dataValidator
	 *            the dataValidator to set
	 */
	public void setDataValidator(DataValidator dataValidator) {
		this.dataValidator = dataValidator;
	}

}
