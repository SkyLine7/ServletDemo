package com.panda.ServletDemo.mvcframework.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.panda.ServletDemo.mvcframework.HandlerExceptionResolver;
import com.panda.ServletDemo.mvcframework.exception.RequsetException;
import com.panda.ServletDemo.mvcframework.exception.ServiceException;
import com.panda.ServletDemo.utils.ResultResponseUtil;

/**
 * 默认的异常解析器
 * @author pcongda
 *
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);
	
	@Override
	public void ResolverException(HttpServletRequest request, HttpServletResponse response, Exception e){
		//获取异常种类
        Throwable cause = e.getCause();
        if(cause == null){
            logger.error(e.getMessage(), e);
            return;
        }
        
	        //请求异常
	        if(cause instanceof RequsetException){
	        	response.setStatus(404);
	            //是否是Ajax请求
	            if (request.getHeader("X-Requested-With") == null) {
	            	try {
	            		//重定向到404
						response.sendRedirect(request.getContextPath()+"/templates/common/404.html");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            }else{
		        	JSONObject json = new JSONObject();
					json.put("msg",ResultResponseUtil.requsetNotFound("没有找到该方法").getMsg());
					json.put("code",ResultResponseUtil.requsetNotFound("没有找到该方法").getCode());
					json.put("data",ResultResponseUtil.requsetNotFound("没有找到该方法").getData());
					response.setContentType("application/json;charset=utf-8");
					try {
						response.getWriter().println(json.toString());
						response.getWriter().flush();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
	            }
	        //服务器异常 
	        }else if(cause instanceof ServiceException){
	        	response.setStatus(500);
	        	 //是否是Ajax请求
	            if (request.getHeader("X-Requested-With") == null) {
	            	try {
	            		//重定向到500
						response.sendRedirect(request.getContextPath()+"/templates/common/500.html");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            }else{
		        	JSONObject json = new JSONObject();
					json.put("msg",ResultResponseUtil.serverError("服务器出错了").getMsg());
					json.put("code",ResultResponseUtil.serverError("服务器出错了").getCode());
					json.put("data",ResultResponseUtil.serverError("服务器出错了").getData());
					response.setContentType("application/json;charset=utf-8");
					try {
						response.getWriter().println(json.toString());
						response.getWriter().flush();
					} catch (IOException e2) {
						e2.printStackTrace();
				}
	        }
		}
	}
}
