package com.panda.ServletDemo.about.clone;
/**
 * 浅拷贝: 原对象
 * @author pcongda
 *
 */
/**
 * @author pcongda
 *
 */
public class CloneClass implements Cloneable{
	
	private String name;
	private int age;
	
	public CloneClass(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CloneClass [name=" + name + ", age=" + age + "]";
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
