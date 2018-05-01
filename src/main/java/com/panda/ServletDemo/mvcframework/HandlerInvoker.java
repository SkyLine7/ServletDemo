package com.panda.ServletDemo.mvcframework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器调用器
 * @author pcongda
 *
 */
public interface HandlerInvoker {
	
	/**
	 * 调用处理器,执行方法
	 * @param request
	 * @param response
	 * @param handler 处理器
	 * @author pcongda
	 */
	public void invokerhandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception;
}
