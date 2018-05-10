package com.guoys.platform.commons.lang;



/**
 * 值对类，描述键值和值的数据类型
 * @author yangbo
 * @version 1.0 2013-12-6
 * @since 1.6
 */
public class ValuePair implements Comparable<ValuePair>{
	
	/**
	 * 键值对象
	 */
	private String key;
	
	/**
	 * 值对象
	 */
	private String value;

	/**
	 * 构造方法
	 * @param key 键值
	 * @param value 值
	 */
	public ValuePair(String key, String value){
		this.key = key;
		this.value = value;
	}

	/**
	 * 取得键值
	 * @return 键值
	 */
	public String getKey(){
		return key;
	}
	
	/**
	 * 设置键值
	 * @param key 键值
	 */
	public void setKey(String key){
		this.key = key;
	}

	/**
	 * 取得值对中的值
	 * @return 值
	 */
	public String getValue(){
		return value;
	}

	

	/**
	 * 设置值对中的值
	 * @param value 值
	 */
	public void setValue(String value){
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return key + ":" + value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ValuePair o) {
		if(o == null)
			return 1;
		return this.toString().compareTo(o.toString());
	}
}
