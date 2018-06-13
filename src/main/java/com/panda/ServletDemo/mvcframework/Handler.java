package com.panda.ServletDemo.mvcframework;

import com.panda.ServletDemo.mvcframework.annotation.MyPathVariable;
import com.panda.ServletDemo.mvcframework.annotation.MyRequestParam;
import com.panda.ServletDemo.mvcframework.bean.MethodParam;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Action方法相关信息
 * @author pcongda
 *
 */
public class Handler implements Serializable{
	
	private static final long serialVersionUID = -5535386111271158957L;
	
	private Class<?> clazz; // Action类字节码对象
	private Method method; // 保存映射的方法
	private Matcher matcher; // 保存请求URL,支持正则表达式
	private Map<MethodParam,Integer> param = new LinkedHashMap<>(); //保存方法参数顺序
	private List<String> pathParamList = new LinkedList<>(); //路径上的参数名称
	private int typeRange; //请求方法是否在Action定义内： 0：在，1：不在
	private MyRequestMethod[] reqMethodTypes; // 限定请求方法类型

	public Handler(Class<?> clazz, Method method,int typeRange,List<String> pathParamList) {
		this.clazz = clazz;
		this.method = method;
		this.typeRange = typeRange;
		this.pathParamList = pathParamList;
		putParamIndexMapping(method);
	}
	
	public Handler(Class<?> clazz, Method method,int typeRange) {
		this.clazz = clazz;
		this.method = method;
		this.typeRange = typeRange;
		putParamIndexMapping(method);
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Method getMethod() {
		return method;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	public int getTypeRange() {
		return typeRange;
	}

	public void setTypeRange(int typeRange) {
		this.typeRange = typeRange;
	}
	
	public  Map<MethodParam,Integer> getParam() {
		return param;
	}

	public void setParam(Map<MethodParam,Integer> param) {
		this.param = param;
	}
	
	public List<String> getPathParamList() {
		return pathParamList;
	}

	public void setPathParamList(List<String> pathParamList) {
		this.pathParamList = pathParamList;
	}

	public MyRequestMethod[] getReqMethodTypes() {
		return reqMethodTypes;
	}

	public void setReqMethodTypes(MyRequestMethod[] reqMethodTypes) {
		this.reqMethodTypes = reqMethodTypes;
	}

	/**
	 * 放置参数及顺序
	 * @author pcongda
	 */
	private void putParamIndexMapping(Method method) {
		//提取方法中加了注解的参数
		Annotation[][] paramters =  method.getParameterAnnotations();
		for (int i = 0; i < paramters.length; i++) {
			for (Annotation a : paramters[i]) {
				if(a instanceof MyRequestParam){
					String paramName = ((MyRequestParam)a).value();
					if(!"".equals(paramName.trim())){
						param.put(new MethodParam(paramName, paramName.getClass()),i);
					}
				}else if(a instanceof MyPathVariable){
					String pathName = ((MyRequestParam)a).value();
					if(!"".equals(pathName.trim())){
						param.put(new MethodParam(pathName, pathName.getClass()),i);
					}
				}
			}
		}
		
		//提取方法中的参数
		Parameter[] params = method.getParameters();
		for (int i = 0; i < params.length; i++) {
			param.put(new MethodParam(params[i].getName(), params[i].getType()),i);
		}
	}
}
