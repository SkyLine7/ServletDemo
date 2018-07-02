package com.panda.ServletDemo.mvcdemo.mvc.service;

import com.panda.ServletDemo.mvcframework.annotation.MyService;

@MyService(value="demo1Service")
public class Demo1ServiceImpl implements Demo1Service {

	@Override
	public String get(String name) {
		return "My name is " + name;
	}

}
