package com.guoys.platform.persistence.dialect;


/**
 * 默认方言实现
 * @author yangbo
 * @version 1.0 2014-7-28
 * @since 1.6
 */
public abstract class DefaultDialect implements Dialect {

	@Override
	public String getPaginationSql(String sql, int offset, int limit) {

		return sql + " limit " + offset + "," + limit;

	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.jdbc.Dialect#getDateColumnCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public String getDateColumnCondition(String columnName,String format) {
		return columnName;
	}

	@Override
	public String substr(String field, String pos) {
		return "substring("+field+","+pos+")";
	}

	@Override
	public String substr(String field, String pos, String len) {
		return "substring("+field+","+pos+","+len+")";
	}

	@Override
	public String length(String field) {
		return "length("+field+")";
	}
	@Override
	public String instr(String srcStr, String tarStr) {
		return "instr("+srcStr+","+tarStr+")";
	}
}
