package com.panda.ServletDemo.mvcframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequsetMapping {
	
	String value() default "";
	
	MyRequestMethod[] method() default {};
}
