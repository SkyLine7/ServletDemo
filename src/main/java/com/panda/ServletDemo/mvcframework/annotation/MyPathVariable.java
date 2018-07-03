/**
 * 
 */
package com.panda.ServletDemo.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyPathVariable {
	
	String value() default "";
}
