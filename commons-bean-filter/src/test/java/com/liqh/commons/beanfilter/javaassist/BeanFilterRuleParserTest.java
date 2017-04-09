package com.liqh.commons.beanfilter.javaassist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.liqh.commons.beanfilter.BeanFilterRule;
import com.liqh.commons.beanfilter.IFilter;
import com.liqh.commons.beanfilter.PropertyFilterDescriptor;
import com.liqh.commons.beanfilter.javaassist.BeanFilterXmlRuleParser;
import com.liqh.commons.beanfilter.javaassist.JavaassistFilterBuilder;
import com.liqh.commons.lang.i18n.I18n;

/**
 * @author liqh
 * 
 *         <pre>
 * <rule name="test" description="bean filter test" isSimple="false" relationOp="#1 or (#2 and (not #3) and #4)">
 * 		<property name="name" 		type="String" 	value="qhli" 	op="equals" />
 * 		<property name="age" 		type="Numberic" value="20" 		op=">=" />
 * 		<property name="married" 	type="Boolean" 	value="true" 	op="==" />
 * 		<property name="birthDay" 	type="Date" 	value="1981-06-01 17:30:00" op="after" />
 * </rule>
 */
public class BeanFilterRuleParserTest {
	static {
		I18n.init(null, "framework/resources/bean_filter_err");
//		ConvertUtils.deregister(java.util.Date.class);
//		ConvertUtils.register(new StringToDateConvert(), java.util.Date.class);
	}
	
	@Test
	public void testNumbericParser_lt() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", 20, "<=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_gt" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("age", 19);
		System.out.println(filter.filter(bean));
	}
	
	
	@Test
	public void testNumbericParser_gt() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", 20, ">=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_gt" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("age", 20);
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testNumbericParser_ne() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", 20, "!=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_ne" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("age", null);
		bean.put("age", "");
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testNumbericParser_eq() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", 20, "==");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_eq" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("age", 20);
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testNumbericParser_eq_null2() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", "$null", "==");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_eq_null" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("age", null);
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testNumbericParser_eq_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", "$null", "==");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_eq_null" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("age", "20");
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testNumbericParser_ne_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "age", "Numberic", "$null", "!=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testNumbericParser_ne_null" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("age", "20");
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testDateParser_ge() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-18 21:57:00", ">=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testDateParser_ge" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("birthDay", "2016-11-18 21:57:00");
		bean.put("birthDay", "2016-11-18 21:58:00");
		System.out.println(filter.filter(bean));
	}
	@Test
	public void testDateParser_le() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-18 21:57:00", "<=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testDateParser_le" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("birthDay", "2016-11-18 21:57:00");
		bean.put("birthDay", "2016-11-18 21:56:00");
		System.out.println(filter.filter(bean));
	}
	@Test
	public void testDateParser_equals() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-18 21:57:00", "equals");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testDateParser_equals" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("birthDay", "2016-11-18 21:57:00");
		System.out.println(filter.filter(bean));
	}
	@Test
	public void testDateParser_after() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-18 21:57:00", "after");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testDateParser_after" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("birthDay", "2016-11-18 21:57:01");
		System.out.println(filter.filter(bean));
	}
	@Test
	public void testDateParser_before() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "2016-11-18 21:57:00", "before");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testDateParser_before" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("birthDay", "2016-11-18 21:55:00");
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testDateParser_ne_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "$null", "!=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testParseString" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("birthDay", "");
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testDateParser_eq_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "birthDay", "Date", "$null", "==");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testParseString" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("birthDay", null);
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testBooleanParser_false_ne() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "married", "Boolean", "false", "!=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testParseString" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("married", "true"); 
		bean.put("married", " "); 
//		bean.put("married", null); 
		System.out.println(filter.filter(bean));
	}
	
	
	@Test
	public void testStringParser_eq_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "name", "String", "$null", "==");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testStringParser" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("name", null); 
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testStringParser_ne_null() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "name", "String", "$null", "!=");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testStringParser" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
//		bean.put("name", ""); 
		bean.put("name", "xx"); 
		System.out.println(filter.filter(bean));
	}
	
	@Test
	public void testStringParser_eq_empty() throws InstantiationException, IllegalAccessException, Exception{
		//id, index, propertyName, propertyType, propertyValue, op
		PropertyFilterDescriptor propertyFilterDescriptor = new PropertyFilterDescriptor("1", "#1", "name", "String", "", "equals");
		Map<String, PropertyFilterDescriptor> propertyFilterDescriptors = new HashMap<String, PropertyFilterDescriptor>();
		propertyFilterDescriptors.put(propertyFilterDescriptor.getIndex(), propertyFilterDescriptor);
		
		// String name , boolean isSimple ,String relationOp
		BeanFilterRule beanFilterRule = new BeanFilterRule("testStringParser_eq_empty" , true , null);
		beanFilterRule.setPropertyFilterDescriptors(propertyFilterDescriptors);
		
		
		IFilter filter = (IFilter)JavaassistFilterBuilder.build(beanFilterRule).newInstance();
		Map<String, Object> bean = new HashMap<String, Object>();	
		bean.put("name", ""); 
		System.out.println(filter.filter(bean));
	}



	@Test
	public void testParse() throws Exception {
		List<BeanFilterRule> listRule = BeanFilterXmlRuleParser.parse("framework/filter/rule.xml");
		System.out.println(listRule.size());
	

		IFilter filter = (IFilter) JavaassistFilterBuilder.build(listRule.get(0)).newInstance();

		Map<String, Object> bean = new HashMap<String, Object>();
		// #1 or (#2 and (not #3) and #4)
		bean.put("name", "sk"); //  false
		bean.put("age", 19); //false
		bean.put("married", false); // true   
//		bean.put("birthDay", new Date()); // after 1981-06-01 17:30:00
		bean.put("birthDay", "2016-11-19 17:30:00"); // true
		System.out.println("[filter result]:"+filter.filter(bean)); // false

		bean.put("age", 20); // true
		System.out.println("[filter result]:"+filter.filter(bean)); // true
		
		bean.put("age", 19); // false
		System.out.println("[filter result]:"+filter.filter(bean)); // false
		
		bean.put("name", "qhli"); 
		System.out.println("[filter result]:"+filter.filter(bean)); // true
		
		bean.put("name", "sk"); 
		System.out.println("[filter result]:"+filter.filter(bean)); // false
		
		bean.put("age", 20); // >= 20
		bean.put("birthDay", "1981-01-01 12:00:00"); // after 1981-06-01 17:30:00
		System.out.println("[filter result]:"+filter.filter(bean)); // false
	}
}
