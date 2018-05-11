package com.panda.ServletDemo.mvcframework.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panda.ServletDemo.mvcframework.Handler;
import com.panda.ServletDemo.mvcframework.HandlerExceptionResolver;
import com.panda.ServletDemo.mvcframework.HandlerInvoker;
import com.panda.ServletDemo.mvcframework.HandlerMapping;
import com.panda.ServletDemo.mvcframework.exception.RequsetException;
import com.panda.ServletDemo.mvcframework.util.InstanceFactory;


/**
 * 前端控制器
 * @author pcongda
 *
 */
@WebServlet(urlPatterns="/*",loadOnStartup= 0,initParams={@WebInitParam(name="contextConfigLocation",value="application.properties")}) //Tomcat容器启动时加载
public class MyDispatcherServlet extends HttpServlet{
	private static final long serialVersionUID = 1459365600624171271L;

	private static final Logger logger = LoggerFactory.getLogger(MyDispatcherServlet.class);
	//获取处理器映射器
	private HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();
	//获取处理器执行器
	private HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();
	//获取异常解析器
	private HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getExceptionResolver();
	
	@Override
	public void init(ServletConfig config) throws ServletException {}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求路径和请求方法
		String reqPath = req.getRequestURI();
		String reqMethod = req.getMethod();
		
		//获取项目名称
		String projectName = req.getContextPath();
		//替换项目名称为：/为最终的路径,多个//替换为一个/
		reqPath = reqPath.replace(projectName, "").replaceAll("/+", "/");
		
		//当访问的不是项目路径时,去掉请求路径末尾的/
        if(reqPath.endsWith("/") && reqPath.length() > 1) {
        	reqPath = reqPath.substring(0, reqPath.length() - 1);
        }
        logger.debug("开始调用请求：方法：{},路径：{}", reqMethod, reqPath);
        
        //将"/"请求重定向到首页接口
        if (reqPath.equals("/") || reqPath.equals("")) {
        	resp.sendRedirect(req.getContextPath()+"/demo2/one");
            return;
        }
        //获取处理器
        Handler handler = handlerMapping.getHandler(reqMethod, reqPath);
        //未找到方法 跳转到 404 页面
        if (handler == null) {
        	logger.error(String.format("调用：%s方法不存在",req.getRequestURI()));
        	throw new RequsetException("请求不存在");
        }
        try {
        	//调用处理器进入用户方法
			handlerInvoker.invokerhandler(req, resp, handler);
		} catch (Exception e) {
			//解析视图异常
			handlerExceptionResolver.ResolverException(req, resp, e);
		}
	}
	
}
