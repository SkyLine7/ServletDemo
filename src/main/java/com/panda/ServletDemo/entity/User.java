package com.panda.ServletDemo.entity;

import java.io.Serializable;
/**
 * 用户实体类
 * @author pcongda
 *
 */
public class User implements Serializable{
		
	private static final long serialVersionUID = 1934031416059808151L;

	private String name;
	
	private Integer age;
	
	private String hegiht;
	
	private String password;
	
	
	public User(){
		super();
	}
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public User(String name, Integer age, String hegiht) {
		super();
		this.name = name;
		this.age = age;
		this.hegiht = hegiht;
	}
	
	public User(String name, Integer age, String hegiht,String password) {
		super();
		this.name = name;
		this.age = age;
		this.hegiht = hegiht;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHegiht() {
		return hegiht;
	}

	public void setHegiht(String hegiht) {
		this.hegiht = hegiht;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
