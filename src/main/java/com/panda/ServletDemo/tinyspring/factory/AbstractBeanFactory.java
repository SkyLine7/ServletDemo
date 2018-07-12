package com.panda.ServletDemo.tinyspring.factory;

import com.panda.ServletDemo.tinyspring.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * beanFactory 接口的 抽象类
 */
public abstract class AbstractBeanFactory implements beanFactory {

	// 保存beanDefinition 的 IoC 容器，key为 bean 名称，value 为bean 实例
	private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	@Override
	public  BeanDefinition getBean(String name) {
		return (BeanDefinition)beanDefinitionMap.get(name).getBean();
	}

	@Override
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
		Object bean = this.doCreateBean(beanDefinition);
		beanDefinition.setBean(bean);
		beanDefinitionMap.put(name,beanDefinition);
	}

	/**
	 * 初始化bean,即创建一个新的bean 实例
	 * @param bean
	 * @return
	 */
	protected abstract Object doCreateBean(BeanDefinition bean);
}
