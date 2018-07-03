package com.panda.ServletDemo.mvcdemo.mvc.controller;

import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;
import com.panda.ServletDemo.util.HSSFWorkbookUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@MyController
@MyRequsetMapping("/output")
public class outputXLS {

	@MyRequsetMapping("/downloadToXLS.html")
	public void createData(HttpServletRequest request, HttpServletResponse response){
		String sheetName = "导出为Excel";
		String fileName = "测试数据.xls";
		// 显示列
		String[] title = new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
		String[][] values = null;
		//自定义list
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");

		values = new String[list.size()][];

		//填充数据
		for(int i=0;i<values.length;i++){
			values[i] = new String[title.length];
			values[i][0] = list.get(i);
			values[i][1] = list.get(i);
			values[i][2] = list.get(i);
			values[i][3] = list.get(i);
			values[i][5] = list.get(i);
			values[i][4] = list.get(i);
			values[i][6] = list.get(i);
		}

		//导出为xls格式
		try (HSSFWorkbook workbook = HSSFWorkbookUtil.getHssFWorkbook(sheetName, title, values)) {
			response.setContentType("application/msexcel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename="+processFileName(request,fileName));
			//设置缓冲区
			response.setBufferSize(1024);
			OutputStream os = null;
			try {
				os = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			workbook.write(os);
			os.flush();
			os.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解决设置名称时的乱码导致无后缀名的情况
	 * @param request
	 * @param fileName
	 * @return
	 */
	public static String processFileName(HttpServletRequest request, String fileName) {
		String codedFileName = "";
		try {
			String agent = request.getHeader("USER-AGENT");
			// ie
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
					&& -1 != agent.indexOf("Trident")) {
				String name = java.net.URLEncoder.encode(fileName, "UTF8");
				codedFileName = name;
			// 火狐,chrome等
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				codedFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedFileName;
	}
}
