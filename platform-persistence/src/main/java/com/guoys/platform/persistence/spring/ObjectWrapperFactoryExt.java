package com.guoys.platform.persistence.spring;

import java.util.Map;

import com.guoys.platform.commons.lang.data.Searcher;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;


import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.QueryHelper;

/**
 * mybatis对象包装工厂类
 * @author yangbo
 * @version 1.0 2014-5-13
 * @since 1.6
 */
public class ObjectWrapperFactoryExt extends DefaultObjectWrapperFactory {

	private Dialect dialect;
	
	public ObjectWrapperFactoryExt(Dialect dialect){
		this.dialect=dialect;
	}
	/* (non-Javadoc)
	 * @see org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory#hasWrapperFor(java.lang.Object)
	 */
	@Override
	public boolean hasWrapperFor(Object object) {
		if(object instanceof Searcher){
			return true;
		}
		if( object instanceof ParamMap){
			return true;
		}
		return super.hasWrapperFor(object);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory#getWrapperFor(org.apache.ibatis.reflection.MetaObject, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
		if(object instanceof Searcher){
			return new MapWrapper(metaObject, QueryHelper.getMybatisCondition(dialect,(Searcher)object));
		}
		if(object instanceof ParamMap){
			Map<String,Object> params = (Map<String,Object>)object;
			convertSearcher(dialect,params,null);
			return new MapWrapper(metaObject, params);
		}
		return super.getWrapperFor(metaObject, object);
	}

	/**
	 * 递归将参数中的Searcher转换为Map串
	 * @param map 可能包含Map的参数
	 * @param key Searcher的ognl访问路径(如,设定参数为@Param("a")Searcher con,则key为a
	 */
	public static void convertSearcher(Dialect dialect,Map<String,Object> map,String key){
		for(Map.Entry<String, Object> e : map.entrySet()){
			if(e.getValue() instanceof Searcher){
				String keyPath;
				if(key == null){
					keyPath = e.getKey();
				}else{
					keyPath = key+"."+e.getKey();
				}
				map.put(keyPath, QueryHelper.getMybatisCondition(dialect,keyPath,(Searcher)e.getValue()));
			}else if(e.getValue() instanceof Map){
				String keyPath;
				if(key == null){
					keyPath = e.getKey();
				}else{
					keyPath = key+"."+e.getKey();
				}
				@SuppressWarnings("unchecked")
				Map<String,Object> m = (Map<String,Object>)e.getValue();
				convertSearcher(dialect,m,keyPath);
			}
		};
	}
	
}
