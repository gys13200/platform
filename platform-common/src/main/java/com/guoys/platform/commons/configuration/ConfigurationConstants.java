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
 * Created on 2016年10月28日
 *******************************************************************************/


package com.guoys.platform.commons.configuration;

/**
 * 配置常量定义
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public class ConfigurationConstants {

	/**
	 * JVM 参数指定平台目录
	 */
	public static final String BOSSSOFT_HOME="BOSSSOFT_HOME";
	/**
	 * 数据库地址
	 */
    public static final String JDBC_URL = "jdbc.url";
    
    /**
     * 用户名
     */
    public static final String JDBC_USERNAME = "jdbc.username";
    
    /**
     * 密码
     */
    public static final String JDBC_PASSWORD = "jdbc.password";
    
    /**
     * JDBC最大连接数
     */
    public static final String JDBC_MAX_ACTIVE = "jdbc.maxActive";
    
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。 如果validationQuery为null，testOnBorrow、testOnReturn、 testWhileIdle都不会其作用。
     */
    public static final String JDBC_VALIDATION_QUERY="jdbc.validationQuery";
    
    /**
     * JDBC配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    public static final String JDBC_TIME_BETWEEN_EVICTION_RUNS_MILLIS="jdbc.timeBetweenEvictionRunsMillis";
    
    /**
     * JDBC 申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis， 执行jdbc.validationQuery检测连接是否有效
     */
    public static final String JDBC_TEST_WHILE_IDLE="jdbc.testWhileIdle";
    
    /**
     * JDBC申请连接时执行validationQuery检测连接是否有效
     */
    public static final String JDBC_TEST_ON_BORROW="jdbc.testOnBorrow";
  
    /**
     * JDBC驱动类名称
     */
    public static final String JDBC_DRIVER_CLASSNAME="jdbc.driverClassName";
    
    /**
     * JNDI数据源是否开启
     */
    public static final String JNDI_DATASOURCE_ENABLE="jndi.datasource.enable";
    
    /**
     * JNDI数据源链接
     */
    public static final String JNDI_DATASOURCE_URL="jndi.datasource.url";
    
    
    /**
     * 实体类型别名基本包,以逗号分隔
     */
    public static final String MYBATIS_TYPE_ALIASES_BASE_PACKAGE="mybatis.type_aliases_base_packages";
    
    /**
     * 映射文件基本包名,以逗号分隔
     */
    public static final String MYBATIS_MAPPER_SCANNER_BASE_PACKAGE="mybatis.mapper_scanner_base_packages";
    
    /**
     * 是否启用SQL拦截
     */
    public static final String MYBATIS_SQL_INTERCEPTOR_ENABLE="mybatis.sql.interceptor.enable";
    
    /**
     * SQL拦截是否获取变更前结果集
     */
    public static final String MYBATIS_SQL_INTERCEPTOR_FETCH_BEFORERESULTSET="mybatis.sql.interceptor.fetch.beforeresultset";
    
    /**
     * SQL拦截不包含表
     */
    public static final String MYBATIS_SQL_INTERCEPTOR_EXCLUDE_TABLES="mybatis.sql.interceptor.exclude.tables";
    
    /**
     * SQL拦截包含表
     */
    public static final String MYBATIS_SQL_INTERCEPTOR_INCLUDE_TABLES="mybatis.sql.interceptor.include.tables";
    
    /**
     * DUBBO启用参数
     */
    public static final String DUBBO_ENABLE="dubbo.enable";
    
    /**
     * EHcache启用参数
     */
    public static final String EHCACHE_ENABLE="ehcache.enable";
    
    /**
     * Spring 文件解析器
     */
    public static final String SPRING_MULTIPART_RESOLVER_ENABLE="spring.multipartresolver.enable";
    /**
     * 限制文件大小
     */
    public static final String SPRING_MULTIPART_RESOLVER_MAXSIZE="spring.multipartresolver.maxsize";
    
	
	public static final String UPLOAD_MAX_FILE_SIZE="upload.max.filesize";
    
    /**
     * 是否启用freemarker视图
     */
    public static final String VIEWS_FREEMARKER_ENABLE="views.freemarker.enable";
    /**
     * freemarker模板加载路径，默认在/WEB-INF/templates下
     */
    public static final String VIEWS_FREEMARKER_TEMPLATE_LOADER_PATH="views.freemarker.templateLoaderPath";
    
    /**
     * 是否启用freemarker 缓存,默认true
     */
    public static final String VIEWS_FREEMARKER_CACHE="views.freemarker.cache";
    
    /**
     * 多数据源名称列表,多个数据源名称定义以逗号分隔
     */
    public static final String MULTIDATASOURCE_NAMES="multidatasource.names";
    
    /**
     * 是否启用/禁用所有数据源开关
     */
    public static final String DATASOURCE_ALL_ENABLE="datasource.all.enable";
    
    /**
     * 是否启用多数据源
     */
    public static final String MULTIDATASOURCE_ENABLE="multidatasource.enable";
    
    /**
     * XA数据源类名
     */
    public static final String MULTIDATASOURCE_XA_DATASOURCE_CLASSNAME="multidatasource.xa.dataSourceClassName";
    
    /**
     * XA连接池大小
     */
    public static final String MULTIDATASOURCE_XA_POOLSIZE="multidatasource.xa.poolSize";
    
    /**
     * XA最小连接池
     */
    public static final String MULTIDATASOURCE_XA_MIN_POOL_SIZE="multidatasource.xa.minPoolSize";
    
    /**
     * XA最大连接池
     */
    public static final String MULTIDATASOURCE_XA_MAX_POOL_SIZE="multidatasource.xa.maxPoolSize";
    
    
    /**
     * UI页面加载缓存开关
     */
    public static final String UI_PAGECACHE_ENABLE="ui.pagecache.enable";
    
	/**
	 * 是否启用XML模板引擎解析,默认true
	 */
    public static final String UI_XMLTEMPLATE_FREEMARKER_ENABLE="ui.xmltemplate.freemarker.enable";
    
	public static final String SWAGGER_API_TITLE = "swagger.api.title";
	
	public static final String SWAGGER_API_DESCRIPTION = "swagger.api.description";
	
	public static final String SWAGGER_API_VERSION = "swagger.api.version";
	
	public static final String SWAGGER_API_COPYRIGHT = "swagger.api.copyright";

	/**
	 * 应用ID
	 */
	public static final String APPLICATION_ID="application.id";
	
	/**
	 * 是否启用假删除
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_ENABLE="persistence.dummy.delete.enable";
	
	/**
	 * 假删除指定表(多个表逗号分隔),全部表可以all
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_TABLES="persistence.dummy.delete.tables";
	/**
	 * 假删除字段名
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_COLUMNNAME="persistence.dummy.delete.columnName";
	/**
	 * 假删除字段值
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_COLUMNVALUE="persistence.dummy.delete.columnValue";
	
	/**
	 * 删除时更新时间字段
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_UPDATETIMECOLUMN="persistence.dummy.delete.updateTimeColumn";
	/**
	 * 更新时间格式(long/date)
	 */
	public static final String PERSISTENCE_DUMMY_DELETE_UPDATETIMEFORMAT="persistence.dummy.delete.updateTimeFormat";
	
	/**
	 * 事务拦截排除指定的类，格式如:com.aaa.bbb..imptpatient..*ServiceImpl.*(..)
	 */
	public static final String SPRING_TRANSACTION_EXECUTION_EXCLUDES="spring.transaction.execution.excludes";
	
	/**
	 * json字符串序列化输出是否将null转换为""字符串
	 */
	public static final String SPRING_JACKSON_SERIALIZER_STRING_NULLTOEMPTY="spring.jackson.serializer.string.null2empty";
	
	/**
	 * Shiro登录过滤地址
	 */
	public static final String SHIRO_LOGIN_EXCLUDE_URLS="shiro.login.exclude.urls";
	
	/**
	 * Shiro权限过滤地址
	 */
	public static final String SHIRO_PERMISSION_EXCLUDE_URLS="shiro.permission.exclude.urls";

}

/*
 * 修改历史
 * $Log$ 
 */