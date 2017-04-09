/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.handler;

import java.util.HashMap;
import java.util.Map;

import com.liqh.commons.datatransform.ValueHandler;

public class HandlerFactory {
	static Map<String, ValueHandler> handlers = new HashMap<String, ValueHandler>();
	
	static{
		handlers.put(StringToDateHandler.class.getSimpleName(), new StringToDateHandler());
	}
	
	public static boolean contains(Class<ValueHandler> claxx){
		return handlers.containsKey(claxx.getSimpleName());
	}
	
	public static ValueHandler getValueHandler(String handlerId){
		return handlers.get(handlerId);
	}
	
	public static void registerHandler(ValueHandler valueHandler){
		handlers.put(valueHandler.getClass().getSimpleName(), valueHandler);
	}
}
