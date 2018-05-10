package com.guoys.platform.commons.lang.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.guoys.platform.commons.lang.expression.ChainLogicExpression;
import com.guoys.platform.commons.lang.expression.Expression;
import com.guoys.platform.commons.lang.expression.Operator;
import com.guoys.platform.commons.lang.expression.ValueExpression;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 搜索类定义
 * @author yangbo
 * @version 1.0 2013-12-16
 * @since 1.6
 */
public class Searcher implements Serializable,Cloneable {
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6256786995910707674L;
	/**
	 * 条件列表
	 */
	private List<Expression> conditions = null;
	/**
	 * 自由条件列表
	 */
	private List<String> freeConditions = null;
	/**
	 * 自由搜索值
	 */
	private String freeValue = null;
	
	/**
	 * 用于属性别名转换的别名定义,字符串形式如:User as u,
	 * 该别名应用场景,如果定义的条件名为User.name 实际使用的表别名为u,需要在查询执行时进行替换
	 * User.name将被替换成u.name
	 */
	private String[] fromAlias = null;
	/**
	 * 添加查询条件
	 * @param condition 条件名称
	 */
	public void addCondition(Expression... condition){
		if(this.conditions == null)
			this.conditions = new ArrayList<Expression>();
		if(this.conditions.size() == 0){
			CollectionUtils.addAll(this.conditions, condition);
		}else{
			for(Expression con:condition){
				if(this.conditions.indexOf(con)<0){
					this.conditions.add(con);
				}
			}
		}
	}
	/**
	 * 添加查询条件
	 * @param condition 条件名称
	 * @param value 条件值
	 * @param dataType 数据类型
	 * @param operator 操作符
	 */
	public void addCondition(String condition,Object value,String dataType,String operator){
		ValueExpression exp = new ValueExpression(condition,value,dataType,operator);
		this.addCondition(exp);
	}
	/**
	 * 添加查询条件
	 * @param condition 条件名称
	 * @param value 条件值
	 * @param operator 操作符
	 */
	public void addCondition(String condition,Object value,String operator){
		ValueExpression exp = new ValueExpression(condition,value,operator);
		this.addCondition(exp);
	}
	/**
	 * 添加查询条件
	 * @param condition 条件名称
	 * @param value 条件值
	 */
	public void addCondition(String condition,Object value){
		ValueExpression exp = new ValueExpression(condition,value);
		this.addCondition(exp);
	}
	/**
	 * 设置自由条件属性列表
	 * @param freeConditions 自由条件属性列表
	 */
	public void setFreeConditions(String... freeConditions) {
		List<String> freeCons = new ArrayList<String>();
		CollectionUtils.addAll(freeCons, freeConditions);
		this.freeConditions = freeCons;
	}
	/**
	 * 添加自由条件属性
	 * @param freeCondition 自由条件属性
	 */
	public void setFreeCondition(String freeValue,String... freeCondition){
		if(freeCondition == null || freeCondition.length == 0 || StringUtils.isEmpty(freeValue))
			return;
		setFreeConditions(freeCondition);
		setFreeValue(freeValue);
	}
	/**
	 * 添加自由条件属性
	 * @param freeCondition 自由条件属性
	 */
	public void addFreeCondition(String... freeCondition){
		if(this.freeConditions == null)
			this.freeConditions = new ArrayList<String>();
		if(this.freeConditions.size() == 0){
			CollectionUtils.addAll(this.freeConditions, freeCondition);
		}else{
			for(String con:freeCondition){
				if(this.freeConditions.indexOf(con)<0){
					this.freeConditions.add(con);
				}
			}
		}
	}
	/**
	 * 转换成表达式列表
	 * @return 表达式列表
	 */
	public List<Expression> toExpressions(){
		List<Expression> cons = getConditions();
		if(StringUtils.isEmpty(getFreeValue()))
			return cons;
		ChainLogicExpression expression = new ChainLogicExpression();
		if(cons != null && cons.size() > 0){
			for(Expression con:cons){
				expression.addExpression(con);
			}
		}
		List<String> freeCons = getFreeConditions();
		String freeValue = getFreeValue();
		if(freeCons != null && freeCons.size() > 0 && StringUtils.isNotEmpty(freeValue)){
			ChainLogicExpression freeExp = new ChainLogicExpression(true);
			for(String freeCon:freeCons){
				Expression exp = new ValueExpression(freeCon,freeValue, Operator.LIKEANY);
				freeExp.addExpression(exp);
			}
			expression.addExpression(freeExp);
			
			conditions.add(expression);
		}
		return conditions;
	}
	
	//getter and setter
	/**
	 * 取得条件列表
	 * @return 条件列表
	 */
	public List<Expression> getConditions() {
		return conditions;
	}

	/**
	 * 设置条件列表
	 * @param conditions 条件列表
	 */
	public void setConditions(List<Expression> conditions) {
		this.conditions = conditions;
	}
	/**
	 * 取得自由条件值
	 * @return 条件值
	 */
	public String getFreeValue() {
		return freeValue;
	}

	/**
	 * 设置自由条件值
	 * @param freeValue 自由条件值
	 */
	public void setFreeValue(String freeValue) {
		this.freeValue = freeValue;
	}
	/**
	 * 取得自由条件属性列表
	 * @return 自由条件属性列表
	 */
	public List<String> getFreeConditions() {
		return freeConditions;
	}

	/**
	 * 设置自由条件属性列表
	 * @param freeConditions 自由条件属性列表
	 */
	public void setFreeConditions(List<String> freeConditions) {
		this.freeConditions = freeConditions;
	}
	
	/**
	 * 取得条件对应的的表(实体)别名定义类表
	 * @return 表别名定义类表
	 */
	public String[] getFromAlias() {
		return fromAlias;
	}

	/**
	 * 设置条件对应的的表(实体)别名定义类表
	 * @param fromAlias 表别名定义类表
	 */
	public void setFromAlias(String... fromAlias) {
		this.fromAlias = fromAlias;
	}
	
	/**
	 * 克隆一个新的Searcher对象
	 */
	@Override
	public Searcher clone(){
		Searcher searcher = new Searcher();
		if(this.conditions!=null){
			for(Expression exp:this.conditions){
				searcher.addCondition(exp);
			}
		}
		
		if(this.freeValue!=null){
			searcher.freeValue = new String(this.freeValue);
		}
		
		if(this.fromAlias!=null){
			searcher.fromAlias = this.fromAlias.clone();
		}
		
		if(this.freeConditions!=null){
			for(String freeCondition:this.freeConditions){
				searcher.addFreeCondition(freeCondition);
			}
		}
		return searcher;
	}
}
