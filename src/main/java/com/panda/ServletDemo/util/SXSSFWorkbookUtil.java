package com.panda.ServletDemo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panda.ServletDemo.mvcdemo.mvc.controller.outputXLS;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 导出Excel：(xlsx格式)
 * Excel2007 以后
 * 适用于数据量大，支持单个sheet表104万条数据
 */
public class SXSSFWorkbookUtil {
	public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式

	public static int DEFAULT_COLOUMN_WIDTH = 17; //默认列宽

	/**
	 * 创建SXSSFWorkbook对象
	 * @param sheetName
	 * @param title
	 * @param values
	 * @return
	 */
//	public SXSSFWorkbook getSXSSFWorkbookObj(String sheetName, String[] title, List<CellMap> values) {
//		int rowNo = 0;
//		int pageRowNo = 0;
//		//内存中保留 1000条数据，以免内存溢出，其余写入硬盘
//		SXSSFWorkbook wb = new SXSSFWorkbook(1000);
//		// 工作表对象
//		Sheet sheet = null;
//		// 行对象
//		Row row = null;
//		// 列对象
//		Cell cell = null;
//		// 设置表单样式风格
//		CellStyle style = wb.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER);
//		if (values != null && values.size() > 0) {
//			for (int j = 0; j < values.size(); j++) {
//				// 超过一百万行后换一个工作簿,单个sheet最多1048576行
//				if (rowNo % 1000000 == 0) {
//					sheet = wb.createSheet("这是我的第" + (rowNo / 1000000 + 1) + "个工作簿");
//					sheet = wb.getSheetAt(rowNo / 1000000);
//					pageRowNo = 0;
//				}
//				rowNo++;
//				// 创建标题
//				if(pageRowNo == 0){
//					row = sheet.createRow(0);
//					for(int i=0;i<title.length;i++){
//						cell = row.createCell(i);
//						cell.setCellValue(title[i]);
//						cell.setCellStyle(style);
//						if(i==title.length-1){
//							sheet.setDefaultColumnWidth(20);
//						}
//					}
//				}else{
//				// 创建行
//				row = sheet.createRow(pageRowNo);
//				for(int k=0;k<values[j].length;k++){
//					// 创建列
//					cell = row.createCell(k);
//					if(k == 0){
//						// 对列进行赋值
//						cell.setCellValue(Long.parseLong(values[j][k]));
//					}else{
//						if(StringUtil.numType(values[j][k])){
//							cell.setCellValue(Double.parseDouble(values[j][k]));
//						}else{
//							cell.setCellValue(String.valueOf(values[j][k]));
//						}
//					}
//					cell.setCellStyle(style);
//				}
//			}
//			pageRowNo++;
//		}
//	}
//        return wb;
//	}


	/**
	 * 生成Excel 2007 OOXML (.xlsx)格式
	 * @param title 标题行
	 * @param headMap 属性-列头
	 * @param jsonArray 数据集
	 * @param datePattern 日期格式，传null值则默认 年月日
	 * @param colWidth 列宽 默认 至少17个字节
	 * @param out 输出流
	 */
	public static void exportExcelX(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out) {
		if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN;
		// 声明一个工作薄，内存中保留 1000条数据，以免内存溢出，其余写入硬盘
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
		workbook.setCompressTempFiles(true);  // 设置压缩

		//设置表头样式
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBold(true);
		titleStyle.setFont(titleFont);

		// 设置列头样式
		CellStyle headerStyle = workbook.createCellStyle();
		//单元格背景,不用设置，设置了变成了黑色看不到了
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);


		// 设置单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		//cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font cellFont = workbook.createFont();
		cellFont.setBold(true);
		cellStyle.setFont(cellFont);

		// 生成一个(带标题)表格
		SXSSFSheet sheet = (SXSSFSheet)workbook.createSheet("第一个sheet");
		//设置列宽
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;//至少字节数
		int[] arrColWidth = new int[headMap.size()];
		// 产生表格标题行,以及设置列宽
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next();
			properties[ii] = fieldName;
			headers[ii] = headMap.get(fieldName);
			int bytes = fieldName.getBytes().length;
			arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(ii,arrColWidth[ii] * 256);
			ii++;
		}
		// 遍历集合数据，产生数据行
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if(rowIndex == 65535 || rowIndex == 0){
				if (rowIndex != 0) sheet = (SXSSFSheet)workbook.createSheet("第二个sheet");//如果数据超过了，则在第二页显示

				SXSSFRow titleRow = (SXSSFRow)sheet.createRow(0);//表头 rowIndex=0
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

				SXSSFRow headerRow = (SXSSFRow)sheet.createRow(1); //列头 rowIndex =1
				for(int i=0;i<headers.length;i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = 2;//数据内容从 rowIndex=2开始
			}
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			SXSSFRow dataRow = (SXSSFRow)sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				SXSSFCell newCell = (SXSSFCell)dataRow.createCell(i);
				Object o =  jo.get(properties[i]);
				String cellValue = "";
				if(o==null) cellValue = "";
				else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
				else if(o instanceof Float || o instanceof Double)
					cellValue= new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
				else cellValue = o.toString();
				newCell.setCellValue(cellValue);
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		//写入输出流
		try {
			workbook.write(out);
			workbook.close();
			workbook.dispose();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 导出excel 以xlxs格式
	 * @param title 标题
	 * @param headMap 列名
	 * @param json 数据集
	 * @param response 响应
	 */
	public static void downloadExcelFile(String title, Map<String,String> headMap, JSONArray json, HttpServletRequest request, HttpServletResponse response){
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// 导出为xlsx格式
			SXSSFWorkbookUtil.exportExcelX(title,headMap,json,null,0,os);

			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			//设置response参数，打开下载页面
			response.reset();
			response.setContentType("application/msexcel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ outputXLS.processFileName(request,title));
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出Excel 97(.xls)格式 ，少量数据
	 * @param title 标题行
	 * @param headMap 属性-列名
	 * @param jsonArray 数据集
	 * @param datePattern 日期格式，null则用默认日期格式
	 * @param colWidth 列宽 默认 至少17个字节
	 * @param out 输出流
	 */
	public static void exportExcel(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out) {
		if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN;
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个添加xls 信息对象
		workbook.createInformationProperties();
		workbook.getDocumentSummaryInformation().setCompany("*****公司");
		SummaryInformation si = workbook.getSummaryInformation();
		si.setAuthor("panda");  //添加xls文件作者信息
		si.setApplicationName("导出程序"); // 添加xls文件创建程序信息
		si.setLastAuthor("最后保存者信息"); // 添加xls文件最后保存者信息
		si.setComments("panda is a programmer!"); // 添加xls文件作者信息
		si.setTitle("POI导出Excel:xls"); // 添加xls文件标题信息
		si.setSubject("POI导出Excel:xls");// 添加文件主题信息
		si.setCreateDateTime(new Date()); // 添加文件创建时间

		//设置表头样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBold(true);
		titleStyle.setFont(titleFont);

		// 设置列头样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		// 设置单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont cellFont = workbook.createFont();
		cellFont.setBold(true);
		cellStyle.setFont(cellFont);

		// 生成一个(带标题)的表格
		HSSFSheet sheet = workbook.createSheet();
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("panda");
		//设置列宽
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH : colWidth;//至少字节数
		int[] arrColWidth = new int[headMap.size()];
		// 产生表格标题行,以及设置列宽
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next();
			properties[ii] = fieldName;
			headers[ii] = fieldName;
			int bytes = fieldName.getBytes().length;
			arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(ii,arrColWidth[ii] * 256);
			ii++;
		}
		// 遍历集合数据，产生数据行
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if(rowIndex == 65535 || rowIndex == 0){
				if ( rowIndex != 0 ) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示
				HSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));
				HSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
				for(int i=0;i<headers.length;i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = 2;//数据内容从 rowIndex=2开始
			}
			JSONObject jo = (JSONObject)JSONObject.toJSON(obj);
			HSSFRow dataRow = sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				HSSFCell newCell = dataRow.createCell(i);
				Object o =  jo.get(properties[i]);
				String cellValue = "";
				if(o==null) cellValue = "";
				else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
				else cellValue = o.toString();
				newCell.setCellValue(cellValue);
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手动造数据:xlsx
	 */
	public static void createDatas(int count,String sheetName,Map<String,String> headMap,JSONArray json, HttpServletRequest request,HttpServletResponse response){
		System.out.println("开始导出xlsx....");
		long start = System.currentTimeMillis();
		//导出为xlsx格式
		downloadExcelFile(sheetName,headMap,json,request,response);
		System.out.println("共"+count+"条数据,执行"+(System.currentTimeMillis() - start)+"ms");
	}

	/**
	 * 手动造数据:xls
	 */
	public static void createData(int count,String sheetName,Map<String,String> headMap,JSONArray json, HttpServletRequest request,HttpServletResponse response){
		System.out.println("开始导出xlsx....");
		long start = System.currentTimeMillis();
		//导出为xls格式
		downloadExcelFiles(sheetName,headMap,json,request,response);
		System.out.println("共"+count+"条数据,执行"+(System.currentTimeMillis() - start)+"ms");
	}

	/**
	 * 导出excel 以xls格式
	 * @param title 标题
	 * @param headMap 列名
	 * @param json 数据集
	 * @param response 响应
	 */
	public static void downloadExcelFiles(String title, Map<String,String> headMap, JSONArray json, HttpServletRequest request, HttpServletResponse response){
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// 导出为xls格式
			SXSSFWorkbookUtil.exportExcel(title,headMap,json,null,0,os);

			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			//设置response参数，打开下载页面
			response.reset();
			response.setContentType("application/msexcel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ outputXLS.processFileName(request,title));
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}