/**
 * 
 */
package com.panda.ServletDemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 使用装饰器模式过滤GET请求编码
 * @author pcongda
 * @version 1.0
 */
public class GetRequestWrapper extends HttpServletRequestWrapper{
	
	private static final Logger logger = LoggerFactory.getLogger(GetRequestWrapper.class);
	
	private HttpServletRequest req;
	
	public GetRequestWrapper(HttpServletRequest request) {
		super(request);
		this.req = request;
	}
	
	
	@Override
	public String getParameter(String name) {
		//得到参数
		String value = this.req.getParameter(name);
		
		//没有参数直接返回
		if(value == null) {
			return value;
		}
		
		//非GET方法直接返回
		if(!this.req.getMethod().equalsIgnoreCase("get")) {
			return value;
		}
		
		//GET方法 
		try {
			value = new String(value.getBytes("ISO-8859-1"), req.getCharacterEncoding());//转为UTF-8
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持该编码!",e);
			e.printStackTrace();
		}
		return value;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String,String[]> parMap =  this.req.getParameterMap();
		//没有参数直接返回空
		if(parMap == null) return null;
		
		//非GET方法直接返回
		if(!this.req.getMethod().equalsIgnoreCase("get")) {
			return parMap;
		}
		
		//遍历map,进行编码处理
		for(String key : parMap.keySet()){
			String[] values = parMap.get(key);
			for(int i=0; i<values.length;i++){
				try {
					values[i] = new String(values[i].getBytes("ISO-8859-1"), this.req.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					logger.error("不支持该编码!",e);
					e.printStackTrace();
				}
			}
		}
		return parMap;
	}
	
	
	@Override
	public String[] getParameterValues(String name) {
		String[] values = this.req.getParameterValues(name);
		
		//没有参数直接返回空
		if(values == null) return null;
		
		//非GET方法直接返回
		if(!this.req.getMethod().equalsIgnoreCase("get")) {
			return values;
		}
		
		for(int i = 0; i < values.length; i++) {
			try {
				values[i] = new String(values[i].getBytes("ISO-8859-1"), this.req.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持该编码!",e);
				e.printStackTrace();
			}
		}
		return values;
	}
}
