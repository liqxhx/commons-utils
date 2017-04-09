/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpelXmlCfgTest {

	/**
	 * 
	 * <pre>
	 * 如果有同学问“#{我不是SpEL表达式}”不是SpEL表达式，而是公司内部的模板，想换个前缀和后缀该如何实现
	 * 呢？
	 * 那我们来看下Spring如何在IoC容器内使用BeanExpressionResolver接口实现来求值SpEL表达式，那如果我们通
	 * 过某种方式获取该接口实现，然后把前缀后缀修改了不就可以了。
	 * 此处我们使用BeanFactoryPostProcessor接口提供postProcessBeanFactory回调方法，它是在IoC容器创建好但
	 * 还未进行任何Bean初始化时被ApplicationContext实现调用，因此在这个阶段把SpEL前缀及后缀修改掉是安全的，具
	 * 体代码如下：
	 * 
	 * 首先通过 ConfigurableListableBeanFactory的getBeanExpressionResolver方法获取BeanExpressionResolver实
	 * 现，其次强制类型转换为StandardBeanExpressionResolver，其为Spring默认实现，然后改掉前缀及后缀。
	 * 
	 * 配置文件和注解风格的几乎一样，只有SpEL表达式前缀变为“%{”了，并且注册了
	 * SpELBeanFactoryPostProcessor，用于修改前缀和后缀的。
	 * 写测试代码测试一下吧：
	 */
	@Test
	public void testPrefixExpression() {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/liqh/commons/beanfilter/expression/el3.xml");
		SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
		Assert.assertEquals("#{'Hello' + world}", helloBean1.getValue());
		SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
		Assert.assertEquals("Hello World!", helloBean2.getValue());
	}

	/**
	 * 
	 * <pre>
	 * SpEL支持在Bean定义时注入，
	 * 默认使用“#{SpEL表达式}”表示，
	 * 其中“#root”根对象默认可以认为是ApplicationContext，
	 * 只有ApplicationContext实现默认支持SpEL，获取根对象属性其实是获取容器中的Bean
	 *
	 */
	@Test
	public void testXmlExpression() {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/liqh/commons/beanfilter/expression/el1.xml");

		String hello1 = ctx.getBean("hello1", String.class);
		String hello2 = ctx.getBean("hello2", String.class);
		String hello3 = ctx.getBean("hello3", String.class);
		Assert.assertEquals("Hello World!", hello1);
		Assert.assertEquals("Hello World!", hello2);
		Assert.assertEquals("Hello World!", hello3);
	}

	@Test
	public void testAnnotationExpression() {
		// 其中“helloBean1 ”值是SpEL表达式的值，
		// 而“helloBean2”是通过setter注入的值，
		// 这说明setter注入将覆盖@Value的值。
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/liqh/commons/beanfilter/expression/el2.xml");
		SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
		Assert.assertEquals("Hello World!", helloBean1.getValue());
		SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
		Assert.assertEquals("haha", helloBean2.getValue());
	}

}
