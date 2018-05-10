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
 * Created on 2017年5月23日
 *******************************************************************************/


package com.guoys.platform.persistence.helper;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class ColumnValue {

	private String name;
	
	private String value;
	
	public ColumnValue(String name,String value){
		this.name=name;
		this.value=value;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	
	
}

/*
 * 修改历史
 * $Log$ 
 */