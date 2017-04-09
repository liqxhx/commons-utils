/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.IFilter;
import com.liqh.commons.lang.model.ILifeCycle;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class SpelExpressionFilter implements IFilter, ILifeCycle {
	public static Logger logger = LoggerFactory.getLogger(SpelExpressionFilter.class);
	private ExpressionParser parser = new SpelExpressionParser();
//	private Map<String, Set<String>> rules = new HashMap<String, Set<String>>();
	private Map<String, Map<String, String>> rules2 = new HashMap<String, Map<String, String>>();
	
	private String[] rulePath;
	private static Semaphore lock = new Semaphore(1);
	
	@Override
	public boolean filter(Object bean) {
		try {
			lock.acquire();
			EvaluationContext context = new StandardEvaluationContext();
			setVariable(context, bean);
			Map<String, String> expressions = getExpression(bean);		
			if(expressions != null && !expressions.isEmpty()) {
			for(Entry<String, String> entry: expressions.entrySet()) {
//				logger.debug("processing {}.{} = {}", bean.getClass().getSimpleName().toLowerCase(), entry.getKey(), entry.getValue());
				if(parser.parseExpression(entry.getValue()).getValue(context, boolean.class)) {
					logger.debug("rule matches {}.{} = {}", bean.getClass().getSimpleName().toLowerCase(), entry.getKey(), entry.getValue());
					return true;
				}
			}
			}else{
				logger.warn("No Filter Rule Fonund For T({})", bean.getClass().getName());
			}
		} catch (InterruptedException e) {
			throw new BeanFilterException(e);
		} finally {
			lock.release();
		}
		return false;
	}
	
	private Map<String, String> getExpression(Object bean) {
//		return rules.get(bean.getClass().getSimpleName().toLowerCase());
		return rules2.get(bean.getClass().getSimpleName().toLowerCase());
	}

	@SuppressWarnings("unchecked")
	private void setVariable(EvaluationContext context, Object bean) {
		 if(bean instanceof Map){
			 Map<String, Object> mapBean = ((Map<String, Object>)bean);
			 for(Entry<String, Object> entry: mapBean.entrySet()){
				 context.setVariable(entry.getKey(), entry.getValue());
			 }
		 } else {
			 
				Field[] fs = bean.getClass().getDeclaredFields();
				for(Field f : fs) {
					f.setAccessible(true);
					try {
//						logger.debug("setVariable {} {}", f.getName(), f.get(bean)); 
						context.setVariable(f.getName(), f.get(bean));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
//			try {	 
//				PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
//				if(ArrayUtils.isNotEmpty(propertyDescriptors)) {
//					for(PropertyDescriptor propertyDescriptor :propertyDescriptors) {
//						Method getter = propertyDescriptor.getReadMethod();
//						if(getter == null) continue;
//						System.out.println(propertyDescriptor.getName()+" xxx "+getter.invoke(bean) );
//						if("class".equalsIgnoreCase(propertyDescriptor.getName())) continue;
//						if(getter.invoke(bean) == null) continue;
//							context.setVariable(propertyDescriptor.getName(), getter.invoke(bean));
//					}	
//				}
//			} catch (IntrospectionException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
		 }
		
	}
	
	@Override
	public Object ping(Object req) throws CommonRuntimeException {
		return SpelExpressionFilter.class.getName();
	}
	@Override
	public void create() throws CommonRuntimeException {		 
		reload();
	}
	
	public void reload() throws BeanFilterException{
		try {
			lock.acquire();
			
			Properties properties = new Properties();
			for(String path : rulePath) {
				InputStream inputStream = SpelExpressionFilter.class.getClassLoader().getResourceAsStream(path);
				try {
					properties.load(inputStream);
				} catch (IOException e) {
					throw new BeanFilterException(e);
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
//			Map<String, Set<String>> tmpRules = new HashMap<String, Set<String>>();
			
			Map<String, Map<String, String>> tmpRules2 = new HashMap<String, Map<String, String>>();
			for(Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String)entry.getKey();
				StringTokenizer token = new StringTokenizer(key, ".");
				if(!token.hasMoreTokens()) continue;	// filter.beanname.ruleN=xxx
				if(!"filter".equals(token.nextToken())) continue;
				if(!token.hasMoreTokens()) continue;
				String beanName = token.nextToken();
				if(StringUtils.isBlank(beanName)) continue;
				if(!token.hasMoreTokens()) continue;
				String ruleName = token.nextToken();
				if(token.hasMoreTokens()) continue;
				if(StringUtils.isBlank(ruleName)) continue;
				
//				Set<String> beanRules = tmpRules.get(beanName);
//				if(beanRules == null) {
//					beanRules = new HashSet<String>();			
//					tmpRules.put(beanName, beanRules);
//				}
//				beanRules.add((String)entry.getValue());
				
				
				Map<String, String> rules = tmpRules2.get(beanName);
				if(rules == null) {
					rules = new HashMap<String, String>();			
					tmpRules2.put(beanName, rules);
				}
				rules.put(ruleName, (String)entry.getValue());
			}
			
//			rules.clear();
//			rules.putAll(tmpRules);
			
			this.rules2.clear();
			this.rules2.putAll(tmpRules2);
			logger.debug("rules:{}", tmpRules2);
		} catch (InterruptedException e) {
			throw new BeanFilterException(e);
		} finally {
			lock.release();
		}
	}
	
	@Override
	public void start() throws CommonRuntimeException {}
	@Override
	public void stop() throws CommonRuntimeException {}
	@Override
	public void destroy() throws CommonRuntimeException {
//		rules.clear();
//		rules = null;		
		this.rules2.clear();
		this.rules2 = null ;
	}

	public String[] getRulePath() {
		return rulePath;
	}

	public void setRulePath(String[] rulePath) {
		this.rulePath = rulePath;
	}

}
