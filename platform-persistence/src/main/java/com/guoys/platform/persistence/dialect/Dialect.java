package com.guoys.platform.persistence.dialect;


import com.guoys.platform.persistence.extension.SPI;

/**
 * mybatis方言接口
 * @version 1.0 2013-12-12
 * @since 1.6
 */

@SPI
public interface Dialect {


	/**
	 * 取得数据库类型
	 * @return 数据库类型
	 */
	public String getDBType();
	/**
	 * 获取分页sql
	 * @param sql 原始查询sql
	 * @param offset 开始记录索引（从0开始计数）
	 * @param limit 每页记录大小
	 * @return 数据库相关的分页sql
	 */
	public String getPaginationSql(String sql, int offset, int limit);

	/**
	 * 取得日期类型字段作为查询条件的表达式
	 * @param columnName 字段名
	 * @param format 日期格式
	 * @return 表达式
	 */
	public String getDateColumnCondition(String columnName, String format);

	
	/**
	 * 字符串截取
	 * @param field 字段名也可以是带单引号字符串
	 * @param pos 起始位置
	 * @return 转换后串
	 */
	public String substr(String field, String pos);
	
	/**
	 * 字符串截取
	 * @param field 字段名也可以是带单引号字符串
	 * @param pos 起始位置
	 * @param len 截取长度
	 * @return 转换后串
	 */
	public String substr(String field, String pos, String len);
	
	/**
	 * 字符串截取
	 * @param field 字段名也可以是带单引号字符串
	 * @return 转换后串
	 */
	public String length(String field);
	
	/**目标字符串在原字符串中的位置
	 * @param srcStr 源字符串
	 * @param tarStr 目标字符串
	 * @return
	 */
	public String instr(String srcStr, String tarStr);
}