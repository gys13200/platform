package com.guoys.platform.commons.lang.expression;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 查询条件逻辑表达式表达式
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class ChainLogicExpression implements Expression{


	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表达式列表
	 */
	private List<Expression> expressions = null;
	
	/**
	 * 操作符
	 */
	private boolean isOr = false;
	
	/**
	 * 构造方法
	 */
	public ChainLogicExpression(){
		
	}
	
	/**
	 * 表达式构造方法
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @param operator 操作符
	 */
	public ChainLogicExpression(boolean isOr,Expression...expressions){
		addExpression(expressions);
		setOr(isOr);
	}



	/**
	 * 取得链式逻辑表达式中包含的表达式列表
	 * @return 表达式列表
	 */
	public List<Expression> getExpressions() {
		return expressions;
	}

	/**
	 * 设置链式逻辑表达式中包含的表达式列表
	 * @param expressions 表达式列表
	 */
	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * 添加表达式到链式表达式中
	 * @param expression 表达式数组
	 */
	public void addExpression(Expression... expressions){
		if(this.expressions == null)
			this.expressions = new ArrayList<Expression>();
		CollectionUtils.addAll(this.expressions, expressions);
	}

	
	/**
	 * 判断是否为‘或’逻辑操作
	 * @return 布尔值
	 */
	public boolean isOr() {
		return isOr;
	}

	/**
	 * 设置是否为‘或’逻辑操作
	 * @param isOr 布尔值
	 */
	public void setOr(boolean isOr) {
		this.isOr = isOr;
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IExpression#getType()
	 */
	@Override
	public String getType() {
		return TYPE_CHAINLOGIC;
	}

	/**
	 * 取得表达式大小
	 * @return 表达式大小
	 */
	public int size(){
		if(expressions == null)
			return 0;
		return expressions.size();
	}
}
