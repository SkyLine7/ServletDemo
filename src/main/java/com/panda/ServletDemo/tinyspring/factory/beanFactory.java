package com.panda.ServletDemo.tinyspring.factory;

import com.panda.ServletDemo.tinyspring.BeanDefinition;

/**
 * bean的IoC 容器工厂
 * @author pcongda
 */
public interface beanFactory {

	//根据名称获取一个bean
	public BeanDefinition getBean(String name);

	//注册一个bean,就是将bean保存到Map中去
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
