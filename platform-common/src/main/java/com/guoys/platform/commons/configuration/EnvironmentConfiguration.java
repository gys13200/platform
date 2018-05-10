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
 * Created on 2017年9月5日
 *******************************************************************************/


package com.guoys.platform.commons.configuration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class EnvironmentConfiguration {

	private static final Logger logger=LoggerFactory.getLogger(EnvironmentConfiguration.class);
	
	/**
	 * 运行环境主目录
	 */
	public static File BOSSSOFT_HOME=null;
	/**
	 * 当前应用配置目录
	 */
	public static File CONFIGURATION_HOME=null;
	
	
	/**
	 * 加载log4j2配置文件并启动(通过反射调用避免直接依赖log4j)
	 * @param log4j2ConfigFile
	 */
	public static void loadLog4j2Configuration(File log4j2ConfigFile){
		InputStream is=null;
		try{
			is=new FileInputStream(log4j2ConfigFile);
			Class configurationSourceClass=ClassUtils.getClass("org.apache.logging.log4j.core.config.ConfigurationSource");
			Object configurationSource=configurationSourceClass.getConstructor(InputStream.class).newInstance(is);
			
			Class configuratorClass=ClassUtils.getClass("org.apache.logging.log4j.core.config.Configurator");
			Object loggerContext=MethodUtils.invokeStaticMethod(configuratorClass, "initialize", new Object[]{Thread.currentThread().getContextClassLoader(),configurationSource});
			MethodUtils.invokeMethod(loggerContext, "stop", null);
			MethodUtils.invokeMethod(loggerContext, "start", null);
			
		}catch(Exception e){
			logger.error("Load {} error!",log4j2ConfigFile.getAbsoluteFile(),e);
		}finally{
			IOUtils.closeQuietly(is);
		}
	}
}

/*
 * 修改历史
 * $Log$ 
 */