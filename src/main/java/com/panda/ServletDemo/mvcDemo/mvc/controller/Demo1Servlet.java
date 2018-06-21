package com.panda.ServletDemo.mvcDemo.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.panda.ServletDemo.entity.User;
import com.panda.ServletDemo.mvcDemo.mvc.service.Demo1Service;
import com.panda.ServletDemo.mvcframework.annotation.*;
import com.panda.ServletDemo.mvcframework.bean.FreemarkerResponse;
import com.panda.ServletDemo.mvcframework.bean.ResultResponse;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;
import com.panda.ServletDemo.utils.ResultResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@MyController
@MyRequsetMapping(value="/demo1")
public class Demo1Servlet {
		
	@MyAutoWired
	private Demo1Service demo1Service;
	
	@MyRequsetMapping(value="/queryList1",method = {MyRequestMethod.GET})
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
		return "r:/demo1/test/a?name="+name;
	}
	
	@MyRequsetMapping(value="/queryList4")
	public ResultResponse queryList1(String jsonData,HttpServletResponse resp,HttpServletRequest req){
		User user = JSON.parseObject(jsonData, User.class);
		System.out.println(user.toString());
		return ResultResponseUtil.success("Ajax POST请求可以了", user);
	}
	
	@MyRequsetMapping(value="/queryList3/{id}",method={MyRequestMethod.POST})
	public String queryList4(HttpServletRequest req,HttpServletResponse resp,@MyPathVariable("id") Integer id, String name) {
		System.out.println(id+":"+name);
		return "/test/index";
	}

	@MyRequsetMapping(value="/test/a")
	public String to404(HttpServletRequest req,HttpServletResponse resp,String name) {
		req.setAttribute("msg","信息1");
		req.setAttribute("name",name);
		req.getSession().setAttribute("errorMsg","测试数据1");
		return "/common/404";
	}

	@MyRequsetMapping(value="/test/b")
	public String toRed(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("msg","信息2");
		req.getSession().setAttribute("errorMsg","测试数据2");
		return "r:/redirectTest.html";
	}

	/**
	 *  请求 404 接口
	 * @param req
	 * @param resp
	 * @return
	 */
	@MyRequsetMapping(value="/404.html")
	public String to404Page(HttpServletRequest req,HttpServletResponse resp) {
		return "/common/404";
	}

	/**
	 *  请求 500 接口
	 * @param req
	 * @param resp
	 * @return
	 */
	@MyRequsetMapping(value="/500.html")
	public String to500Page(HttpServletRequest req,HttpServletResponse resp) {
		return "/common/500";
	}

	/**
	 * freemarker 测试
	 * @param req
	 * @param resp
	 * @return
	 */
	@MyRequsetMapping(value="/test/freemarker")
	public FreemarkerResponse toFreemarkerA(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> data = new HashMap<>();
		req.setAttribute("user","Big Joe");
		data.put("user", "Big Joe");
		return ResultResponseUtil.getFreemarkerResult("/a",data);
	}
}
