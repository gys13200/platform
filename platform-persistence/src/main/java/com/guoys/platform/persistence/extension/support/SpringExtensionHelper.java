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
 * Created on 2016年9月8日
 *******************************************************************************/


package com.guoys.platform.persistence.extension.support;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import com.guoys.platform.commons.reflect.AnnotationUtils;
import com.guoys.platform.commons.reflect.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Spring 扩展辅助类
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class SpringExtensionHelper {
	
	private static Logger logger=LoggerFactory.getLogger(SpringExtensionHelper.class);
	
	private static SpringExtensionLoader springExtensionLoader=null;
	
	private static final String SPRING_EXTENSION_LOADER_CLASS="com.bosssoft.platform.runtime.spring.SpringContextExtensionLoader";

	/**
	 * 初始化Spring注解属性字段
	 * @param instance
	 */
	public static void initAutowireFields(Object instance){
		if(springExtensionLoader==null){
			try{
				springExtensionLoader=(SpringExtensionLoader) ClassUtils.newInstance(SPRING_EXTENSION_LOADER_CLASS, null);
			}catch(Throwable t){
				logger.warn("Can't load {}",SPRING_EXTENSION_LOADER_CLASS,t);
			}
			
//			ThreadClassLoaderProxy proxy=ThreadClassLoaderFactory.getThreadClassLoaderProxy(Thread.currentThread().getContextClassLoader());
//			AnnotationRepository respository=new AnnotationRepository(proxy.getCurrentThreadClassLoader());
//			respository.scanAnnotation("com.bosssoft.*");
//			Class[] classes=respository.getAnnotatedClasses(Activate.class);
//			for(Class clazz:classes){
//				if(SpringExtensionLoader.class.isAssignableFrom(clazz)){
//					springExtensionLoader=(SpringExtensionLoader)ClassUtils.newInstance(clazz, null);
//					break;
//				}
//			}
		}
		
		if(springExtensionLoader!=null){
			Field[] fields= AnnotationUtils.getAnnotatedFields(instance.getClass(), Resource.class);
			for(Field field:fields){
				Object value=springExtensionLoader.lookup(field.getType());
				if(value!=null){
					field.setAccessible(true);
					try{
						field.set(instance, value);
					}catch(Exception e){
						logger.error("Set spring bean to field "+field.getName()+" error!",e);
					}
				}
			}
		}
	}
	
}

/*
 * 修改历史
 * $Log$ 
 */