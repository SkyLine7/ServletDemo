package com.panda.ServletDemo.mvcDemo.mvc.controller;

import com.alibaba.fastjson.JSONArray;
import com.panda.ServletDemo.entity.Student;
import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;
import com.panda.ServletDemo.util.SXSSFWorkbookUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@MyController
@MyRequsetMapping("/outputXLS")
public class OutPutXLSX {

	@MyRequsetMapping("/downloadToXLSX.html")
	public void downloadToXLSX(HttpServletRequest request, HttpServletResponse response){
		//先 生成10000条假数据
		int count = 100000;
		JSONArray json = new JSONArray();
		for(int i=0;i<100000;i++){
			Student s = new Student();
			s.setName("POI"+i);
			s.setAge(i);
			s.setBirthday(new Date());
			s.setHeight(i);
			s.setWeight(i);
			s.setSex(i/2==0?false:true);
			json.add(s);
		}
		Map<String,String> headMap = new LinkedHashMap<>();
		headMap.put("name","姓名");
		headMap.put("age","年龄");
		headMap.put("birthday","生日");
		headMap.put("height","身高");
		headMap.put("weight","体重");
		headMap.put("sex","性别");

		//调用下载excel接口
		SXSSFWorkbookUtil.createDatas(count,"测试数据.xlsx",headMap,json,request,response);
	}

	@MyRequsetMapping("/downloadToXLS.html")
	public void downloadToXLS(HttpServletRequest request, HttpServletResponse response){
		//先 生成10000条假数据
		int count = 100000;
		JSONArray json = new JSONArray();
		for(int i=0;i<100000;i++){
			Student s = new Student();
			s.setName("POI"+i);
			s.setAge(i);
			s.setBirthday(new Date());
			s.setHeight(i);
			s.setWeight(i);
			s.setSex(i/2==0?false:true);
			json.add(s);
		}
		Map<String,String> headMap = new LinkedHashMap<>();
		headMap.put("name","姓名");
		headMap.put("age","年龄");
		headMap.put("birthday","生日");
		headMap.put("height","身高");
		headMap.put("weight","体重");
		headMap.put("sex","性别");

		//调用下载excel接口
		SXSSFWorkbookUtil.createData(count,"测试数据.xls",headMap,json,request,response);
	}
}
