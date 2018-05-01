package com.panda.ServletDemo.mvcframework.bean;

import java.io.Serializable;
/**
 * 返回结果
 * @author pcongda
 *
 */
public class ResultResponse implements Serializable{

	private static final long serialVersionUID = -5979183088996742582L;
	
	private Integer code;
	private String msg;
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data= data;
	}
}
