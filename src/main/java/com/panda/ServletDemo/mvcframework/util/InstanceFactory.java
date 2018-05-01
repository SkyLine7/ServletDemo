package com.panda.ServletDemo.mvcframework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panda.ServletDemo.mvcframework.HandlerExceptionResolver;
import com.panda.ServletDemo.mvcframework.HandlerInvoker;
import com.panda.ServletDemo.mvcframework.HandlerMapping;
import com.panda.ServletDemo.mvcframework.ViewResolver;
import com.panda.ServletDemo.mvcframework.impl.DefaultHandlerExceptionResolver;
import com.panda.ServletDemo.mvcframework.impl.DefaultHandlerInvoker;
import com.panda.ServletDemo.mvcframework.impl.DefaultHandlerMapping;
import com.panda.ServletDemo.mvcframework.impl.DefaultViewResolver;

/**
 * 实例工厂
 * @author pcongda
 *
 */
public class InstanceFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
	
	//缓存已经创建的实例
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    //默认处理器映射器字符key
    private static final String HANDLER_MAPPING = "mvcframework.default.handler-mapping";
    
    //默认处理器执行器字符key
    private static final String HANDLER_INVOKER = "mvcframework.default.handler-invoker";
    
    //默认的视图解析器字符key
    private static final String VIEW_RESOLVER = "mvcframework.default.view-resolver";
    
    //默认的异常解析器字符key
    private static final String EXCEPTION_RESOLVER = "mvcframework.default.exception-resolver";
    
    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }
    
    public static HandlerInvoker getHandlerInvoker() {
    	return getInstance(HANDLER_INVOKER, DefaultHandlerInvoker.class);
    }
    
    public static ViewResolver getViewResolver() {
    	return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
    }
    
    public static HandlerExceptionResolver getExceptionResolver() {
    	return getInstance(EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }
    
    @SuppressWarnings("unchecked")
	private static<T> T getInstance(String cacheKey,Class<T> defaultClazz) {
    	//如果缓存中存在该对象，从缓存中取
    	if(cache.containsKey(cacheKey)) {
    		return (T)cache.get(cacheKey);
    	}
    	T t = null;
		try {
			t = defaultClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("创建:{}实例错误:{}", defaultClazz,e);
		}
    	//若实例不为空，则将其放入缓存
        if (t != null) {
            cache.put(cacheKey, t);
        }
    	return t;
    }
}
