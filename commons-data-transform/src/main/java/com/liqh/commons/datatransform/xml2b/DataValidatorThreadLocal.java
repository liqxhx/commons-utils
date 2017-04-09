package com.liqh.commons.datatransform.xml2b;

public class DataValidatorThreadLocal {

	private static final ThreadLocal<Integer> validateCounter = new ThreadLocal<Integer>(){
		
		@Override
		protected Integer initialValue(){
			return new Integer(0);
		}
		
	};
	
	public static void put(int validateNumber){
		validateCounter.set(validateNumber);
	}
	
	public static int get(){
		return validateCounter.get().intValue();
	}
	
	public static void removeValidateCounter(){
		validateCounter.remove();
	}
	
}
