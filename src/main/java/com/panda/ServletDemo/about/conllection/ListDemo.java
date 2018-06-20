package com.panda.ServletDemo.about.conllection;

import java.util.*;

/**
 * list集合
 * @author pcongda
 *
 */
public class ListDemo {
	
	public static void main(String[] args) {
		List<Integer> a = new ArrayList<>();
		List<Integer> b = new ArrayList<>(a);
		b.add(1);
		System.out.println(b.size());
		Iterator<Integer> it = b.iterator();
		while (it.hasNext()) {
			int num = (Integer) it.next();
			System.out.println(num);
		}
		
		//Runnable
 	}
	
	//匿名内部类
	public void test1(){
		Comparator<Integer> com = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};
		TreeSet<Integer> set = new TreeSet<>(com);
	}
	
	//lambda表达式
	public void test2(){
		Comparator<Integer> com = (x,y) -> Integer.compare(x, y);
		TreeSet<Integer> set = new TreeSet<>(com);

	}
}
