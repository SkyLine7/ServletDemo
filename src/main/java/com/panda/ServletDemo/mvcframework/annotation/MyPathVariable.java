/**
 * 
 */
package com.panda.ServletDemo.mvcframework.annotation;

/**
 * @author pcongda
 *
 */
public @interface MyPathVariable {
	
	String value() default "";
}
