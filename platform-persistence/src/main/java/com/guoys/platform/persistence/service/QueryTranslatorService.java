package com.guoys.platform.persistence.service;

import com.guoys.platform.commons.lang.expression.Expression;

import java.util.List;
import java.util.Map;



/**
 * QL翻译器工厂方法接口
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public interface QueryTranslatorService extends QueryTranslator{

	/**
	 * 取得QL(JPQL或HQL)条件转换器
	 * @param expression 表达式
	 * @return QL条件转换器
	 */
	QueryTranslator getTranslator(Expression expression);
	
	/**
	 * 取得一组表达式的预编译条件
	 * @param expressions 表达式
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 预编译条件
	 */
	public PreQuery getPreCondition(List<Expression> expressions, String... fromes);

	
	/**
	 * 取得一组表达式的查询条件
	 * @param expressions 表达式
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 查询条件
	 */
	public String getCondition(List<Expression> expressions, String... fromes);
	
	/**
	 * 取得一组表达式的查询条件
	 * @param expressions 表达式
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 查询条件
	 */
	public Map<String,Object> getMybatisCondition(List<Expression> expressions, String paramKey, String... fromes);
}
