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
 * Created on 2017年10月10日
 *******************************************************************************/


package com.guoys.platform.persistence.mybatis.plugin;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class KeyValuePair implements java.io.Serializable{

	private String key;
	
	private Object value;

	private String javaType;
	
	public KeyValuePair(){
		
	}
	
	public KeyValuePair(String key,Object value,String javaType){
		this.key=key;
		this.value=value;
		this.javaType=javaType;
	}
	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key The key to set.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return Returns the javaType.
	 */
	public String getJavaType() {
		return javaType;
	}

	/**
	 * @param javaType The javaType to set.
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	
}

/*
 * 修改历史
 * $Log$ 
 */