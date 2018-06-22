package com.panda.ServletDemo.mvcframework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常解析器
 * @author pcongda
 *
 */
public interface HandlerExceptionResolver {
	
	/**
	 * 解析异常
	 * @author pcongda
	 */
	public void resolverException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
