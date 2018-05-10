package com.guoys.platform.persistence.spring;

import java.util.Map;

import com.guoys.platform.commons.lang.data.Searcher;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.ognl.PropertyAccessor;
import org.apache.ibatis.scripting.xmltags.DynamicContext;


import com.guoys.platform.persistence.dialect.Dialect;
import com.guoys.platform.persistence.service.QueryHelper;



/**
 * 用于覆盖DynamicContext的Ognl属性访问控制
 * @version 1.0 2014-5-13
 * @since 1.6
 */
public class ContextAccessor implements PropertyAccessor {
	
	public Dialect dialect;
	

	public ContextAccessor(Dialect dialect){
		this.dialect=dialect;
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.ognl.PropertyAccessor#getProperty(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getProperty(Map context, Object target, Object name)
	        throws OgnlException {
	      Map map = (Map) target;

	      Object result = map.get(name);
	      if (result != null) {
	        return result;
	      }
	      Object parameterObject = map.get(DynamicContext.PARAMETER_OBJECT_KEY);
	      if(!map.containsKey("_searcher_convert")){
	    	  map.put("_searcher_convert", "Y");
		      if(parameterObject instanceof Searcher){
		    	  parameterObject = QueryHelper.getMybatisCondition(dialect,(Searcher)parameterObject);
		    	  map.put(DynamicContext.PARAMETER_OBJECT_KEY, parameterObject);
		      }else if(parameterObject instanceof Map){
		    	  ObjectWrapperFactoryExt.convertSearcher(dialect,(Map)parameterObject, null);
		      }
	      }
	      
	      if("utils".equalsIgnoreCase(name.toString())){
	    	  return dialect;
	      }
	      
	      if (parameterObject instanceof Map) {
	    	  return ((Map)parameterObject).get(name);
	      }
	      
	      return null;
	    }

	    /* (non-Javadoc)
	     * @see org.apache.ibatis.ognl.PropertyAccessor#setProperty(java.util.Map, java.lang.Object, java.lang.Object, java.lang.Object)
	     */
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public void setProperty(Map context, Object target, Object name, Object value)
	        throws OgnlException {
	      Map map = (Map) target;
	      map.put(name, value);
	    }

	@Override
	public String getSourceAccessor(OgnlContext ognlContext, Object o, Object o1) {
		return null;
	}

	@Override
	public String getSourceSetter(OgnlContext ognlContext, Object o, Object o1) {
		return null;
	}
}