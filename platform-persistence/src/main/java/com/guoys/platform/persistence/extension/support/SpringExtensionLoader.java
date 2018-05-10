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
 * Created on 2016年9月6日
 *******************************************************************************/


package com.guoys.platform.persistence.extension.support;


import com.guoys.platform.persistence.extension.SPI;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
@SPI
public interface SpringExtensionLoader {
	
	/**
	 * 获取Spring Bean
	 * @param clazz
	 * @return
	 */
	public Object lookup(Class<?> clazz);

}

/*
 * 修改历史
 * $Log$ 
 */