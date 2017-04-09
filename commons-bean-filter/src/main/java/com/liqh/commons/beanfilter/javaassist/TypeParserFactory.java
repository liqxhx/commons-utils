package com.liqh.commons.beanfilter.javaassist;

import java.util.Hashtable;
import java.util.Map;


public final class TypeParserFactory {
	private static final Map<String,ITypeParser> parserMap = new Hashtable<String,ITypeParser>() ;
	
	static{
		register(RenderType.STRING, 		new StringParser());
		register(RenderType.BOOLEAN, 	new BooleanParser());
		register(RenderType.NUMBERIC,	new NumbericParser()) ;
		register(RenderType.DATE,			new DateParser()) ;
	}
	
	public static ITypeParser getTypeParser(String propertyType) {
		return parserMap.get(propertyType) ;
	}
	
	public static void register(String propertyType , ITypeParser typeParser) {
		parserMap.put(propertyType, typeParser) ;
	}
	public static void unRegister(String propertyType) {
		parserMap.remove(propertyType) ;
	}
	
	public static void clear(){
		parserMap.clear() ;
	}

	
}
