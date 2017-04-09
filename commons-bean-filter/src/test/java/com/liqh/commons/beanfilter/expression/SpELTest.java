/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.expression;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
@SuppressWarnings({"unchecked","unused","deprecation"})
public class SpELTest {
	@Test
	public void test2() throws ParseException{
		String exp = "#name == 'qhli' OR (#age >=20 AND (NOT #married) AND #birthDay.after(new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse('1981-06-01 17:30:00')))";
	
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("name", "sk");
		context.setVariable("age", 19);
		context.setVariable("married", false);
		context.setVariable("birthDay", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1982-06-01 17:30:00"));
		
		boolean result = parser.parseExpression(exp).getValue(context, boolean.class);
		System.out.println(result);
	}
	@Test
	public void test() throws ParseException{
		String exp = "#bean['name'] == 'qhli' OR (#bean['age'] >=20 AND (NOT #bean['married']) AND #bean['birthDay'].after(new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse('1981-06-01 17:30:00')))";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "sk");
		map.put("age", 21);
		map.put("married", false);
		map.put("birthDay", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1982-06-01 17:30:00"));
		
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("bean", map);
		boolean result = parser.parseExpression(exp).getValue(context, boolean.class);
		System.out.println(result);
	}
	
	@Test
	public void testSpel集合选择(){
		ExpressionParser parser = new SpelExpressionParser();
//		在SQL中指使用select进行选择行数据，而在SpEL指根据原集合通过条件表达式选择出满足条件的元素
//		并构造为新的集合，SpEL使用“(list|map).?[选择表达式]”，其中选择表达式结果必须是boolean类型，如果true则选
//		择的元素将添加到新集合中，false将不添加到新集合中
		
		//1.首先准备测试数据
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(4); 
		collection.add(5);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1); 
		map.put("b", 2);
		
		//2.集合或数组测试
		EvaluationContext context1 = new StandardEvaluationContext();
		context1.setVariable("collection", collection);
//		对于集合或数组选择，如“#collection.?[#this>4]”将选择出集合元素值大于4的所有元素。选择表达式必须返回
//		布尔类型，使用“#this”表示当前元素。
		
		Collection<Integer> result1 = parser.parseExpression("#collection.?[#this>4]").getValue(context1, Collection.class);
		assertSame(1, result1.size());
		assertSame(5, result1.iterator().next());
	
		// 对于字典选择，如“#map.?[#this.key != 'a']”将选择键值不等于”a”的，其中选择表达式中“#this”是
//		Map.Entry类型，而最终结果还是Map，这点和投影不同；集合选择和投影可以一起使用，如“#map.?[key !=
//				'a'].![value+1]”将首先选择键值不等于”a”的，然后在选出的Map中再进行“value+1”的投影
		//3.字典测试
		EvaluationContext context2 = new StandardEvaluationContext();
		context2.setVariable("map", map);
		Map<String, Integer> result2 = parser.parseExpression("#map.?[#this.key != 'a']").getValue(context2, Map.class);
		assertSame(1, result2.size());

		List<Integer> result3 = parser.parseExpression("#map.?[key != 'a'].![value+1]").getValue(context2, List.class);
		assertSame(3, result3.iterator().next());
	}
	
	@Test
	public void testSpel集合投影(){
		ExpressionParser parser = new SpelExpressionParser();
//		在SQL中投影指从表中选择出列，而在SpEL指根据集合中的元素中通过选择来构造另一个集合，该集合
//		和原集合具有相同数量的元素；SpEL使用“（list|map）.![投影表达式]”来进行投影运算
		
		//1.首先准备测试数据
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(4); 
		collection.add(5);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1); 
		map.put("b", 2);

		//2.测试集合或数组
		EvaluationContext context1 = new StandardEvaluationContext();
		context1.setVariable("collection", collection);
		// “#this”代表每个集合或数组元素 
		// 可以使用比如“#this.property”来获取集合元素的属性，其中“#this”可以省略
		Collection<Integer> result1 = parser.parseExpression("#collection.![#this+1]").getValue(context1, Collection.class); 
		assertSame(2, result1.size());
		assertSame(5, result1.iterator().next());
		
		//3.测试字典
		// SpEL投影运算还支持Map投影，但Map投影最终只能得到List结果，
		//对于投影表达式中的“#this”将是Map.Entry，所以可以使用“value”来获取值，使用“key”来获取键。
		EvaluationContext context2 = new StandardEvaluationContext();
		context2.setVariable("map", map);
		List<Integer> result2 = parser.parseExpression("#map.![ value+1]").getValue(context2, List.class);
		assertSame(2, result2.size());
	}
	
	@Test
	public void testSpel列表_字典_数组元素修改(){
		ExpressionParser parser = new SpelExpressionParser();
		//对数组修改直接对“#array[index]”赋值即可修改元素值，同理适用于集合和字典类型
		//1.修改数组元素值
		int[] array = new int[] {1, 2};
		EvaluationContext context1 = new StandardEvaluationContext();
		context1.setVariable("array", array);
		int result1 = parser.parseExpression("#array[1] = 3").getValue(context1, int.class);
		assertSame(3, result1);
		
		//2.修改集合值
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		collection.add(2);
		EvaluationContext context2 = new StandardEvaluationContext();
		context2.setVariable("collection", collection);
		int result2 = parser.parseExpression("#collection[1] = 3").getValue(context2, int.class);
		assertSame(3, result2);
		parser.parseExpression("#collection[1]").setValue(context2, 4);
		result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
		assertSame(4, result2);
		
		//3.修改map元素值
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		EvaluationContext context3 = new StandardEvaluationContext();
		context3.setVariable("map", map);
		int result3 = parser.parseExpression("#map['a'] = 2").getValue(context3, int.class);
		assertSame(2, result3);
		
	}
	
	@Test
	public void testSpel集合(){
		
		ExpressionParser parser = new SpelExpressionParser();
		// SpEL目前支持所有集合类型和字典类型的元素访问
		// 使用“集合[索引]”访问集合元素
		// 使用#map['key']访问字典元素
		// 注：集合元素访问是通过Iterator遍历来定位元素位置的。
		
		//SpEL内联List访问
		int result1 = parser.parseExpression("{1,2,3}[0]").getValue(int.class);
		//即list.get(0)
		assertSame(1, result1);
		
		//SpEL目前支持所有集合类型的访问
		Collection<Integer> collection = new HashSet<Integer>();
		collection.add(1);
		collection.add(2);
		EvaluationContext context2 = new StandardEvaluationContext();
		context2.setVariable("collection", collection);
		int result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
		//对于任何集合类型通过Iterator来定位元素
		assertSame(2, result2);

		//SpEL对Map字典元素访问的支持
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 1);
		EvaluationContext context3 = new StandardEvaluationContext();
		context3.setVariable("map", map);
		int result3 = parser.parseExpression("#map['a']").getValue(context3, int.class);
		assertSame(1, result3);
	}
	
	@Test
	public void testSpelBean引用(){
//		SpEL支持使用“@”符号来引用Bean，在引用Bean时需要使用BeanResolver接口实现来查找Bean，
//		Spring提供BeanFactoryResolver实现；
		
//		在示例中我们首先初始化了一个IoC容器，ClassPathXmlApplicationContext 实现默认会把
//		“System.getProperties()”注册为“systemProperties”Bean，因此我们使用 “@systemProperties”来引用该
//		Bean。
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.refresh();
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(ctx));
		Properties result1 = parser.parseExpression("@systemProperties").getValue(context, Properties.class);
		System.out.println(result1);
		assertSame(System.getProperties(), result1);
	}
	
	@Test
	public void testSpel数组(){
		ExpressionParser parser = new SpelExpressionParser();
		String expression3 = "{{1+2,2+4},{3,4+4}}";
		//声明二维数组并初始化
		//	int[][] result4 = parser.parseExpression("new int[2][3]{{1,2,3},{4,5,6}").getValue(int[][].class);
	
		int[] result4 = parser.parseExpression("new int[2]{1,2}").getValue(int[].class);

		
		//定义一维数组并初始化
		int[] result5 = parser.parseExpression("new int[1]").getValue(int[].class);
		
		//内联数组：和Java 数组定义类似，只是在定义时进行多维数组初始化。
		//定义多维数组但不初始化
		int[][][] result6 = parser.parseExpression(expression3).getValue(int[][][].class);
		
		//错误的定义多维数组，多维数组不能初始化
		String expression4 = "new int[1][2][3]{{1}{2}{3}}";
		try {
			int[][][] result7 = parser.parseExpression(expression4).getValue(int[][][].class);
			fail();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testSpel内联List(){
		// 从Spring3.0.4开始支持内联List，使用{表达式，……}定义内联List，如“{1,2,3}”将返回一个整型的ArrayList
		// 而“{}”将返回空的List
		// 对于字面量表达式列表，SpEL会使用java.util.Collections.unmodifiableList方法
		// 将列表设置为不可修改。
		ExpressionParser parser = new SpelExpressionParser();
		List<Integer> result2 = parser.parseExpression("{}").getValue(List.class);
 
		//对于字面量列表也将返回不可修改的List
		List<Integer> result1 = parser.parseExpression("{1,2,3}").getValue(List.class);
		System.out.println(result1.get(0)+" "+result1.get(0).getClass().getName());
		assertSame(1, result1.get(0));
		try {
			result1.set(0, 2);
			fail("不可能执行到这，对于字面量列表不可修改");
		} catch (Exception e) {			
		}
		
		//对于列表中只要有一个不是字面量表达式，将只返回原始List，
		//不会进行不可修改处理
		String expression3 = "{{1+2,2+4},{3,4+4}}";
		List<List<Integer>> result3 = parser.parseExpression(expression3).getValue(List.class);
		result3.get(0).set(0, 1);
		assertSame(2, result3.size());


		
	}
	
 
	@Test
	public void testSpel对象方法调用(){
//		对象方法调用更简单，跟Java语法一样；如“'haha'.substring(2,4)”将返回“ha”；而对于根对
//		象可以直接调用方法；
//		比如根对象date方法“getYear”可以直接调用。
		ExpressionParser parser = new SpelExpressionParser();
		Date date = new Date();
		StandardEvaluationContext context = new StandardEvaluationContext(date);
		int result2 = parser.parseExpression("getYear()").getValue(context, int.class);
		assertSame(date.getYear(), result2);
	}
	
	@Test
	public void testSpel对象属性存取及安全导航表达式(){
//		对象属性获取非常简单，即使用如“a.property.property”这种点缀式获取，
//		SpEL对于属性名首字母是不区分大小写的；SpEL还引入了Groovy语言中的安全导航运算符“(对象|属性)?.属性”，用
//		来避免但“?.”前边的表达式为null时抛出空指针异常，而是返回null；修改对象属性值则可以通过赋值表达式或
//		Expression接口的setValue方法修改
		
		ExpressionParser parser = new SpelExpressionParser();
		//1.访问root对象属性
		Date date = new Date();
		StandardEvaluationContext context = new StandardEvaluationContext(date);
		int result1 = parser.parseExpression("Year").getValue(context, int.class);
		assertSame(date.getYear(), result1);
		int result2 = parser.parseExpression("year").getValue(context, int.class);
		assertSame(date.getYear(), result2);
		
//		对于当前上下文对象属性及方法访问，可以直接使用属性或方法名访问，比如此处根对象date属性“year”，注意
//		此处属性名首字母不区分大小写。
		//2.安全访问
		context.setRootObject(null);
		Object result3 = parser.parseExpression("#root?.year").getValue(context, Object.class);
		assertSame(null, result3);
		
//		SpEL引入了Groovy的安全导航运算符，比如此处根对象为null，所以如果访问其属性时肯定抛出空指针异常，而
//		采用“?.”安全访问导航运算符将不抛空指针异常，而是简单的返回null。
		
		//3.给root对象属性赋值
		context.setRootObject(date);
		int result4 = parser.parseExpression("Year = 4").getValue(context, int.class);
		assertSame(4, result4);
		parser.parseExpression("Year").setValue(context, 5);
		int result5 = parser.parseExpression("Year").getValue(context, int.class);
		assertSame(5, result5);
		
		
	}
	
	@Test
	public void testSpel赋值表达式(){
		ExpressionParser parser = new SpelExpressionParser();
		//1.给root对象赋值
		EvaluationContext context = new StandardEvaluationContext("aaaa");
		parser.parseExpression("#root='aaaaa'");
		String result1 = parser.parseExpression("#root").getValue(context, String.class);
		System.out.println(result1);
//		assertSame("aaaaa", result1);
//		String result2 = parser.parseExpression("#this='aaaa'").getValue(context, String.class);
//		assertSame("aaaa", result2);

		 //2.给自定义变量赋值
		 context.setVariable("#variable", "variable");
		 String result3 = parser.parseExpression("#variable=#root").getValue(context, String.class);
		 assertSame("aaaa", result3);
	}
	
	@Test
	public void testSpel自定义函数() throws SecurityException, NoSuchMethodException{
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
		context.registerFunction("parseInt", parseInt);
		context.setVariable("parseInt2", parseInt);
		String expression1 = "#parseInt('3') == #parseInt2('3')";
		boolean result1 = parser.parseExpression(expression1).getValue(context, boolean.class);
		
		assertSame(true, result1);
		
		
	}
	@Test
	public void testSpel变量定义及引用(){
//		变量定义通过EvaluationContext接口的setVariable(variableName, value)方法定义；在表达式
//		中使用“#variableName”引用；除了引用自定义变量，SpE还允许引用根对象及当前上下文对象，使用“#root”引
//		用根对象，使用“#this”引用当前上下文对象
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("var", "hello spel");
		
		ExpressionParser parser = new SpelExpressionParser();
		assertSame("hello spel", parser.parseExpression("#var").getValue(context, String.class));
		
		context = new StandardEvaluationContext("hello spel");
		assertSame("hello spel", parser.parseExpression("#root").getValue(context, String.class));
		
		context = new StandardEvaluationContext("hello spel");
		assertSame("hello spel", parser.parseExpression("#this").getValue(context, String.class));
	
	}
	
	@Test
	public void testSpelInstanceOf(){
		ExpressionParser parser = new SpelExpressionParser();
		String exp = "'hello spel' instanceof T(String)";
		assertSame(true, parser.parseExpression(exp).getValue(boolean.class));
	}
	@Test
	public void testSpel类实例化(){
		// 类实例化同样使用java关键字“new”，类名必须是全限定名，但java.lang包内的类型除外，
		// 如String、Integer
		ExpressionParser parser = new SpelExpressionParser();
		String exp1 = "new String('hello spel')";
		System.out.println(parser.parseExpression(exp1).getValue(String.class));
		
		String exp2 = "new java.util.Date()";
		System.out.println(parser.parseExpression(exp2).getValue(Date.class));
	}
	
	@Test
	public void testSpel类相关表达式(){
//		使用“T(Type)”来表示java.lang.Class实例，“Type”必须是类全限定名，“java.lang”包除
//		外，即该包下的类可以不指定包名；使用类类型表达式还可以进行访问类静态方法及类静态字段
		ExpressionParser parser = new SpelExpressionParser();
		String exp1 = "T(String)";
		Class<String> strclass = parser.parseExpression(exp1).getValue(Class.class);
		System.out.println(strclass.getName());
		
		String exp2 = "T(Integer).MIN_VALUE";
		System.out.println(parser.parseExpression(exp2).getValue(int.class));
		
		String exp3 = "T(Integer).parseInt('1')";
		System.out.println(parser.parseExpression(exp3).getValue(Integer.class));
		
	}
	
	// 括号优先级表达式：使用“(表达式)”构造，括号里的具有高优先级。
	@Test
	public void testSpel正则表达式(){
		String exp = "'123' matches '\\d{3}'";
		ExpressionParser parser = new SpelExpressionParser();
		System.out.println(parser.parseExpression(exp).getValue(boolean.class));
	}
	
	
	@Test
	public void testSpel三目运算及Elivis运算表达式(){
		ExpressionParser parser = new SpelExpressionParser();
		// 三目运算符“表达式1?表达式2:表达式3”用于构造三目运算表达式，如“2>1?true:false”将返回true；
		String exp1 = "2>1?true:false";
		System.out.println(parser.parseExpression(exp1).getValue(boolean.class));
		
		// Elivis运算符“表达式1?:表达式2”从Groovy语言引入用于简化三目运算符的
		// 当表达式1为非null时则返回表达式1，当表达式1为null时则返回表达式2
		// 如“null?:false”将返回false，而“true?:false”将返回true
		String exp2 = "null?:false";
		System.out.println(parser.parseExpression(exp2).getValue(boolean.class));
	}
	
	
	@Test
	public void testSpel字符串连接及截取表达式(){
		ExpressionParser parser = new SpelExpressionParser();
		String expression1 = "'Hello ' + 'World!'";
		System.out.println(parser.parseExpression(expression1).getValue(String.class));
		
		// 使用“'String'[index]”来截取一个字符，目前只支持截取一个
		String expression2 = "'Hello World!'[0]";
		System.out.println(parser.parseExpression(expression2).getValue(String.class));
	}
	
	@Test
	public void testSpel逻辑表达式(){
		//逻辑运算符不支持Java中的 && 和 || 。
		ExpressionParser parser = new SpelExpressionParser();
		String expression1 = "(2 > 1) and (!true or !false)";
		boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
		System.out.println(result1);
		
		String expression2 = "2 > 1 and (NOT true OR NOT false)";
		boolean result2= parser.parseExpression(expression2).getValue(boolean.class);
		System.out.println(result2);
	}
	@Test
	public void testSpel关系表达式(){
		// 等于（==）、不等于(!=)、大于(>)、大于等于(>=)、小于(<)、小于等于(<=)，区间（between）运算，
		// SpEL同样提供了等价的“EQ” 、“NE”、 “GT”、“GE”、 “LT” 、“LE”来表示等于、不等于、大于、大于等于、小于、小于等于，不区分大小写。
		ExpressionParser parser = new SpelExpressionParser();
		boolean result1 = parser.parseExpression("1>2").getValue(boolean.class); // false
		boolean resutl2 = parser.parseExpression("1 between {1, 2}").getValue(boolean.class); // true
	}
	
	@Test
	public void testSpel算数运算表达式(){
		ExpressionParser parser = new SpelExpressionParser();
		// 加减乘除
		int result1 = parser.parseExpression("1+2-3*4/2").getValue(Integer.class);//-3
		System.out.println(result1);
		
		// 求余
		int result2 = parser.parseExpression("4%3").getValue(Integer.class);//1
 
		// 幂运算
		int result3 = parser.parseExpression("2^3").getValue(Integer.class);//8
		
		// SpEL还提供求余（MOD）和除（DIV）而外两个运算符，与“%”和“/”等价，不区分大小写
	}
	
	@Test
	public void testSpelBasic(){
		ExpressionParser parser = new SpelExpressionParser();
		//字符串
		String str1 = parser.parseExpression("'Hello World!'").getValue(String.class);
		System.out.println(str1);
		String str2 = parser.parseExpression("\"Hello World!\"").getValue(String.class);
		System.out.println(str2);
		
		// 数字类型
		int int1 = parser.parseExpression("1").getValue(Integer.class);
		long long1 = parser.parseExpression("-1L").getValue(long.class);
		float float1 = parser.parseExpression("1.1").getValue(Float.class);
		double double1 = parser.parseExpression("1.1E+2").getValue(double.class);
		int hex1 = parser.parseExpression("0xa").getValue(Integer.class);
		long hex2 = parser.parseExpression("0xaL").getValue(long.class);
		
		// 布尔类型
		boolean true1 = parser.parseExpression("true").getValue(boolean.class);
		boolean false1 = parser.parseExpression("false").getValue(boolean.class);
		
		// null类型
		Object null1 = parser.parseExpression("null").getValue(Object.class);
		System.out.println(null1);
		
		
	}
}
