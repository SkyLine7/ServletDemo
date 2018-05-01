/**
 * 
 */
package com.panda.ServletDemo.mvcframework.exception;

/**
 * 请求异常
 * @author pcongda
 * @version 1.0
 */
public class RequsetException extends RuntimeException{	
	
	private static final long serialVersionUID = -483207706285982459L;

	public RequsetException() {
		super();
	}

	public RequsetException(String msg) {
		super(msg);
	}

	public RequsetException(String msg,Throwable thr) {
		super(msg,thr);
	}
}
