package com.panda.ServletDemo.mvcDemo.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.panda.ServletDemo.entity.User;
import com.panda.ServletDemo.mvcDemo.mvc.service.Demo1Service;
import com.panda.ServletDemo.mvcframework.annotation.MyAutoWired;
import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyPathVariable;
import com.panda.ServletDemo.mvcframework.annotation.MyRequestParam;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;
import com.panda.ServletDemo.mvcframework.bean.ResultResponse;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;
import com.panda.ServletDemo.utils.ResultResponseUtil;

@MyController
@MyRequsetMapping(value="/demo1")
public class Demo1Servlet {
		
	@MyAutoWired
	private Demo1Service demo1Service;
	
	@MyRequsetMapping(value="/queryList1")
	public ResultResponse queryList1(String name,Integer age,String hegiht,HttpServletResponse resp,HttpServletRequest req){
		req.setAttribute("name", name);
		return ResultResponseUtil.success("Ajax GET请求可以啦", new User(name, age, hegiht));
	}
	
	@MyRequsetMapping(value="/queryList2")
	public String queryList2(HttpServletRequest req,HttpServletResponse resp,@MyRequestParam("name") String name){
		String str = demo1Service.get(name);
		System.out.println(str);
		return "r:/demo1/queryList3/3";
	}
	
	@MyRequsetMapping(value="/queryList3/{id}/{name}")
	public String queryList3(HttpServletRequest req,HttpServletResponse resp,@MyPathVariable("id") Integer id,@MyPathVariable("name") String name) {
		req.setAttribute("name", name);
		req.setAttribute("id", id);
		return "/test/a";
	}
	
	@MyRequsetMapping(value="/queryList4")
	public ResultResponse queryList1(String jsonData,HttpServletResponse resp,HttpServletRequest req){
		User user = JSON.parseObject(jsonData, User.class);
		System.out.println(user.toString());
		return ResultResponseUtil.success("Ajax POST请求可以了", user);
	}
	
	//method={MyRequestMethod.GET}功能待实现
	@MyRequsetMapping(value="/queryList3/{id}",method={MyRequestMethod.GET})
	public String queryList4(HttpServletRequest req,HttpServletResponse resp,@MyPathVariable("id") Integer id, String name) {
		System.out.println(id+":"+name);
		return "/test/index";
	}
}
