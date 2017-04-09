package com.liqh.commons.beanfilter.javaassist;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.lang.utils.StringUtils;

/**
 * 一将个过滤规则BeanFilterRule编译成DefaultFilter的子类
 * 然后就可以用这个类过滤JavaBean
 * @author qhli
 *
 */
public final class JavaassistFilterBuilder {
	private static Logger logger = LoggerFactory.getLogger(JavaassistFilterBuilder.class);
	private static ClassPool classPool;
	public static final String CRLF = "\r\n";
	
	static{
		classPool = ClassPool.getDefault();
		classPool.insertClassPath(new ClassClassPath(DefaultFilter.class));
	}

	@SuppressWarnings({ "rawtypes" })
	public static Class build(BeanFilterRule beanFilterRule) throws BeanFilterException {
		Map<String, String> booleanVariables = new HashMap<String, String>();
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = beanFilterRule.getPropertyFilterDescriptors();

		try {
			CtClass cc = classPool.get(DefaultFilter.class.getName());
			cc.setName(DefaultFilter.class.getName() + DefaultFilter.getIndex());
			
			StringBuffer script = new StringBuffer();
			script.append("{").append(CRLF);
			script.append("String propertyValue = null ;").append(CRLF);
			script.append("String refValue = null ;").append(CRLF);
			 
			CtMethod ctMethod = cc.getDeclaredMethod("filter");
			
			for(Entry<String, PropertyFilterDescriptor> entry : propertyFilterDescriptors.entrySet()){
				ITypeParser parser = TypeParserFactory.getTypeParser(entry.getValue().getPropertyType());
				script.append(parser.toScript(entry.getValue()));
				booleanVariables.put(entry.getKey(), parser.getBooleanVariableName());// #1 agexxxxx
			}



			if (beanFilterRule.isSimple()) {
				script.append(genSimpleBooleanExpression(booleanVariables, beanFilterRule.getRelationOp()));
			} else {
				script.append(genComplexBooleanExpression(booleanVariables, beanFilterRule.getRelationOp()));
			}

			script.append("}");
			logger.debug("op is simple:{}; expression:{}",beanFilterRule.isSimple(),beanFilterRule.getRelationOp());
			logger.debug("-----------------------Construct IFilter For {}-----------------------",beanFilterRule.getDescription());
			logger.debug("public class {} for method filter(Object bean){}", cc.getName(),script);
			logger.debug("----------------------------------------------");
			ctMethod.insertBefore(script.toString());

			return cc.toClass();
		} catch (NotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (CannotCompileException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	//op = (#1 AND #2) OR (NOT #3)
	private static Object genComplexBooleanExpression(Map<String,String> booleanVariables, String relationOp) {
		
		StringBuffer strbuf = new StringBuffer("return ");
		relationOp = relationOp.toUpperCase() ;
		relationOp = relationOp.replaceAll("AND", "&&") ;
		relationOp = relationOp.replaceAll("OR","||") ;
		relationOp = relationOp.replaceAll("NOT", "!") ;
		
		for(Entry<String, String> entry : booleanVariables.entrySet()) {
			relationOp = relationOp.replaceAll(entry.getKey(), entry.getValue()) ;
		}
		strbuf.append(relationOp).append(";") ;
		return strbuf.toString();
	}

	private static String genSimpleBooleanExpression(Map<String,String> booleanVariables, String relationOp) {
		StringBuffer strbuf = new StringBuffer("return ");
 
		if ("AND".equalsIgnoreCase(relationOp) || StringUtils.isBlank(relationOp)) {
			doAppend(booleanVariables, strbuf, null, " && ");
		}else if("OR".equalsIgnoreCase(relationOp)){
			doAppend(booleanVariables, strbuf, null, " || ");
		}else if("NOT".equalsIgnoreCase(relationOp)){
			doAppend(booleanVariables, strbuf, "!", " && ");
		}
		strbuf.append(";") ;
		return strbuf.toString();
	}

	private static void doAppend(Map<String, String> booleanVariables, StringBuffer strbuf, String expressNot, String express) {
		int count = booleanVariables.size();
		int index = 0;
		for(Entry<String, String> entry : booleanVariables.entrySet()) {
			strbuf.append(StringUtils.isNotBlank(expressNot)?expressNot:"").append(entry.getValue());
			if (index < count - 1) {
				strbuf.append(express);
			}
			index++;
		}
	}
}
