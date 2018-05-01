package com.panda.ServletDemo.mvcframework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图解析器
 * @author pcongda
 *
 */
public interface ViewResolver {
	
	/**
	 * @param request
	 * @param response
	 * @param result 方法返回值
	 * @author pcongda
	 */
	public void resolverView(HttpServletRequest request, HttpServletResponse response, Object result);
}
