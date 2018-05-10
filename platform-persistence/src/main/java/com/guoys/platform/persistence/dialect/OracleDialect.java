package com.guoys.platform.persistence.dialect;


public class OracleDialect extends DefaultDialect implements Dialect {

	@Override
	public String getPaginationSql(String sql, int offset, int limit) {

		return "select * from (select rownum rn, t.* from (" + sql

		+ ") t where rownum <= " + (offset+limit)

		+ ") t1 where t1.rn > " + offset;

	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.jdbc.Dialect#getDateColumnCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public String getDateColumnCondition(String columnName,String format) {
		return "to_char("+columnName+",'"+format+"')";
	}

	@Override
	public String substr(String field, String pos) {
		return "substr("+field+","+pos+")";
	}

	@Override
	public String substr(String field, String pos, String len) {
		return "substr("+field+","+pos+","+len+")";
	}

	@Override
	public String getDBType() {
		return "oracle";
	}

}