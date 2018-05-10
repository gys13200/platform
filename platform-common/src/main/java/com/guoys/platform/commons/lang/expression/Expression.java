package com.guoys.platform.commons.lang.expression;

/**
 * 表达式接口
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public interface Expression extends java.io.Serializable{

	/**
	 * 简单表达式
	 */
	public static final String TYPE_SIMPLE = "SIMPLEVALUE";
	/**
	 * 逻辑表达式“与或”
	 */
	public static final String TYPE_LOGIC = "LOGICAL";
	
	/**
	 * 链式逻辑表达式“与或”，表达式列表中的表达式并列与或者或
	 */
	public static final String TYPE_CHAINLOGIC = "CHAINLOGICAL";
	/**
	 * 取得表达式类型
	 * @return 表达式类型
	 */
	public String getType();
}
