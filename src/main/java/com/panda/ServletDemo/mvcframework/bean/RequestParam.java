package com.panda.ServletDemo.mvcframework.bean;

import java.util.Map;

/**
 * 封装请求参数
 * @author pcongda
 *
 */
public class RequestParam {
	private final Map<String, Object> filedMap;

	public RequestParam(Map<String,Object> filedMap) {
		this.filedMap = filedMap;
	}
	
	public Object get(String name) {
		return filedMap.get(name);
	}
	
	public String getString(String name) {
        return String.valueOf(get(name));
    }

    public double getDouble(String name) {
        return Double.parseDouble(get(name).toString());
    }

    public long getLong(String name) {
        return Long.parseLong(get(name).toString());
    }

    public int getInt(String name) {
        return Integer.parseInt(get(name).toString());
    }
	
	public Map<String,Object> getFiledMap(){
		return filedMap;
	}
	
}
