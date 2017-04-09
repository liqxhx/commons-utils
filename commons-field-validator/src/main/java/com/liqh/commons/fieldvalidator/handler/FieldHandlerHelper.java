package com.liqh.commons.fieldvalidator.handler;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.liqh.commons.fieldvalidator.FieldValidateException;

public class FieldHandlerHelper {
	
	public static Object getFieldValue(Object bean, String fieldName) throws FieldValidateException {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
		} catch (Exception e) {
			throw new FieldValidateException(e);
		}

		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : properties) {
			if (propertyDescriptor.getName().equals(fieldName)) {
				Method getter = propertyDescriptor.getReadMethod();
				try {
					return getter.invoke(bean);
					// } catch (IllegalAccessException e) {
					// e.printStackTrace();
					// } catch (IllegalArgumentException e) {
					// e.printStackTrace();
					// } catch (InvocationTargetException e) {
					// e.printStackTrace();
					// }
				} catch (Exception e) {
					throw new FieldValidateException(e);
				}
			}
		}
		return null;
	}
}
