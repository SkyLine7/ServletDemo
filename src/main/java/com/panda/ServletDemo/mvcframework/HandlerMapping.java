package com.panda.ServletDemo.mvcframework;

/**
 * 处理器映射器
 * @author pcongda
 *
 */
public interface HandlerMapping {
	
	/**
	 * @param reqMethod 当前请求方法：eg:GET,POST...
	 * @param reqPath  当前请求路径
	 * @author pcongda
	 */
	public Handler getHandler(String reqMethod,String reqPath);
}
