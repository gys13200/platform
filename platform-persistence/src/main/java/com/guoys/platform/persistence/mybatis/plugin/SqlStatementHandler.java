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
 * Created on 2017年10月1日
 *******************************************************************************/


package com.guoys.platform.persistence.mybatis.plugin;


import com.guoys.platform.persistence.extension.SPI;
import com.guoys.platform.persistence.mybatis.plugin.SqlStatement.CommandType;

/**
 * SQL语句处理器
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
@SPI
public interface SqlStatementHandler {
	
	
	/**
	 * 对指定的表进行SQL处理
	 * @return
	 */
	public String[] includeTables();
	
	/**
	 * 如果指定的表在includeTables中，则排除
	 * @return
	 */
	public String[] excludeTables();
	
	/**
	 * 接受的操作类型
	 * @return
	 */
	public CommandType[] supportCommands();
	/**
	 * SQL语句处理
	 * @param sqlStatement
	 */
	public void handle(SqlStatement sqlStatement);
}

/*
 * 修改历史
 * $Log$ 
 */