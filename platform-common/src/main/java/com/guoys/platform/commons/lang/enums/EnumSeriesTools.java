package com.guoys.platform.commons.lang.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 枚举序列的工具类
 * 	该类服务于下列两中类：
 * 		1类必须继承EnumSeries接口
 * 		2枚举类型必须是继承EnumSeriesSupport的接口
 * 
 * @author Mr.T
 *
 */
public class EnumSeriesTools {

	/**
	 * 根据类名获取下拉框数据源
	 * @param className
	 * @return
	 */

	public static List<EnumSeriesSupport> getComboDataSource(String className){
		Class<?> cls = classForName(className);
		if(cls == null){
			return new ArrayList<EnumSeriesSupport>();
		}
		if(cls.isEnum()){
			return getEnumComboDataSource(cls);
		}else{
			return getComboDataSource(cls);
		}
	}
	/**
	 * 获取枚举类型的数据源
	 * @param enumClass
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<EnumSeriesSupport> getEnumComboDataSource(Class enumClass){
		List<EnumSeriesSupport> result = new ArrayList<EnumSeriesSupport>();
		Class<?>[] interfaces = enumClass.getInterfaces();
		if(interfaces.length == 0){
			return result;
		}
		if(!interfaces[0].equals(EnumSeriesSupport.class)){
			return result;
		}
		EnumSet<?> enumSet = EnumSet.allOf(enumClass);
		for (Object item : enumSet) {
			result.add((EnumSeriesSupport)item);
		}
		return result;
	}
	
	/**
	 * 获取枚举类型的数据源
	 * @param enumClass
	 * @return
	 */
	private static List<EnumSeriesSupport> getComboDataSource(Class<?> cls){
		Class<?>[] interfaces = cls.getInterfaces();
		if(interfaces.length == 0){
			return new ArrayList<EnumSeriesSupport>();
		}
		if(!interfaces[0].equals(EnumSeries.class)){
			return new ArrayList<EnumSeriesSupport>();
		}
		@SuppressWarnings("unchecked")
		EnumSeries dataSource = getComboDataSourceInstance((Class<EnumSeries>) cls);
		if(dataSource != null){
			return dataSource.getEnumSeries();
		}
		return new ArrayList<EnumSeriesSupport>();
	}
	/**
	 * 根据类名生成Class类
	 * @param className
	 * @return
	 */
	private static Class<?> classForName(String className){
		Class<?> result = null;
		try{
			result = Class.forName(className);
		}catch (ClassNotFoundException e){
			
		}
		return result;
	}
	/**
	 * 根据类名生成Class类
	 * @param className
	 * @return
	 */
	private static EnumSeries getComboDataSourceInstance(Class<EnumSeries> cls){
		Object result = null;
		try{
			result = cls.newInstance();
		}catch (Exception e){
			
		}
		if(result != null){
			return (EnumSeries)result;
		}
		return null;
	}
}
