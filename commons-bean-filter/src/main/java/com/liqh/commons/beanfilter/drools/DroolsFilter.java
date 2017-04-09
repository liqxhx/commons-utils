/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.drools;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liqh.commons.beanfilter.BeanFilterException;
import com.liqh.commons.beanfilter.IFilter;
import com.liqh.commons.beanfilter.constant.BFResCode;
import com.liqh.commons.lang.model.ILifeCycle;
import com.liqh.commons.lang.model.ex.CommonRuntimeException;

public class DroolsFilter implements IFilter, ILifeCycle {
	public static Logger logger = LoggerFactory.getLogger(DroolsFilter.class);
	private KnowledgeBase kbase;
	private StatelessKnowledgeSession statelessKSession;
	private static final Semaphore lock = new Semaphore(1);
	
	/**
	 * drools引擎过滤状态变量，包含开始、结束时间等
	 */
	private static final FilterVars _vars = new FilterVars();
	private String[] drls;
	/**
	 * 强制过滤标识
	 */
	private boolean forceExecuteFilter = true;
	
	@Override
	public boolean filter(Object bean) {
		try {
			lock.acquire();
			if(forceExecuteFilter) {
				logger.debug("{} force filter", bean);
				return doFilter(bean);
			} else {
				if(bean.getClass().isAnnotationPresent(NeedFilter.class)) {
//					NeedFilter needFilter = bean.getClass().getAnnotation(NeedFilter.class);
//					String fieldName = needFilter.value();					
					logger.debug("{} need filter", bean);
					return doFilter(bean);
				}
			}
		} catch (InterruptedException e) {
			logger.debug(e.getMessage() , e);
		} finally {
			lock.release();
		}			
		return false;
	}

	private boolean doFilter(Object bean) {
		logger.debug("before filter:{}", bean);
		_vars.reset();
		logger.debug("_vars reset: {}", _vars);
		// drools execute
		statelessKSession.execute(bean);
		logger.debug("end filter, {}, {}", _vars.getHowlong(), _vars, bean);
		
		boolean filteredFlagFieldValue = false;
		String filteredFlagFieldName = null;
		if(!forceExecuteFilter) {
			NeedFilter needFilter = bean.getClass().getAnnotation(NeedFilter.class);
			filteredFlagFieldName = needFilter.value();		
		}
		if(StringUtils.isNotBlank(filteredFlagFieldName)) {
			try {
				Field field = bean.getClass().getDeclaredField(filteredFlagFieldName);
				field.setAccessible(true);
				if(field.get(bean) instanceof Boolean) {
					filteredFlagFieldValue = (Boolean) field.get(bean);
				} else {
					filteredFlagFieldValue = (Boolean) ConvertUtils.convert(field.get(bean), Boolean.class);
				}
			} catch (SecurityException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
			} catch (NoSuchFieldException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
			
			if( _vars.getFiltered() != null && filteredFlagFieldValue != _vars.getFiltered()){
				// 过滤结果不匹配,属性值:{0},环境值:{1}
				throw new BeanFilterException(BFResCode.EBF0010, filteredFlagFieldValue, _vars.getFiltered());
			}
			
		} else {
			filteredFlagFieldValue = _vars.getFiltered()==null?false:_vars.getFiltered();
		}	
		return filteredFlagFieldValue;
	}

	@Override
	public Object ping(Object req) throws CommonRuntimeException {
		logger.debug("ping {}", req);
		return this.getClass().getName();
	}

	@Override
	public void create() throws CommonRuntimeException {
		logger.debug("begin create");		
		reload(false);
		logger.debug("end create");
	}

	public void removeRule(String packageName) throws BeanFilterException {
		try{
			lock.acquire();
			logger.debug("start remove {}", packageName);
			kbase.removeKnowledgePackage(packageName);
			logger.info("removed {}", packageName);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			throw new BeanFilterException(e);
		} finally {
			lock.release();
		}
	}
	
	public void addRule(Object rule, boolean ispath) throws BeanFilterException {
		logger.debug("start addRule {} {}", rule, ispath);
		try{
			lock.acquire();
			if(rule == null || StringUtils.isBlank(String.valueOf(rule))) {
				// reload
				doReload(true, drls);
			}
			else if (rule instanceof String) {
				
				String script = (String) rule;
				if (ispath) {
					logger.debug("start add path {}", script);
					try {
						doReload(true, new String[]{script});
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new BeanFilterException(e);
					}
					logger.info("end add path {}", script);
				} else {
					// 本身就是规则
					logger.debug("start add script {}", script);
					try {						
						KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
						Reader reader = new StringReader(script);
						kbuilder.add(ResourceFactory.newReaderResource(reader),ResourceType.DRL);
						
						if(CollectionUtils.isNotEmpty(kbuilder.getKnowledgePackages())) {
							for(KnowledgePackage pkg : kbuilder.getKnowledgePackages()) {
								kbase.removeKnowledgePackage(pkg.getName());
								logger.debug("add after remove package {}", pkg.getName());
							}
							kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
						} else {
							logger.warn("packages is empty");
						}
						kbuilder = null;
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new BeanFilterException(e);
					}
				}
				logger.debug("end add script {}", script);				
			
			} else {
				logger.warn("addRule Not Support {}", rule.getClass());
			}
			
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			throw new BeanFilterException(e);
		} finally {
			lock.release();
		}	
		logger.debug("end add {} {}", rule, ispath);
	}
	
	private void reload(boolean reload) throws BeanFilterException {
		if(reload)
			logger.debug("reload start");
		
		try{
			lock.acquire();
			
			doReload(reload, drls);
			
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			throw new BeanFilterException(e);
		} finally {
			lock.release();
		}	
		
		if(reload)
			logger.debug("reload end");		
	}

	private void doReload(boolean reload, String[] drls) {
		if(ArrayUtils.isEmpty(drls)) {
			// 未配置过滤规则文件
			throw new BeanFilterException(BFResCode.EBF0002);
		} 
		// 1
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		for(String drlFile : drls) {
			kbuilder.add(ResourceFactory.newClassPathResource(drlFile, DroolsFilter.class), ResourceType.DRL);
			if(kbuilder.hasErrors()) {
				StringBuilder msgBuilder = new StringBuilder();
//					logger.error("规则中存在错误，错误消息如下：");
				KnowledgeBuilderErrors kbuidlerErrors=kbuilder.getErrors();
				for(Iterator<KnowledgeBuilderError> iter=kbuidlerErrors.iterator();iter.hasNext();){
//						logger.error("{}",iter.next());
					msgBuilder.append(iter.next()).append(";");
				}
				// drools规则中存在错误,错误消息如下:{0}
				throw new BeanFilterException(BFResCode.EBF0011, msgBuilder.toString());
			}
		}
		
		// 产生规则包的集合
		Collection<KnowledgePackage> kpackages = kbuilder.getKnowledgePackages();
		if(CollectionUtils.isEmpty(kpackages)) {
			// drools规则包为空,规则路径:{0}
			throw new BeanFilterException(BFResCode.EBF0012, drls.toString());
		} else{
			logger.debug("kpackages size:{}", kpackages.size());
			
			
			if(reload) {
				for(KnowledgePackage kpkg : kpackages) {
					kbase.removeKnowledgePackage( kpkg.getName());
					logger.debug("Add KnowledgePackage After Remove {}", kpkg.getName());				 
				}
				kbase.addKnowledgePackages(kpackages);
			}
		}
		
		
		// 2
		if(!reload) {
			KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
			kbConf.setProperty("org.drools.sequential", "true");
			kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
			kbase.addKnowledgePackages(kpackages);
			for(KnowledgePackage kpkg : kpackages) {
				logger.debug("Add KnowledgePackage {} on Create.", kpkg.getName());				 
			}
			
			// 3
			statelessKSession = kbase.newStatelessKnowledgeSession();
			
			// 4
			statelessKSession.setGlobal("_vars", _vars);
			statelessKSession.setGlobal("_logger", logger);
		}
	}

	@Override
	public void start() throws CommonRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws CommonRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws CommonRuntimeException {
		logger.debug("begin destroy");
	
		logger.debug("end destroy");
	}

	public String[] getDrls() {
		return drls;
	}

	public void setDrls(String[] drls) {
		this.drls = drls;
	}

	public boolean isForceExecuteFilter() {
		return forceExecuteFilter;
	}

	public void setForceExecuteFilter(boolean forceExecuteFilter) {
		this.forceExecuteFilter = forceExecuteFilter;
	}

}
