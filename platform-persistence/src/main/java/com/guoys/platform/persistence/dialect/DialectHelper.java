package com.guoys.platform.persistence.dialect;

import com.guoys.platform.persistence.extension.ExtensionLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;



/**
 * 方言助手类
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public final class DialectHelper {

	/**
	 * 根据数据源生成
	 * @param dbType 数据库类型
	 * @return 方言对象
	 */
	public static Dialect getDialect(String dbType){
		Set<String> dialects= ExtensionLoader.getExtensionLoader(Dialect.class).getSupportedExtensions();
		for(String name:dialects){
			Dialect dialect=ExtensionLoader.getExtensionLoader(Dialect.class).getExtension(name);
			if(dbType.equalsIgnoreCase(dialect.getDBType())){
				return dialect;
			}
		}
		
		throw new IllegalArgumentException("Unknown Database Type of " + dbType);
		
	}
	
	/**
	 * 根据数据源获取数据库jdbcurl
	 * @param dataSource
	 * @return
	 */
	public static String getJdbcUrlFromDataSource(DataSource dataSource) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
			}
			String url= connection.getMetaData().getURL();
			return url;
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    public static Dialect fromJdbcUrl(String jdbcUrl) {
		Set<String> dialects=ExtensionLoader.getExtensionLoader(Dialect.class).getSupportedExtensions();
		for(String name:dialects){
			Dialect dialect=ExtensionLoader.getExtensionLoader(Dialect.class).getExtension(name);
			if(jdbcUrl.indexOf(":" + dialect.getDBType() + ":") != -1 ){
				return dialect;
			}
		}
		
		throw new IllegalArgumentException("Unknown Database Type of " + jdbcUrl);
    }
	
}
