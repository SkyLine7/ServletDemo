package com.panda.ServletDemo.mvcframework.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * freemarker 响应对象
 * @author pcongda
 */
public class FreemarkerResponse implements Serializable{

	private static final long serialVersionUID = 797097186345353287L;

	private String url;
	private Map<String,Object> dataMap;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public FreemarkerResponse(String url, Map<String, Object> dataMap) {
		this.url = url;
		this.dataMap = dataMap;
	}
}
