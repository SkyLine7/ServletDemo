package com.panda.ServletDemo.mvcframework.bean;

import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 封装http请求
 * @author pcongda
 *
 */
public class Requestor implements Serializable {

	private static final long serialVersionUID = -3064371760592711062L;
	//请求路径
	private String requestPath;
	//请求方法限定
	private MyRequestMethod[] requsetMethod;
	
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


	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
