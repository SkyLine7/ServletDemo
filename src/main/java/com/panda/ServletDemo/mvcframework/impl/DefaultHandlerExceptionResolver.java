package com.panda.ServletDemo.mvcframework.impl;

import com.alibaba.fastjson.JSONObject;
import com.panda.ServletDemo.mvcframework.HandlerExceptionResolver;
import com.panda.ServletDemo.mvcframework.exception.RequsetException;
import com.panda.ServletDemo.mvcframework.exception.ServiceException;
import com.panda.ServletDemo.util.ResultResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的异常解析器
 * @author pcongda
 *
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);
	
	@Override
	public void resolverException(HttpServletRequest request, HttpServletResponse response, Exception e){
        //请求异常
        if(e instanceof RequsetException){
            response.setStatus(400);
            //是否是Ajax请求
            if (request.getHeader("X-Requested-With") == null) {
                //转发到404页面
                request.setAttribute("errorMsg",e.getMessage());
	            logger.error("请求异常，错误原因为:{}",e.getMessage());
                try {
	                request.getRequestDispatcher("/demo1/404.html").forward(request,response);
                } catch (Exception e1){
					e.printStackTrace();
                }
            }else{
	            response.setStatus(500);
	            JSONObject json = new JSONObject();
				json.put("msg",ResultResponseUtil.badRequest(e.getMessage()).getMsg());
				json.put("code",ResultResponseUtil.badRequest(e.getMessage()).getCode());
				json.put("data",ResultResponseUtil.badRequest(e.getMessage()).getData());
				response.setContentType("application/json;charset=utf-8");
				try {
					response.getWriter().println(json.toString());
					response.getWriter().flush();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
            }
	        //服务器异常
	        }else if(e instanceof ServiceException){
	            response.setStatus(500);
	             //是否是Ajax请求
	            if (request.getHeader("X-Requested-With") == null) {
                    //转发到500页面
	                request.setAttribute("errorMsg",e.getMessage());
	                logger.error("服务器异常，错误原因为:{}",e.getMessage());
	                try {
		                request.getRequestDispatcher("/demo1/500.html").forward(request,response);
	                } catch (Exception e1){
		                e.printStackTrace();
	                }
	            }else{
		            response.setStatus(500);
		            JSONObject json = new JSONObject();
					json.put("msg",ResultResponseUtil.serverError(e.getMessage()).getMsg());
					json.put("code",ResultResponseUtil.serverError(e.getMessage()).getCode());
					json.put("data",ResultResponseUtil.serverError(e.getMessage()).getData());
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
