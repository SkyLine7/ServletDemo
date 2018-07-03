package com.panda.ServletDemo.util;

import com.panda.ServletDemo.mvcframework.util.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.text.ParseException;

/**
 * 导出execl工具类:Excel2003
 * HSSFWorkbook工具类(导出xls格式文件)
 * 最多支持65535行，适用于一般数据量少导出
 * @author pcongda
 */
public class HSSFWorkbookUtil {

	/**
	 * 生成HSSFworkbook对象
	 * @param sheetName
	 * @param title
	 * @param values
	 * @return
	 * @throws ParseException
	 */
	public static HSSFWorkbook getHssFWorkbook(String sheetName,String[] title,String[][] values) throws ParseException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个sheet
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 添加到表头
		HSSFRow row = sheet.createRow(0);
		// 设置单元格样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);

		//创建单元格对象
		HSSFCell cell = null;

		// 创建标题
		for(int i=0;i<title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
			if(i==title.length-1){
				sheet.setDefaultColumnWidth(20);
			}
		}

		// 创建内容
		if(values != null && values.length>0){
			for(int j=0;j<values.length;j++){
				row = sheet.createRow(j+1);
				for(int k=0;k<values[j].length;k++){
					cell = row.createCell(k);
					if(k == 0){
						cell.setCellValue(Long.parseLong(values[j][k]));
					}else{
						if(StringUtil.numType(values[j][k].getClass())){
							cell.setCellValue(Double.parseDouble(values[j][k]));
						}else{
							cell.setCellValue(values[j][k]);
						}
					}
					cell.setCellStyle(style);
				}
			}
		}
		return workbook;
	}
}
