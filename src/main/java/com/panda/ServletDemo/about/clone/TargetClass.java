package com.panda.ServletDemo.about.clone;

/**
 * 浅拷贝：目标对象
 * @author pcongda
 *
 */
public class TargetClass implements Cloneable{
	
	private String name;
	private int age;
	private CloneClass clone;
	
	public TargetClass(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	
	public TargetClass(String name, int age,CloneClass clone) {
		super();
		this.name = name;
		this.age = age;
		this.clone = clone;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		TargetClass f = (TargetClass)super.clone();
		CloneClass h = f.getClone();
		h =(CloneClass)h.clone();
		return f;
	}

	@Override
	public String toString() {
		return "TargetClass [name=" + name + ", age=" + age + ", clone=" + clone + "]";
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


	public void setAge(int age) {
		this.age = age;
	}


	public CloneClass getClone() {
		return clone;
	}


	public void setClone(CloneClass clone) {
		this.clone = clone;
	}
}
