package com.panda.ServletDemo.mvcframework.impl;

import com.panda.ServletDemo.mvcframework.Handler;
import com.panda.ServletDemo.mvcframework.HandlerInvoker;
import com.panda.ServletDemo.mvcframework.ViewResolver;
import com.panda.ServletDemo.mvcframework.bean.MethodParam;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;
import com.panda.ServletDemo.mvcframework.exception.RequsetException;
import com.panda.ServletDemo.mvcframework.util.InstanceFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;

/**
 * 默认的处理器执行器
 * @author pcongda
 *
 */
public class DefaultHandlerInvoker implements HandlerInvoker{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerInvoker.class);
	
	//获取视图解析器
    private ViewResolver viewResolver = InstanceFactory.getViewResolver();
	
	@Override
	public void invokerhandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
		//检查是否hander不存在或请求方法类型不匹配
		checkHanderStatus(handler,request);
		//获取Action相关信息
		Class<?> actionCls = handler.getClazz();
		Method method = handler.getMethod();
		//创建 Action实例
        Object actionInstance = actionCls.newInstance();
        //创建 Action方法的参数列表
        Object[] methodParamArray = createActionMethodParamList(request,response, handler);
        //检查参数个数是否一致
        checkParamList(method,methodParamArray);
        // 取消类型安全检测
        method.setAccessible(true);
        //反射调用方法
        Object returnObj = method.invoke(actionInstance, methodParamArray);
        //解析视图
        viewResolver.resolverView(request, response, returnObj);
	}

	/**
	 * 创建参数列表
	 * @author pcongda
	 */
	private Object[] createActionMethodParamList(HttpServletRequest request, HttpServletResponse response, Handler handler) {
		// 获取参数列表
        Map<MethodParam,Integer> paramMap = handler.getParam();
        //获取方法参数列表
        Class<?>[] actionParamTypes = handler.getMethod().getParameterTypes();
        //获取方法参数值
        Object[] paramValue = new Object[actionParamTypes.length];
        //创建路径参数列表(支持正则)
        paramValue = createPathParamList(handler.getMatcher(),paramMap,handler.getPathParamList(),paramValue);
        
        //requset,response赋值
        for (Map.Entry<MethodParam, Integer> entry : paramMap.entrySet()) {
			if(entry.getKey().getClazz().equals(HttpServletRequest.class)){
				paramValue[entry.getValue()] = request;
			}else if(entry.getKey().getClazz().equals(HttpServletResponse.class)){
				paramValue[entry.getValue()] = response;
			}
		}
        
        //判断是普通字段上传还是文件上传
        if(ServletFileUpload.isMultipartContent(request)) {
        	//multipart类型,先不管
        }else { //普通请求
    		Map<String, Object> reqParamMap = getRequestParamMap(request);
    		for(Map.Entry<String, Object> obj:reqParamMap.entrySet()) {
    			String value = obj.getValue().toString();
    			//方法定义的参数没有请求中的参数，跳过
    			for (Map.Entry<MethodParam, Integer> entry : paramMap.entrySet()) {
	    			if(!entry.getKey().getName().equals(obj.getKey())) {
	    				continue;
	    			}
	    			Class<?> clazz = entry.getKey().getClazz();
	    			paramValue[entry.getValue()] = (convert(clazz,value));
    			}
    		}
        }
		return paramValue;
	}


	/**
	 * 参数类型转换:支持int/Integer double/Double long/Long String
	 * @author pcongda
	 */
	private Object convert(Class<?> type, String value) {
		if(type.equals(int.class) || type.equals(Integer.class)) {
			return Integer.valueOf(value);
		}else if(type.equals(double.class) || type.equals(Double.class)) {
			return Double.valueOf(value);
		}else if(type.equals(long.class) || type.equals(Long.class)) {
			return Long.valueOf(value);
		}
		return value;
	}

	/**
	 * 拼接方法参数列表，带正则
	 * @author pcongda
	 */
	private Object[] createPathParamList(Matcher matcher,Map<MethodParam,Integer> paramMap,List<String> restUrlNameList,Object[] paramValue) {
        //遍历正则表达式中匹配的组
		for(int i = 1; i <= matcher.groupCount(); i++) {
			for (Map.Entry<MethodParam,Integer> paramObj : paramMap.entrySet()) {
				//请求url中参数名和方法参数一致 ,则添加 
				if(!restUrlNameList.get(i-1).equals(paramObj.getKey().getName())){
					continue;
				}
	    	   //按顺序获取正则参数
	    	   String param = matcher.group(i);
	    	   paramValue[paramObj.getValue()] = convert(paramObj.getKey().getClazz(),param);
    	   }
       }
       return paramValue;
	}
	
	
	/**
     * 获取请求中所有参数:(不包含文件)
     * @author pcongda
     */
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        try {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {  //复杂请求
            	//将url解码
            	String queryString = URLDecoder.decode(getString(request.getInputStream()), "UTF-8");
                if (queryString.length() != 0 && queryString != null) {
                	//转为字符串数组
                    String[] qsArray = StringUtils.splitByWholeSeparator(queryString, "&");
                    if (!(qsArray == null || qsArray.length == 0)) {
                        for (String qs : qsArray) {
                            String[] array = StringUtils.splitByWholeSeparator(qs, "=");
                            if ((array != null && array.length != 0) && array.length == 2) {
                                String paramName = array[0];
                                String paramValue = array[1];
                                if (paramMap.containsKey(paramName)) {
                                    paramValue = paramMap.get(paramName) + String.valueOf((char)29) + paramValue;
                                }
                                paramMap.put(paramName, paramValue);
                            }
                        }
                    }
                }
            } else {   //简单请求：GET,POST
                Enumeration<String> paramNames = request.getParameterNames();
                while(paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String[] paramValues = request.getParameterValues(paramName);
                    if (paramValues != null && paramValues.length != 0) {
                        if (paramValues.length == 1) {  //input type=text
                            paramMap.put(paramName, paramValues[0]);
                        } else { //input type=checkbox ect
                            StringBuilder paramValue = new StringBuilder("");
                            for (int i = 0; i < paramValues.length; i++) {
                                paramValue.append(paramValues[i]);
                                if (i != paramValues.length - 1) { //最后一个不用加：
                                    paramValue.append(String.valueOf((char)29));
                                }
                            }
                            paramMap.put(paramName, paramValue.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取请求参数出错！", e);
            throw new RequsetException("获取请求参数出错！",e);
        }
        return paramMap;
    }
    
    
    /**
     * inputStream 转  String
     * @author pcongda
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error("inputStream 转 String 出错！", e.getMessage());
            throw new RequsetException("inputStream 转 String 出错！",e);
        }
        return sb.toString();
    }
    
    /**
     * 检查参数是否对应以及个数是否相同
     * @author pcongda
     */
    private void checkParamList(Method actionMethod, Object[] actionMethodParamArray) {
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        if (actionMethodParameterTypes.length != actionMethodParamArray.length) {
            String message = String.format("参数个数不匹配，无法调用:%s方法！原始参数个数：%d，实际参数个数：%d", actionMethod.getName(),actionMethodParameterTypes.length, actionMethodParamArray.length);
            throw new RequsetException(message);
        }
    }

	private void checkHanderStatus(Handler handler,HttpServletRequest request) {
		//未找到方法 抛出异常
		if (handler == null) {
			logger.error(String.format("调用：%s请求方法不存在",request.getRequestURI()));
			throw new RequsetException("请求不存在");
		}

		// 请求方法不在指定内，抛出异常
		if (handler.getTypeRange() == 1) {
			logger.error(String.format("调用：%s请求方法类型不匹配",request.getRequestURI()));
			MyRequestMethod[] myMethod = handler.getReqMethodTypes();
			throw new RequsetException(String.format("请求方法类型不匹配，需要方法类型:%s,实际方法类型:%s", Arrays.toString(myMethod),request.getMethod()));
		}
	}
}
