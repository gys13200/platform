package com.guoys.platform.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guoys.platform.commons.lang.expression.Expression;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;


import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.translator.ChainLogicExpressionTranslator;
import com.guoys.platform.persistence.service.translator.LogicExpressionTranslator;
import com.guoys.platform.persistence.service.translator.ValueExpressionTranslator;


/**
 * 基于hibernate的条件转换器助手类
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class QueryTranslatorServiceImpl implements QueryTranslatorService{

	private Dialect dialect;
	/**
	 * 简单表达式转换器
	 */
	private static ValueExpressionTranslator valueExpressionTranslator = null;
	
	/**
	 * 逻辑表达式转换器
	 */
	private static LogicExpressionTranslator logicExpressionTranslator = null;
	
	/**
	 * 链式逻辑表达式转换器
	 */
	private static ChainLogicExpressionTranslator chainLogicExpressionTranslator = null;
	
	
	public QueryTranslatorServiceImpl(Dialect dialect){
		this.dialect=dialect;
	}
	
	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslatorService#getTranslator(com.bosssoft.frame.data.query.IExpression)
	 */
	@Override
	public QueryTranslator getTranslator(Expression expression) {
		if(Expression.TYPE_SIMPLE.equals(expression.getType())){
			if(valueExpressionTranslator == null)
				valueExpressionTranslator = new ValueExpressionTranslator(dialect);
			return valueExpressionTranslator;
		}else if(Expression.TYPE_LOGIC.equals(expression.getType())){
			if(logicExpressionTranslator == null)
				logicExpressionTranslator = new LogicExpressionTranslator(dialect);
			return logicExpressionTranslator;
		}else if(Expression.TYPE_CHAINLOGIC.equals(expression.getType())){
			if(chainLogicExpressionTranslator == null)
				chainLogicExpressionTranslator = new ChainLogicExpressionTranslator(dialect);
			return chainLogicExpressionTranslator;
		}
		throw new IllegalArgumentException("Unknow expression type "+expression.getType());
	}
	
	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslatorService#getPreCondition(java.util.List, java.lang.String[])
	 */
	@Override
	public PreQuery getPreCondition(List<Expression> expressions,String... fromes){
		if(expressions == null || expressions.size() == 0)
			return null;
		String where = "";
		Object[] vals = null;
		for(Expression expression:expressions){
			PreQuery con = getTranslator(expression).getPreCondition(expression,fromes);
			if(where.length() > 0){
				where += " and ";
				vals = ArrayUtils.add(vals, con.getParameters());
			}else{
				vals = con.getParameters();
			}
			where += con.getQuery();
		}
		return new PreQuery(where,vals);
	}



	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslatorService#getCondition(java.util.List, java.lang.String[])
	 */
	@Override
	public String getCondition(List<Expression> expressions, String... fromes) {
		if(expressions == null || expressions.size() == 0)
			return null;
		String where = "";
		for(Expression expression:expressions){
			if(where.length() > 0)
				where += " and ";
			where += getTranslator(expression).getPreCondition(expression,fromes);
		}
		return where;
	}
	
	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslatorService#getPreCondition(java.util.List, java.lang.String[])
	 */
	@Override
	public Map<String,Object> getMybatisCondition(List<Expression> expressions,String paramKey,String... fromes){
		if(expressions == null || expressions.size() == 0)
			return null;
		String where = "";
		Map<String,Object> parameters = new HashMap<String,Object>();
		for(Expression expression:expressions){
			parameters = getTranslator(expression).getMybatisCondition(expression,parameters,paramKey,fromes);
			String con = (String)parameters.remove(QueryHelper.MYBATIS_CON);
			if(StringUtils.isEmpty(con))
				continue;
			if(where.length() > 0){
				where += " and ";
			}
			where += con;
		}
		parameters.put(QueryHelper.MYBATIS_CON, where);
		return parameters;
	}
	
	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getPreCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public PreQuery getPreCondition(Expression expression, String... froms) {
		return getTranslator(expression).getPreCondition(expression, froms);
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public String getCondition(Expression expression, String... froms) {
		return getTranslator(expression).getCondition(expression);
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getMybatisCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[], java.util.Map)
	 */
	@Override
	public Map<String, Object> getMybatisCondition(Expression expression, Map<String, Object> parameters,String paramKey,String... fromes) {
		return getTranslator(expression).getMybatisCondition(expression,parameters,paramKey, fromes);
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}
	

}
