package com.guoys.platform.commons.lang.expression;
/**
 * 查询条件逻辑表达式表达式
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class LogicExpression implements Expression{


	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 左侧表达式
	 */
	private Expression left = null;
	
	/**
	 * 右侧表达式
	 */
	private Expression right = null;
	
	/**
	 * 操作符
	 */
	private boolean isOr = false;
	
	/**
	 * 构造方法
	 */
	public LogicExpression(){
		
	}
	
	/**
	 * 表达式构造方法
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @param operator 操作符
	 */
	public LogicExpression(Expression left, Expression right, boolean isOr){
		setLeft(left);
		setRight(right);
		setOr(isOr);
	}

	/**
	 * 取得左侧表达式
	 * @return 左侧表达式
	 */
	public Expression getLeft() {
		return left;
	}

	/**
	 * 设置左侧表达式
	 * @param left 左侧表达式
	 */
	public void setLeft(Expression left) {
		this.left = left;
	}

	/**
	 * 取得右侧表达式
	 * @return 右侧表达式
	 */
	public Expression getRight() {
		return right;
	}

	/**
	 * 设置右侧表达式
	 * @param right 右侧表达式
	 */
	public void setRight(Expression right) {
		this.right = right;
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
		return TYPE_LOGIC;
	}

	
}
