package com.panda.ServletDemo.mvcframework;

import java.util.List;
import java.util.Map;

/**
 * 自定义响应头工具类
 * @author pcongda
 *
 */
public interface MultiValueMap<K,V> extends Map<K,List<V>>{

	V getFirst(K key);

	//添加单个值到给定键的当前值列表中
	void add(K key,V value);

	//设置单个值到指定的键中
	void set(K key, V value);

	void setAll(Map<K, V> values);

	Map<K, V> toSingleValueMap();
}
