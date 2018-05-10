package com.guoys.platform.persistence.dialect;


/**
 * sqlserver方言实现
 * @author yangbo
 * @version 1.0 2014-7-28
 * @since 1.6
 */
public class SqlServerDialect extends DefaultDialect implements Dialect {

    @Override

    public String getPaginationSql(String sql, int pageNo, int pageSize) {

       return "select top " + pageSize + " from (" + sql

              + ") t where t.id not in (select top " + (pageNo-1)*pageSize + " t1.id from ("

              + sql + ") t1)";

    }

	@Override
	public String length(String field) {
		return "len("+field+")";
	}

	@Override
	public String getDBType() {
		return "sqlserver";
	}
    @Override
    public String instr(String srcStr, String tarStr) {
    	return "charindex("+tarStr+","+srcStr+")";
    }
    
}