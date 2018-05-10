package com.guoys.platform.commons.lang.enums;

/**
 * 枚举序列的对象接口，
 * 	让对象拥有能提供text和value的行为
 * @author Mr.T
 *
 */
public interface EnumSeriesSupport {

	/**
	 * 获取下拉框的文本
	 * @return
	 */
	String getText();
	/**
	 * 获取下拉框的值
	 * @return
	 */
	String getValue();
}
