package com.panda.ServletDemo.about.conllection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
 	}

}
