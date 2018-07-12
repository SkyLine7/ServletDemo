package com.panda.ServletDemo.tinyspring.factory;

import com.panda.ServletDemo.tinyspring.BeanDefinition;

public class AutowriedCapableBeanFactory extends AbstractBeanFactory{
	/**
	 * 初始化bean，即创建一个新的bean 实例
	 *
	 * @param bean
	 * @return
	 */
	@Override
	protected Object doCreateBean(BeanDefinition bean) {
		Object beanInstance = null;
		try {
			beanInstance = bean.getBeanClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanInstance;
	}
}
