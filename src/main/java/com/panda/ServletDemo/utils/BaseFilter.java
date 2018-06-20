/**
 * 
 */
package com.panda.ServletDemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局编码设置，页面缓存设置,跨域设置
 * @author pcongda
 * @version 1.0
 */
@WebFilter(filterName="baseFilter",urlPatterns="/*",initParams= {@WebInitParam(name="encode",value="UTF-8")})
public class BaseFilter implements Filter{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseFilter.class);
	
	private FilterConfig config = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.info("开始进入BaseFilter....");
		 //将request和response强转成http协议的
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        String fEncode =  config.getInitParameter("encode");
        
        //设置编码
        req.setCharacterEncoding(fEncode);
        resp.setCharacterEncoding(fEncode);
       
        //设置页面不缓存
  		resp.setHeader("Pragma", "no-cache");
  		resp.setDateHeader("Expires", -1);
  		resp.addHeader("Cache-Control", "no-cache");
  		
  		//跨域设置
  		resp.setHeader("Access-Control-Allow-Origin","*");
  		//跨域支持方法设置
  		resp.setHeader("Access-Control-Allow-Method", "GET,POST,PUT,DELETE");
  		//是否允许请求带有验证信息
  		resp.setHeader("Access-Control-Allow-Credentials", "true");
  		
  		//HTTP状态码如果为204,则不会返回任何数据,需要把HTTP状态码更新为200
  		if(resp.getStatus()==204){
  			resp.setStatus(200);
  		}    

  		GetRequestWrapper myRequest = new GetRequestWrapper(req);
 		//传递被增强后的request
        chain.doFilter(myRequest, resp);   
	}
	
	@Override
	public void destroy() {
		this.config = null;
	}

}
