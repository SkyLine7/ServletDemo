package com.panda.ServletDemo.mvcDemo.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;

/**
 * @author pcongda
 *
 */
@MyController
@MyRequsetMapping("/demo2")
public class Demo2Servlet {
	
	/**
	 * 首页接口
	 * @author pcongda
	 */
	@MyRequsetMapping("/one")
	public String indexPage(HttpServletRequest req,HttpServletResponse resp) {
		System.out.println("普通GET请求可以啦");
		return "/test/index";
	}
}
