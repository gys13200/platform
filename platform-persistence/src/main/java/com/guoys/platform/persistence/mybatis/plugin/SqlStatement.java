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

import java.util.List;

/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class SqlStatement implements java.io.Serializable{
	
	public enum CommandType {
		INSERT,
		UPDATE,
		DELETE,
		SELECT
	}
	/**
	 * 表名
	 */
	private String table;
	
	/**
	 * 操作类型
	 */
	private CommandType commandType;
	
	/**
	 * SQL语句
	 */
	private String sql;
	
	/**
	 * 参数
	 */
	private List<KeyValuePair> parameters;
	
	/**
	 * 执行方法名
	 */
	private String sqlId;
	
	/**
	 * 耗时
	 */
	private long cost;
	
	/**
	 * 创建时间
	 */
	private long createTime;


	/**
	 * @return Returns the sql.
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql The sql to set.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return Returns the parameters.
	 */
	public List<KeyValuePair> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(List<KeyValuePair> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return Returns the commandType.
	 */
	public CommandType getCommandType() {
		return commandType;
	}

	/**
	 * @param commandType The commandType to set.
	 */
	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}


	/**
	 * @return Returns the cost.
	 */
	public long getCost() {
		return cost;
	}

	/**
	 * @param cost The cost to set.
	 */
	public void setCost(long cost) {
		this.cost = cost;
	}

	/**
	 * @return Returns the createTime.
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime The createTime to set.
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return Returns the sqlId.
	 */
	public String getSqlId() {
		return sqlId;
	}

	/**
	 * @param sqlId The sqlId to set.
	 */
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table The table to set.
	 */
	public void setTable(String table) {
		this.table = table;
	}
	
	
	

}

/*
 * 修改历史
 * $Log$ 
 */