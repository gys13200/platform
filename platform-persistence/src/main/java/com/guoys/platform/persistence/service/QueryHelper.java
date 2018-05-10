package com.guoys.platform.persistence.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.guoys.platform.commons.lang.data.Searcher;
import com.guoys.platform.commons.lang.data.Sort;
import com.guoys.platform.persistence.dialect.Dialect;
/**
 * 查询助手类
 * @version 1.0 2013-12-12
 * @since 1.6
 */
public final class QueryHelper {

	/**
	 * 条件名称
	 */
	public final static String MYBATIS_CON = "__QUERYCON";
	
	/**
	 * 取得QL工厂对象
	 * @return 工厂对象
	 */
	public static QueryTranslatorService getQueryTranslatorService(Dialect dialect){
		return new QueryTranslatorServiceImpl(dialect);
	}
	
	/**
	 * 取得一组表达式的预编译条件
	 * @param seacher 查询条件对象
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 预编译条件
	 */
	public static PreQuery getPreCondition(Dialect dialect, Searcher seacher, String... fromes){
		if(seacher == null)
			return null;
		return getQueryTranslatorService(dialect).getPreCondition(seacher.toExpressions(), fromes);
	}

	
	/**
	 * 取得一组表达式的查询条件
	 * @param seacher 查询条件对象
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 查询条件
	 */
	public static String getCondition(Dialect dialect,Searcher seacher,String... fromes){
		if(seacher == null)
			return null;
		return getQueryTranslatorService(dialect).getCondition(seacher.toExpressions(), fromes);
	}
	
	/**
	 * 取得一组表达式的查询条件
	 * @param expression 表达式
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 查询条件
	 */
	public static Map<String,Object> getMybatisCondition(Dialect dialect,Searcher searcher){
		return getMybatisCondition(dialect,null,searcher);
	}
	/**
	 * 取得一组表达式的查询条件
	 * @param expression 表达式
	 * @param fromes 查询对像列表，如果有别名可以直接写成 Table as name
	 * @return 查询条件
	 */
	public static Map<String,Object> getMybatisCondition(Dialect dialect,String paramKey,Searcher searcher){
		if(searcher == null)
			return null;
		Map<String,Object> conMap = getQueryTranslatorService(dialect).getMybatisCondition(searcher.toExpressions(),paramKey, searcher.getFromAlias());
		if(conMap == null)
			conMap = new HashMap<String,Object>();
		return conMap;
	}
	
	/**
	 * 根据别名取得别名转化后的属性全名
	 * @param property 属性名
	 * @param froms 别名映射
	 * @return 属性全名
	 */
	public static String getFullPropertyName(String property,String... froms) {
		if(froms == null)
			return property;
		Map<String,String> aliases = new HashMap<String,String>();
		String mainFrom = null;
		for(String from : froms){
			if(from.indexOf(" as ") > 0){
				String[] names = from.split(" as ");
				aliases.put(names[0].trim(), names[1].trim());
				if(mainFrom == null)
					mainFrom = names[1].trim();
			}else if(from.indexOf(" ") > 0){
				String[] names = from.split(" ");
				aliases.put(names[0].trim(), names[1].trim());
				if(mainFrom == null)
					mainFrom = names[1].trim();
			}
		}
		int pos = property.indexOf(".");
		if(pos <= 0){
			if(mainFrom != null)
					return mainFrom += "." + property;
			return property;
		}
		String name = property.substring(0,pos);
		String a = aliases.get(name);
		if(a != null){
			property = a + property.substring(pos);
		}
		return property;
	}
	/**
	 * 取得排序串
	 * @param sort 排序对象
	 * @param froms 别名映射
	 * @return 排序串
	 */
	public static String getSort(Sort sort, String... froms){
		if(sort == null)
			return null;
		Iterator<Sort.Order> it = sort.iterator();
		String orderString = "";
		while(it.hasNext()){
			Sort.Order order = it.next();
			String protperty = getFullPropertyName(order.getProperty(),froms);
			if(orderString.length()>0)
				orderString += ",";
			orderString += protperty + " " + order.getDirection().toString();
		}
		return orderString;
	}
	
	/**
	 * 将条件转化为带索引的条件串
	 * @param where 原始条件
	 * @param startIndex 开始索引
	 * @return 转化后的条件串
	 */
	public static String getIndexCondition(String where,int startIndex){
		if(where == null||!where.contains("?"))
			return where;
		if(startIndex == 0)
			startIndex++;
		String[] parts = where.split("\\?");
		StringBuffer result = new StringBuffer();
		int count = 0;
		for(String part:parts){
			if(part.length() == 0)
				continue;
			result.append(part);
			count++;
			if(count<parts.length){
				result.append("?").append(startIndex);
				startIndex++;
			}
		}
		return result.toString();
	}
}
