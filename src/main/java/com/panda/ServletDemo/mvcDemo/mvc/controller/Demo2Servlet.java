package com.panda.ServletDemo.mvcDemo.mvc.controller;

import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * vue页面1
	 * @author pcongda
	 */
	@MyRequsetMapping("/vue1")
	public String toVuePage1(HttpServletRequest req,HttpServletResponse resp) {
		return "/vue/vue1";
	}

	/**
	 * vue页面2
	 * @author pcongda
	 */
	@MyRequsetMapping("/vue2")
	public String toVuePage2(HttpServletRequest req,HttpServletResponse resp) {
		return "/vue/vue2";
	}

	/**
	 * vue页面4
	 * @author pcongda
	 */
	@MyRequsetMapping("/vue4")
	public String toVuePage4(HttpServletRequest req,HttpServletResponse resp) {
		return "/vue/vue4";
	}
}
