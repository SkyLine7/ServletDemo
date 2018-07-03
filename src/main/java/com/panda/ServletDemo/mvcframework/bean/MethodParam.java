package com.panda.ServletDemo.mvcframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 封装方法中的参数列表及顺序
 * @author pcongda
 *
 */
public class MethodParam implements Serializable{
	
	private static final long serialVersionUID = -963415862767544073L;

	//参数名称
	private String name;
	//参数类型
	private Class<?> clazz;
	
	
	public MethodParam(String name, Class<?> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
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
