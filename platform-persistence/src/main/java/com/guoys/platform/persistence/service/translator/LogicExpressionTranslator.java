package com.guoys.platform.persistence.service.translator;

import java.util.HashMap;
import java.util.Map;

import com.guoys.platform.commons.lang.expression.Expression;
import com.guoys.platform.commons.lang.expression.LogicExpression;
import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.PreQuery;
import com.guoys.platform.persistence.service.QueryHelper;
import com.guoys.platform.persistence.service.QueryTranslator;
import com.guoys.platform.persistence.service.QueryTranslatorService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 逻辑条件表达式的转换器
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class LogicExpressionTranslator implements QueryTranslator {

	private Dialect dialect;
	private QueryTranslatorService service;
	
	public LogicExpressionTranslator(Dialect dialect){
		this.dialect=dialect;
		service = QueryHelper.getQueryTranslatorService(dialect);
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getPreCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public PreQuery getPreCondition(Expression expression, String... froms) {
		
		LogicExpression exp = getExpression(expression);
		
		PreQuery leftCon = null;
		Expression left = exp.getLeft();
		if(left != null){
			leftCon = service.getPreCondition(left, froms);
		}
		Expression right = exp.getRight();
		PreQuery rightCon = null;
		if(right != null){
			rightCon = service.getPreCondition(right, froms);
		}
		if(StringUtils.isEmpty(leftCon.getQuery()))
			return rightCon;
		if(StringUtils.isEmpty(rightCon.getQuery()))
			return leftCon;
		String condition = null;
		Object[] vals = null;
		if(exp.isOr()){
			condition = "(" + leftCon.getQuery()+" or " + rightCon.getQuery() + ")";
		}else{
			condition = "(" + leftCon.getQuery()+" and " + rightCon.getQuery() + ")";
		}
		vals = ArrayUtils.add(leftCon.getParameters(), leftCon.getParameters());
		return new PreQuery(condition,vals);
	}

	

	/**
	 * 转换表达式为值类型表达式
	 * @param expression 表达式
	 * @return 值表达式
	 */
	protected LogicExpression getExpression(Expression expression){
		if(!(expression instanceof LogicExpression))
			throw new IllegalArgumentException("非法的表达式转换");
		return (LogicExpression)expression;
	}

	

	@Override
	public String getCondition(Expression expression, String... froms) {
		LogicExpression exp = getExpression(expression);

		String leftCon = null;
		Expression left = exp.getLeft();
		if(left != null){
			leftCon = service.getCondition(left, froms);
		}
		Expression right = exp.getRight();
		String rightCon = null;
		if(right != null){
			rightCon = service.getCondition(right, froms);
		}
		if(StringUtils.isEmpty(leftCon))
			return rightCon;
		if(StringUtils.isEmpty(rightCon))
			return leftCon;
		if(exp.isOr()){
			return  "(" + leftCon+" or " + rightCon + ")";
		}else{
			return "(" + leftCon +" and " + rightCon + ")";
		}
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getMybatisCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[], java.util.Map)
	 */
	@Override
	public Map<String, Object> getMybatisCondition(Expression expression,
			Map<String, Object> parameters,String paramKey,String... fromes) {
		LogicExpression exp = getExpression(expression);

		String leftCon = null;
		if(parameters == null)
			parameters = new HashMap<String,Object>();
		Expression left = exp.getLeft();
		if(left != null){
			parameters = service.getMybatisCondition(left,parameters,paramKey, fromes);
			leftCon = (String)parameters.get(QueryHelper.MYBATIS_CON);
		}
		Expression right = exp.getRight();
		String rightCon = null;
		if(right != null){
			parameters = service.getMybatisCondition(right,parameters,paramKey, fromes);
			rightCon = (String)parameters.get(QueryHelper.MYBATIS_CON);
		}
		if(StringUtils.isEmpty(leftCon))
			return parameters;
		if(StringUtils.isEmpty(rightCon)){
			parameters.put(QueryHelper.MYBATIS_CON,leftCon);
			return parameters;
		}
		String condition = null;
		if(exp.isOr()){
			condition = "(" + leftCon+" or " + rightCon + ")";
		}else{
			condition = "(" + leftCon+" and " + rightCon + ")";
		}
		parameters.put(QueryHelper.MYBATIS_CON,condition);
		return parameters;
	}



	@Override
	public Dialect getDialect() {
		return dialect;
	}
}
