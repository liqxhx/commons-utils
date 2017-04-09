/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;

public class SpELBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	/**
	 * *
	 * 
	 * <pre>
	 * “#{我不是SpEL表达式}”不是SpEL表达式，而是公司内部的模板，想换个前缀和后缀该如何实现呢？
	 * 
	 * 那我们来看下Spring如何在IoC容器内使用BeanExpressionResolver接口实现来求值SpEL表达式，那如果我们通
	 * 过某种方式获取该接口实现，然后把前缀后缀修改了不就可以了。
	 * 此处我们使用BeanFactoryPostProcessor接口提供postProcessBeanFactory回调方法，它是在IoC容器创建好但
	 * 还未进行任何Bean初始化时被ApplicationContext实现调用，因此在这个阶段把SpEL前缀及后缀修改掉是安全的，具
	 * 体代码如下：
	 * 
	 * 首先通过 ConfigurableListableBeanFactory的getBeanExpressionResolver方法获取BeanExpressionResolver实
	 * 现，其次强制类型转换为StandardBeanExpressionResolver，其为Spring默认实现，然后改掉前缀及后缀。
	 * 
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		StandardBeanExpressionResolver resolver = (StandardBeanExpressionResolver) beanFactory
				.getBeanExpressionResolver();
		resolver.setExpressionPrefix("%{");
		resolver.setExpressionSuffix("}");
	}
}