package com.liqh.commons.beanfilter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilsTest {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println(BeanUtils.getProperty(map, "agentName") ); // null
		map.put("name", null);
		System.out.println(BeanUtils.getProperty(map, "name") ); // null
		map.put("prefix", "");
		System.out.println(BeanUtils.getProperty(map, "prefix") ); // ""
		
		// 获取一个bean中没有的属性时，返回null
	}

}
