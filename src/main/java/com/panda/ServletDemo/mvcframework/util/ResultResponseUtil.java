package com.panda.ServletDemo.mvcframework.util;

import com.panda.ServletDemo.mvcframework.bean.FreemarkerResponse;
import com.panda.ServletDemo.mvcframework.bean.ResultResponse;

import java.util.Map;

/**
 * 统一响应工具类
 * @author pcongda
 * @version 1.0
 */
public class ResultResponseUtil {
	
	//json响应格式
	private static ResultResponse getResultResponse(Integer code, String msg, Object obj){
		ResultResponse result = new ResultResponse();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(obj);
		return result;
	}
	
	//响应成功无数据
	public static ResultResponse success(String msg){
		return getResultResponse(200,msg,null);
	}
	
	//响应成功有数据
	public static ResultResponse success(String msg,Object obj){
		return getResultResponse(200,msg,obj);
	}
	
	//响应失败:400
	public static ResultResponse badRequest(String msg){
		return getResultResponse(400,msg,null);
	}
	
	//响应失败:404
	public static ResultResponse requsetNotFound(String msg){
		return getResultResponse(404,msg,null);
	}
	
	//响应失败:406
	public static ResultResponse illegalArguments(String msg){
		return getResultResponse(406,msg,null);
	}
	
	//响应失败:500
	public static ResultResponse serverError(String msg){
		return getResultResponse(500,msg,null);
	}

	//响应freemarker 对象
	public static FreemarkerResponse getFreemarkerResult(String url,Map<String,Object> dataMap){
		return new FreemarkerResponse(url,dataMap);
	}
}
