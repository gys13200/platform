package com.guoys.platform.persistence.dialect;


/**
 * H2数据库方言
 * @version 1.0 2014-7-28
 * @since 1.6
 */
public class H2Dialect extends DefaultDialect implements Dialect {

	@Override
	public String getDBType() {
		return "h2";
	}


}
