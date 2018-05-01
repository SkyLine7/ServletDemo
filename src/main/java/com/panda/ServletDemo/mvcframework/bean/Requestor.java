package com.panda.ServletDemo.mvcframework.bean;

import java.io.Serializable;

import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;

/**
 * 封装http请求
 * 
 * @author pcongda
 *
 */
public class Requestor implements Serializable {

	private static final long serialVersionUID = -3064371760592711062L;

	private String requestPath; // 请求路径
	private MyRequestMethod[] requsetMethod; // 请求方法
	
	/**
	 * @param requestPath
	 * @param requsetMethod
	 */
	public Requestor(String requestPath, MyRequestMethod[] requsetMethod) {
		this.requestPath = requestPath;
		this.requsetMethod = requsetMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public MyRequestMethod[] getRequsetMethod() {
		return requsetMethod;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public void setRequsetMethod(MyRequestMethod[] requsetMethod) {
		this.requsetMethod = requsetMethod;
	}

}
