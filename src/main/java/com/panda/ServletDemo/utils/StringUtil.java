/**
 * 
 */
package com.panda.ServletDemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author pcongda
 *
 */
public class StringUtil {
	
	 /**
     * 替换固定格式的字符串（支持正则）
     */
    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    /**
	 * 返回该字符串的首字母小写形式
	 * @param str
	 * @author pcongda
	 * @return
	 */
	public static String toLower(String str){
		char[] chars = str.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}
	
	/**
	 * Int类型转换为Integer
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object convert(Class<?> type,String value){
		if(Integer.class == type){
			return Integer.valueOf(value);
		}
		return value;
	}

	/**
	 * 判断类型是否是String
	 * @param type
	 * @return
	 */
	public static boolean numType(Class<?> type) {
		if(type == String.class){
			return true;
		}
		return false;
	}
}
