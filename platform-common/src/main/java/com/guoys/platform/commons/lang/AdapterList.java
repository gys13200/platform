package com.guoys.platform.commons.lang;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 列表适配器，为了直接将整个列表的对象适配成新对象的列表
 * 检少循环创建新对象的次数
 * @author yangbo
 * @version 1.0 2013-12-6
 * @since 1.6
 */
public class AdapterList<E,T> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable{

	/**
	 * 日志对象
	 */
	protected  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -664686039444125456L;

	/**
	 * 原始列表
	 */
	private List<T> original = null;
	
	/**
	 * 适配类
	 */
	private Class<? extends E> adapterClass = null;
	
	/**
	 * 构造方法
	 * @param adapterClass 适配器类
	 * @param original 原始列表
	 */
	public AdapterList(Class<? extends E> adapterClass,List<T> original){
		this.adapterClass = adapterClass;
		this.original = original;
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public E get(int index) {
		if(this.adapterClass == null || size() == 0)
			return null;
		T t = original.get(index);
		try {
			return adapterClass.getConstructor(t.getClass()).newInstance(t);
		} catch (Exception e) {
			logger.error("适配对象构造出错！",e);
			return null;
		} 
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		if(original == null)
			return 0;
		return original.size();
	}

}
