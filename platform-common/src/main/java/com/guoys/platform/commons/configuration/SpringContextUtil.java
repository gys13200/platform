/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Bosssoft Co, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年4月26日
 *******************************************************************************/


package com.guoys.platform.commons.configuration;

import com.guoys.platform.commons.reflect.ClassUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class SpringContextUtil {
	
	private static Logger logger=LoggerFactory.getLogger(SpringContextUtil.class);
	
	private static final String KEY_SPRING_APPLICATION_CONTEXT_AWARE_CLASS="spring.applicationcontextaware.class";
	
	private static Class getSpringApplicationContextAwareClass()throws Exception{
		String className= PropertiesUtil.getProperty(KEY_SPRING_APPLICATION_CONTEXT_AWARE_CLASS, "com.bosssoft.platform.runtime.spring.RuntimeApplicationContext");
		return ClassUtils.forName(className, Thread.currentThread().getContextClassLoader());
	}
	/**
	 * 获取Spring Bean
	 * @param beanName bean名称
	 * @return
	 */
	public static <E> E getBean(String beanName){
		try{
			Class applicationContextAwareClass=getSpringApplicationContextAwareClass();
			Object result=MethodUtils.invokeStaticMethod(applicationContextAwareClass,"getBean", new Object[]{beanName});
			return (E)result;
		}catch(Exception e){
			logger.error("Error get spring applicationContext bean {}",beanName,e);
			throw new RuntimeException(e);
		}
	}
	
	public static <E> E getBeanByType(Class<? extends E> type) {
		try{
			Class applicationContextAwareClass=getSpringApplicationContextAwareClass();
			Object result=MethodUtils.invokeStaticMethod(applicationContextAwareClass,"getBeanByType", new Object[]{type});
			return (E)result;
		}catch(Exception e){
			logger.error("Error get spring applicationContext bean of type {}",type.getClass(),e);
			throw new RuntimeException(e);
		}
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> type){
		try{
			Class applicationContextAwareClass=getSpringApplicationContextAwareClass();
			Object result=MethodUtils.invokeStaticMethod(applicationContextAwareClass,"getBeansOfType", new Object[]{type});
			return (Map<String, T>)result;
		}catch(Exception e){
			logger.error("Error get spring applicationContext bean of type {}",type.getClass(),e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据注解获取Bean
	 * @param type
	 * @return
	 */
	public static Map<String,Object> getBeansWithAnnotation(Class type){
		try{
			Class applicationContextAwareClass=getSpringApplicationContextAwareClass();
			Object result=MethodUtils.invokeStaticMethod(applicationContextAwareClass,"getBeansWithAnnotation", new Object[]{type});
			return (Map<String, Object>)result;
		}catch(Exception e){
			logger.error("Error get spring applicationContext bean with annotation {}",type.getClass(),e);
			throw new RuntimeException(e);
		}
	}
}

/*
 * 修改历史
 * $Log$ 
 */