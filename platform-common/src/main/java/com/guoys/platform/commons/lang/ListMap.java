package com.guoys.platform.commons.lang;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LinkedMap;

/**
 * List和Map的结合实现
 * @author yaboocn
 * @version 1.0 2009-12-8
 * @since 1.0
 */
public class ListMap<K,V> implements Map<K,V>,Cloneable, Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4012791369088550906L;
	
	/**对象集合*/
	private LinkedMap objects = new LinkedMap();
	
	/**
	 * 将指定键值的对象放入列表Map中
	 * @param key 键值
	 * @param object 需放入的对象
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value){
		return (V)objects.put(key,value);
	}

	/**
	 * 将指定键值的对象放入列表Map中的指定位置
	 * @param index 需放入的位置
	 * @param key 键值
	 * @param value 需放入的对象
	 */
	public void put(int index, K key, V value){
		objects.remove(key);
		LinkedMap tempMap = new LinkedMap();
		int size = objects.size();
		if(size < index)
			throw new ArrayIndexOutOfBoundsException(index);
		for(int i = size - 1; i >= index; i--){
			Object k = objects.get(i);
			tempMap.put(k, objects.remove(i));
		}
		put(key, value);
		size = tempMap.size();
		for(int i = size - 1; i >= 0; i--){
			Object k = tempMap.get(i);
			objects.put(k, tempMap.get(k));
		}
		
	}

	/**
	 * 取得指定键值对应的对象
	 * @param key 键值
	 * @return 键值对应的对象
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key){
		return (V)objects.get(key);
	}

	/**
	 * 取得指定位置的对象
	 * @param index 位置索引
	 * @return 索引对应对象
	 */
	@SuppressWarnings("unchecked")
	public V get(int index){
		return (V)objects.getValue(index);
	}

	/**
	 * 设置指定索引的对象值
	 * @param index 指定索引
	 * @param value 指定的对象值
	 */
	public void set(int index, V value){
		Object key = objects.get(index);
		if(key != null){
			objects.put(key, value);
		}else{
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	/**
	 * 取得指定键值的对应索引
	 * @param key 键值
	 * @return 索引
	 */
	public int indexOf(Object key){
		return objects.indexOf(key);
	}

	/**
	 * 取得指定索引对应的键值
	 * @param index 索引
	 * @return 键值
	 */
	@SuppressWarnings("unchecked")
	public K getKey(int index){
		return (K) objects.get(index);
	}

	/**
	 * 移除指定键值对应的对象
	 * @param key 键值
	 * @return 键值对应的对象
	 */
	@SuppressWarnings("unchecked")
	public V remove(Object key){
		return (V) objects.remove(key);
	}

	/**
	 * 移除指定索引对应的对象
	 * @param index 索引
	 * @return 索引对应的对象
	 */
	@SuppressWarnings("unchecked")
	public V remove(int index){
		return (V) objects.remove(index);
	}

	/**
	 * 清除所有对象
	 */
	public void clear(){
		objects.clear();
	}

	/**
	 * 取得当前列表Map的大小
	 * @return 列表Map的大小
	 */
	public int size(){
		return objects.size();
	}

	/**
	 * 取得列表Map的键值列表对象
	 * @return 键值列表对象
	 */
	@SuppressWarnings("unchecked")
	public List<K> keyList(){
		return objects.asList();
	}

	/**
	 * 取得键值的迭代器
	 * @return 迭代器
	 */
	@SuppressWarnings("unchecked")
	public Iterator<K> iterator(){
		return objects.asList().iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	public ListMap<K,V> clone()throws CloneNotSupportedException{
		ListMap<K,V> newObject =  (ListMap<K,V>)super.clone();
		newObject.objects = (LinkedMap)objects.clone();
		return newObject;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return objects.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return objects.containsValue(value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@SuppressWarnings("unchecked")
	public Set<Entry<K,V>> entrySet() {
		return objects.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return objects.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return objects.keySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		objects.putAll(m);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		return objects.values();
	}
}
