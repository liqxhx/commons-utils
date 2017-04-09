package com.liqh.commons.beanfilter.javaassist;

import com.liqh.commons.beanfilter.IFilter;



public class DefaultFilter implements IFilter {
	public static int index = 0;

	public static int getIndex() {
		synchronized (DefaultFilter.class) {
			return index++;
		}
	}

	public boolean filter(Object bean) {
		return false;
	}

}
