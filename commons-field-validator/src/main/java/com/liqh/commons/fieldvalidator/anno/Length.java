package com.liqh.commons.fieldvalidator.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Length {
	String fieldName();

	int max() default Integer.MAX_VALUE;

	int min() default Integer.MIN_VALUE;
}
