package com.panda.ServletDemo.mvcframework.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.panda.ServletDemo.mvcframework.ContainerListener;
import com.panda.ServletDemo.mvcframework.Handler;
import com.panda.ServletDemo.mvcframework.HandlerMapping;
import com.panda.ServletDemo.mvcframework.bean.Requestor;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;

/**
 * 默认处理器映射器
 * @author pcongda
 *
 */
public class DefaultHandlerMapping implements HandlerMapping{

	@SuppressWarnings({ "static-access" })
	@Override
	public Handler getHandler(String reqMethod, String reqPath) {
		Handler handler = null;
		//获取方法映射
		Map<Requestor, Handler> actionMap = ContainerListener.getActionMap();
		//循环
		for(Map.Entry<Requestor, Handler> entity1 :actionMap.entrySet()) {
			Requestor requstor = entity1.getKey();
			String path = requstor.getRequestPath();
			//获取请求路径匹配器（使用正则表达式匹配请求路径并从中获取相应的请求参数）
			Matcher pathMatcher = Pattern.compile(path).matcher(reqPath);
            //判断请求路径是否匹配
            if (pathMatcher.matches()) {
                handler = entity1.getValue();
                //设置请求路径匹配器
                if (handler != null) {
                    handler.setMatcher(pathMatcher);
                }
                break;
            }
		}
		
		for (Map.Entry<Requestor, Handler> entity2 : actionMap.entrySet()) {
			//找hander.Mathcher不为null的
			if(entity2.getValue().getMatcher() != null) {
				Requestor requstor = entity2.getKey();
				MyRequestMethod[] myMethod = requstor.getRequsetMethod();
				//判断请求方法类型是否包含页面请求类型
				if(myMethod.length > 0){
					handler.setTypeRange(1);
					for (int i = 0; i < myMethod.length; i++) {
						if(reqMethod.equals(myMethod[i].values())){
							handler.setTypeRange(0);
							break;
						}
					}
				}
			}
		}
		return handler;
	}

}
