package com.panda.ServletDemo.mvcframework.exception;
/**
 * 服务器异常
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = -6920206350971787059L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String msg,Throwable thr) {
		super(msg,thr);
	}
}
