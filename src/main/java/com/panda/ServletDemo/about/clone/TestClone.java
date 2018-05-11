package com.panda.ServletDemo.about.clone;

/**
 * clone() 方法测试
 * @author pcongda
 *
 */
public class TestClone {
	public static void main(String[] args) throws CloneNotSupportedException {
		fun1();
//		fun2();
	}
	
	public static void fun1() throws CloneNotSupportedException {
		CloneClass p = new CloneClass(new String("padda"), 20);
		TargetClass t1 = new TargetClass("pada", 18,p);
		TargetClass t2 = (TargetClass)t1.clone();
		t2.getClone().setAge(21);
		t2.setAge(99);
		t2.setName("ttt");
		System.out.println(t1==t2);
		System.out.println(t1.equals(t2));
		System.out.println(t1.toString());
		System.out.println(t2.toString());
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
	}
	
	public static void fun2() throws CloneNotSupportedException {
		TargetClass t1 = new TargetClass("pada", 18);
		TargetClass t2 = (TargetClass) t1.clone();
		t2.setName("ttt");
		t2.setAge(1);
		System.out.println(t1 == t2);
		System.out.println(t1.toString());
		System.out.println(t2.toString());
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
	}
}
