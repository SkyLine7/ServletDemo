package com.panda.ServletDemo.mvcframework.impl;

import com.alibaba.fastjson.JSONObject;
import com.panda.ServletDemo.mvcframework.ContainerListener;
import com.panda.ServletDemo.mvcframework.ViewResolver;
import com.panda.ServletDemo.mvcframework.bean.ResultResponse;
import com.panda.ServletDemo.mvcframework.exception.RequsetException;
import com.panda.ServletDemo.mvcframework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的视图解析器
 * @author pcongda
 *
 */
public class DefaultViewResolver implements ViewResolver{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultViewResolver.class);

	@Override
	public void resolverView(HttpServletRequest request, HttpServletResponse response, Object result) {

		//没有返回值，说明响应的不是json或者页面,流或其他
		if (result == null) return ;

		// 页面
		if(result instanceof java.lang.String) {
			String str = result.toString();
			int index = str.indexOf(":");
			if(index == -1) { //转发
				TemplateEngine engine = ContainerListener.templateEngine(request.getServletContext());
		        WebContext context = new WebContext(request, response, request.getServletContext());      
		        try {
		        	//使用thymeleaf加载模版
					engine.process(str, context, response.getWriter());
				} catch (IOException e) {
					logger.error("加载thymeleaf模版引擎失败，原因是:{}",e.getMessage());
					throw new RequsetException("加载thymeleaf模版引擎失败", e);
				}
			}else {
				String prefix = str.substring(0, index);//前缀
				String path = str.substring(index + 1);//路径
				if(prefix.equals("r")) {//重定向
					try {
						response.sendRedirect(request.getContextPath() + path);
					} catch (IOException e) {
						logger.error("重定向失败，原因是:{}",e.getMessage());
						throw new ServiceException("服务器错误!",e);
					}
				}
			}
		}else{  // json
			response.setContentType("application/json;charset=utf-8");
			try {
				if(result instanceof ResultResponse){//返回json数据
					ResultResponse res = (ResultResponse)result;
					Object entity = res.getData();
					JSONObject json = new JSONObject();
					
					json.put("msg", res.getMsg());
					json.put("code",res.getCode());//200
					json.put("data", JSONObject.toJSON(entity) != null ? JSONObject.toJSON(entity) : null);
					
					response.getWriter().println(json.toString());
					response.getWriter().flush();
				}
			} catch (Exception e) {
				logger.error(String.format("返回json数据失败:{}", e.getMessage()));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//500
				throw new ServiceException("服务器错误！",e);

			}
		}
	}

}
