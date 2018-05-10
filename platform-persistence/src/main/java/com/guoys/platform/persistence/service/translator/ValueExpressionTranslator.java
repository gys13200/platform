package com.guoys.platform.persistence.service.translator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.guoys.platform.commons.lang.DataType;
import com.guoys.platform.commons.lang.expression.Expression;
import com.guoys.platform.commons.lang.expression.Operator;
import com.guoys.platform.commons.lang.expression.ValueExpression;
import com.guoys.platform.commons.lang.type.BDate;
import com.guoys.platform.commons.lang.type.BDateTime;
import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.PreQuery;
import com.guoys.platform.persistence.service.QueryHelper;
import com.guoys.platform.persistence.service.QueryTranslator;
import org.apache.commons.lang.ObjectUtils;



/**
 * 基于简单值条件的转换器
 * @author yangbo
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public class ValueExpressionTranslator implements QueryTranslator {

	private Dialect dialect;
	
	public ValueExpressionTranslator(Dialect dialect){
		this.dialect=dialect;
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getPreCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public PreQuery getPreCondition(Expression expression, String... froms) {
		ValueExpression exp = getExpression(expression);
		
		String property = QueryHelper.getFullPropertyName(exp.getPropertyName(),froms);
		Object value = exp.getValue();
		String con = null;
		switch(exp.getOperator()){
		
		case NOTEQUAL:
			con = property + "!=?";
			break;
			
		case GREATER:
			con = property + ">?";
			break;
			
		case LESS:
			con = property + "<?";
			break;
		
		case GREATEREQUAL:
			con = property + ">=?";
			break;
			
		case LESSEQAUL:
			con = property + "<=?";
			break;
		case LIKEANY:
		case LIKESTART:
		case LIKEEND:
		case NOTLIKEANY:
			return getLikePreCondition(property,exp);
		case ISNULL:
			con = property + " is null";
			value = null;
			break;
		case ISNOTNULL:
			con = property + " is not null";
			value = null;
			break;
//		case IN:
//			con = property + "in ?";
//			break;
		default:
			con = property + "=?";
		}
		return new PreQuery(con,value==null?null:new Object[]{value});
	}

	/**
	 * 取得like表达式的预编译条件信息
	 * @param exp 表达式对象
	 * @return 预编译条件
	 */
	protected PreQuery getLikePreCondition(String property,ValueExpression exp){
		Object value = exp.getValue();
		if(DataType.isNumberType(exp.getDataType())){
			property = "CAST(" + property + " AS string)";
		}else if(DataType.DATE == exp.getDataType()){
			property =getDialect().getDateColumnCondition(property,BDate.DEFAULT_FORMAT.toPattern());
			if(value instanceof Date){
				value = BDate.DEFAULT_FORMAT.format(value);
			}
		}
		switch(exp.getOperator()){
		case LIKESTART:
			value = value.toString()+"%";
			break;
		case LIKEEND:
			value = "%"+value.toString();
			break;
		default:
			value = "%"+value.toString()+"%";
			break;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if(exp.getOperator()== Operator.NOTLIKEANY){
			sb.append(" not");
		}
		sb.append(" like ?");
		
		return new PreQuery(sb.toString(),new Object[]{value});
	}

	
	
	/**
	 * 转换表达式为值类型表达式
	 * @param expression 表达式
	 * @return 值表达式
	 */
	protected ValueExpression getExpression(Expression expression){
		if(!(expression instanceof ValueExpression))
			throw new IllegalArgumentException("非法的表达式转换");
		return (ValueExpression)expression;
	}

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.data.query.IQueryTranslator#getCondition(com.bosssoft.frame.data.query.IExpression, java.lang.String[])
	 */
	@Override
	public String getCondition(Expression expression, String... froms) {
		ValueExpression exp = getExpression(expression);
		
		String property = QueryHelper.getFullPropertyName(exp.getPropertyName(),froms);
		String origion = property;
		Object value = exp.getValue();
		if(DataType.DATE == exp.getDataType()){
			property = getDialect().getDateColumnCondition(property,BDate.DEFAULT_FORMAT.toPattern());
			if(value instanceof Date){
				value = BDate.DEFAULT_FORMAT.format(value);
			}
		}else if(DataType.DATETIME == exp.getDataType()){
			property =getDialect().getDateColumnCondition(property, BDateTime.DEFAULT_FORMAT.toPattern());
			if(value instanceof Date){
				value = BDateTime.DEFAULT_FORMAT.format(value);
			}
		}
		Operator op = exp.getOperator();
		if(op == Operator.LIKEANY){
			value = "%"+value.toString()+"%";
		}else if(op == Operator.LIKESTART){
			value = value.toString()+"%";
		}else if(op == Operator.LIKEEND){
			value = "%"+value.toString();
		}
		if(!DataType.isNumberType(exp.getDataType())){
			value = "'"+value+"'";
		}
		switch(op){
		case NOTEQUAL:
			return property + "!="+value;
			
		case GREATER:
			return property + ">"+value;
			
		case LESS:
			return property + "<"+value;
		
		case GREATEREQUAL:
			return property + ">="+value;
			
		case LESSEQAUL:
			return property + "<="+value;

		case LIKEANY:
		case LIKESTART:
		case LIKEEND:
		case NOTLIKEANY:
			return property + "like"+value;
		case ISNULL:
			return origion + " is null";
		case ISNOTNULL:
			return origion + " is not null";
		default:
			return property + "="+value;
		}
	}

	/**
	 * 取得like表达式的预编译条件信息
	 * @param exp 表达式对象
	 * @return 预编译条件
	 */
	protected String[] getLikeMybatisCondition(String property,ValueExpression exp,String paramExp){
		Object value = exp.getValue();
		if(DataType.isNumberType(exp.getDataType())){
			property = "CAST(" + property + " AS string)";
		}else if(DataType.DATE == exp.getDataType()){
			property =getDialect().getDateColumnCondition(property,BDate.DEFAULT_FORMAT.toPattern());
			if(value instanceof Date){
				value = BDate.DEFAULT_FORMAT.format(value);
			}
		}
		switch(exp.getOperator()){
		case LIKESTART:
			value = value.toString()+"%";
			break;
		case LIKEEND:
			value = "%"+value.toString();
			break;
		default:
			value = "%"+value.toString()+"%";
			break;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if(exp.getOperator()==Operator.NOTLIKEANY){
			sb.append(" not");
		}
		sb.append(" like ");
		sb.append(paramExp);
		//return new PreQuery(sb.toString(),new Object[]{value});
		//return new String[]{property + " like "+paramExp,value.toString()};
		return new String[]{sb.toString(),value.toString()};
	}
	@Override
	public Map<String, Object> getMybatisCondition(Expression expression, Map<String, Object> parameters,String paramKey,
			String... fromes) {
		ValueExpression exp = getExpression(expression);
		
		String property = QueryHelper.getFullPropertyName(exp.getPropertyName(),fromes);
		Object value = exp.getValue();
		String propName = exp.getPropertyName();
		propName = propName.replaceAll("\\.", "__");
		if(parameters == null)
			parameters = new HashMap<String,Object>();
		Object oldValue = parameters.get(propName);
		int c = 1;
		while(oldValue != null){
			if(ObjectUtils.equals(value, oldValue))
				break;
			propName+=c;
			oldValue = parameters.get(propName);
			c++;
		}
		if(paramKey == null){
			paramKey = "";
		}else{
			paramKey+=".";
		}
		
		String paramExp = "#{"+paramKey+propName+"}";
		String con = null;
		switch(exp.getOperator()){
		
		case NOTEQUAL:
			con = property + "!="+paramExp;
			break;
			
		case GREATER:
			con = property + ">"+paramExp;
			break;
			
		case LESS:
			con = property + "<"+paramExp;
			break;
		
		case GREATEREQUAL:
			con = property + ">="+paramExp;
			break;
			
		case LESSEQAUL:
			con = property + "<="+paramExp;
			break;

		case LIKEANY:
		case LIKESTART:
		case LIKEEND:
		case NOTLIKEANY:
			String[] vals= getLikeMybatisCondition(property,exp,paramExp);
			con=vals[0];
			value = vals[1];
			break;
		case ISNULL:
			con = property + " is null";
			value = null;
			break;
		case ISNOTNULL:
			con = property + " is not null";
			value = null;
			break;
		case IN:{
			con = property + " in "+paramExp;
			break;
		}
		default:
			con = property + "="+paramExp;
		}
		if(value != null)
			parameters.put(propName, value);
		parameters.put(QueryHelper.MYBATIS_CON,con);
		return parameters;
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}

}
