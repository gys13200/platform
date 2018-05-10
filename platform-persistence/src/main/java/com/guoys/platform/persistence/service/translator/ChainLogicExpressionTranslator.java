package com.guoys.platform.persistence.service.translator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guoys.platform.commons.lang.expression.ChainLogicExpression;
import com.guoys.platform.commons.lang.expression.Expression;
import org.apache.commons.lang.ArrayUtils;

import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.PreQuery;
import com.guoys.platform.persistence.service.QueryHelper;
import com.guoys.platform.persistence.service.QueryTranslator;
import com.guoys.platform.persistence.service.QueryTranslatorService;

/**
 * 链式逻辑条件表达式的转换器
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class ChainLogicExpressionTranslator implements QueryTranslator {

	private Dialect dialect;
	
	private QueryTranslatorService service;
	
	public ChainLogicExpressionTranslator(Dialect dialect){
		this.dialect=dialect;
		service = QueryHelper.getQueryTranslatorService(dialect);
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getPreCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public PreQuery getPreCondition(Expression expression,String... froms) {
		
		ChainLogicExpression exp = getExpression(expression);
		List<Expression> children = exp.getExpressions();
		if(children == null || children.size() == 0)
			return null;
		if(children.size() == 1)
			return service.getPreCondition(children.get(0),froms);
		String condition = null;
		String op = " and ";
		if(exp.isOr())
			op = " or ";
		Object[] vals = null;
		for(Expression child:children){
			PreQuery current = service.getPreCondition(child,froms);
			if(condition == null){
				condition = current.getQuery();
				vals = current.getParameters();
			}else{
				condition += op + current.getQuery();
				vals = ArrayUtils.addAll(vals, current.getParameters());
			}
		}
		//if(exp.isOr())
		condition = "("+condition+")";
		return new PreQuery(condition,vals);
	}

	

	/**
	 * 转换表达式为值类型表达式
	 * @param expression 表达式
	 * @return 值表达式
	 */
	protected ChainLogicExpression getExpression(Expression expression){
		if(!(expression instanceof ChainLogicExpression))
			throw new IllegalArgumentException("非法的表达式转换");
		return (ChainLogicExpression)expression;
	}


	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public String getCondition(Expression expression,String...froms) {
		ChainLogicExpression exp = getExpression(expression);

		List<Expression> children = exp.getExpressions();
		if(children == null || children.size() == 0)
			return null;
		if(children.size() == 1)
			return service.getCondition(children.get(0),froms);
		String result = null;
		String op = " and ";
		if(exp.isOr())
			op = " or ";
		for(Expression child:children){
			String current = service.getCondition(child,froms);
			if(result == null)
				result = current;
			else
				result += op + current;
		}
//		if(exp.isOr())
		result = "("+result+")";
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getMybatisCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[], java.util.Map)
	 */
	@Override
	public Map<String, Object> getMybatisCondition(Expression expression, Map<String, Object> parameters,String paramKey,
			String... fromes) {
		ChainLogicExpression exp = getExpression(expression);
		if(parameters == null)
			parameters = new HashMap<String,Object>();
		List<Expression> children = exp.getExpressions();
		if(children == null || children.size() == 0)
			return parameters;
		
		if(children.size() == 1)
			return service.getMybatisCondition(children.get(0),parameters,paramKey,fromes);
		String condition = null;
		String op = " and ";
		if(exp.isOr())
			op = " or ";
		for(Expression child:children){
			parameters = service.getMybatisCondition(child,parameters,paramKey,fromes);
			if(condition == null){
				condition = (String)parameters.get(QueryHelper.MYBATIS_CON);
			}else{
				condition += op + (String)parameters.get(QueryHelper.MYBATIS_CON);
			}
		}
//		if(exp.isOr())
		condition = "("+condition+")";
		parameters.put(QueryHelper.MYBATIS_CON, condition);
		return parameters;
	}



	@Override
	public Dialect getDialect() {

		return dialect;
	}
}
