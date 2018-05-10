package com.guoys.platform.persistence.dialect;


/**
 * MySql方言
 * @author yangbo
 * @version 1.0 2014-7-28
 * @since 1.6
 */
public class MySQL5Dialect extends DefaultDialect implements Dialect {

	@Override
	public String getDBType() {
		return "mysql";
	}


}
