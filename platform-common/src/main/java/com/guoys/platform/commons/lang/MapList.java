package com.guoys.platform.commons.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public abstract class MapList<K,E> implements List<E> {

	private List<E> list = new ArrayList<E>();
	
	private Map<K,E> map = new HashMap<K,E>();
	
	public abstract K getObjectKey(E e);

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		K key = this.getObjectKey(e);
		if(this.map.containsKey(key)){
			this.list.remove(map.get(key));
			this.map.remove(key);
		}
		map.put(key, e);
		return list.add(e);
		
	}

	@Override
	public boolean remove(Object o) {
		@SuppressWarnings("unchecked")
		Object key = this.getObjectKey((E)o);
		this.map.remove(key);
		return this.list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for(E e:c){
			K key = this.getObjectKey(e);
			if(this.map.containsKey(key)){
				this.list.remove(e);
			}
			this.map.put(key, e);
		}
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modify = false;
		for(Object o:c){
			if(this.remove(o)){
				modify = true;
			}
		}
		return modify;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		this.map.clear();
		this.list.clear();
	}

	@Override
	public E get(int index) {
		return this.list.get(index);
	}

	@Override
	public E set(int index, E element) {
		E e = list.get(index);
		this.map.remove(this.getObjectKey(e));
		this.map.put(this.getObjectKey(element), element);
		return this.list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		K key = this.getObjectKey(element);
		if(this.map.containsKey(key)){
			E e = this.map.get(key);
			this.list.remove(e);
			this.map.remove(key);
		}
		this.map.put(key, element);
		this.list.add(index, element);
	}

	@Override
	public E remove(int index) {
		E e = this.list.remove(index);
		K key = this.getObjectKey(e);
		this.map.remove(key);
		return e;
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return this.list.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}
	
	public E getByKey(K key){
		return this.map.get(key);
	}
	
	public boolean containsKey(K key){
		return this.map.containsKey(key);
	}
	
//	public boolean put(E e){
//		K key = this.getObjectKey(e);
//		if(this.map.containsKey(key)){
//			E ele = map.get(key);
//			list.remove(ele);
//			map.remove(key);
//		}
//		map.put(key, e);
//		return list.add(e);
//	}
	
}
