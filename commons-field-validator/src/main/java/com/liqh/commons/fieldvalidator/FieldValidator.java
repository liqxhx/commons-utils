package com.liqh.commons.fieldvalidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liqh.commons.fieldvalidator.anno.NeedValidate;
import com.liqh.commons.fieldvalidator.handler.FieldHandlerHelper;
import com.liqh.commons.fieldvalidator.handler.IFieldHandler;
import com.liqh.commons.fieldvalidator.handler.LengthHandler;
import com.liqh.commons.fieldvalidator.handler.RegexHandler;
import com.liqh.commons.fieldvalidator.handler.RequireHandler;
import com.liqh.commons.fieldvalidator.handler.ValueInHandler;

/**
 * <pre>
 * @author qhlee
 * @versioin v1.0 2016年6月2日
 * @see IFieldValidator
 */
public class FieldValidator implements IFieldValidator {
	private static Map<String, IFieldHandler> executorMap = new HashMap<String, IFieldHandler>();
	private static final String SUFFIX = "Handler";
	private static  final String PREFIX = "com.liqh.commons.fieldvalidator.anno";

	static {
		executorMap.put("RequireHandler", new RequireHandler());
		executorMap.put("LengthHandler", new LengthHandler());
		executorMap.put("RegexHandler", new RegexHandler());
		executorMap.put("ValueInHandler", new ValueInHandler());
	}
	
	public void regist(String type, IFieldHandler handler) throws FieldValidateException {
		executorMap.put(type, handler);
	}


	public void validate(Object validateObj) throws FieldValidateException {
		// do some log
		if (validateObj == null) {
			return;
		}

		if (!needValidate(validateObj)) { // 如果当前对象不需要校验，就返回
			// do some log
			return;
		}

		Class<?> currentClass = validateObj.getClass();
		while (currentClass != null) {
			// 属性列表
			Field[] fields = currentClass.getDeclaredFields();
			for (Field field : fields) {
				validateField(validateObj, field);
			}

			// 校验父类
			Class<?> superClass = currentClass.getSuperclass();
			currentClass = needValidate(superClass) ? superClass : null;
		}

	}

	@SuppressWarnings("rawtypes")
	private void validateField(Object validateObj, Field field) {
		// 如果字段的类型需要校验，则对字段进行校验
		if (needValidate(field.getClass())) {
			Object value = FieldHandlerHelper.getFieldValue(validateObj, field.getName());
			validate(value);
		}

		// 如果字段是List类型，则看list中每个对象是否需要校验
		if (List.class.isAssignableFrom(field.getType())) {
			List value = (List) FieldHandlerHelper.getFieldValue(validateObj, field.getName());
			if (value != null && !value.isEmpty()) {
				for (Object obj : value) {
					if (needValidate(obj.getClass())) {
						validate(obj);
					}
				}
			}
		}

		if (field.getType().isArray()) {
			Object[] value = (Object[]) FieldHandlerHelper.getFieldValue(validateObj, field.getName());
			if (value != null && value.length != 0) {
				for (Object obj : value) {
					if (needValidate(obj.getClass())) {
						validate(obj);
					}
				}
			}
		}

		List<Annotation> annotations = getValidateAnnotations(field);
		if (annotations != null && annotations.size() != 0) {
			for (Annotation annotation : annotations) {
				String annoName = annotation.annotationType().getSimpleName();
				IFieldHandler handler = null;
				handler = executorMap.get(annoName + SUFFIX);
				if (handler == null) {
					throw new FieldValidateException("FV0001");// 没有对应的Handler
				}
				handler.doValidate(validateObj, field);
			}
		}

	}

	private List<Annotation> getValidateAnnotations(Field field) {
		List<Annotation> annos = new ArrayList<Annotation>();
		Annotation[] allAnnos = field.getAnnotations();
		if (allAnnos != null && allAnnos.length != 0) {
			for (Annotation anno : allAnnos) {
				// if (anno.annotationType().getSimpleName().startsWith(PREFIX))
				// {
				// annos.add(anno);
				// }
				if (anno.annotationType().getName().startsWith(PREFIX)) {
					annos.add(anno);
				}
			}
		}
		return annos;
	}

	private boolean needValidate(Object validateObj) {
		// isAssignableFrom 检查validateObj的class是否是IAnnotationValidable的子类、子接口或实现类
		// isAnnotationPresent 检查对象是否使用了注解@ValidateBean
		if (IAnnotationValidable.class.isAssignableFrom(validateObj.getClass()) || validateObj.getClass().isAnnotationPresent(NeedValidate.class)) {
			return true;
		}
		return false;
	}

	private boolean needValidate(Class<?> claxx) {
		// isAssignableFrom 检查claxx是否是IAnnotationValidable的子类、子接口或实现类
		// isAnnotationPresent 检查claxx是否使用了注解@ValidateBean
		if (IAnnotationValidable.class.isAssignableFrom(claxx) || claxx.isAnnotationPresent(NeedValidate.class)) {
			return true;
		}
		return false;
	}
}
