package com.guoys.platform.persistence.service;

import com.guoys.platform.commons.lang.expression.Expression;
import com.guoys.platform.persistence.dialect.Dialect;

import java.util.Map;




/**
 * 条件转换结果
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public interface QueryTranslator {

	
	/**
	 * 获取当前数据库方言
	 * @return
	 */
	public Dialect getDialect();
	
	/**
	 * 取得表达式的预编译条件
	 * @param expression 表达式
	 * @param froms 实体列表支持别名
	 * @return 预编译条件
	 */
	public PreQuery getPreCondition(Expression expression, String... froms);
	
	/**
	 * 取得表达式对应的条件串
	 * @param expression 表达式
	 * @return 条件串
	 */
	public String getCondition(Expression expression, String... froms);
	
	/**
	 * 取得mybatis表达式对应的
	 * @param expression 表达式对象
	 * @param parameters 参数map
	 * @param fromes 表名数组
	 * @param paramKey 参数关键字
	 * @return 参数map
	 */
	public Map<String, Object> getMybatisCondition(Expression expression, Map<String, Object> parameters, String paramKey,
                                                   String... fromes);
}
