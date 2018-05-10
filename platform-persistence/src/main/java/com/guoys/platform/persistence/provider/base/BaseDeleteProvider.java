/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.guoys.platform.persistence.provider.base;

import java.util.ArrayList;
import java.util.List;

import com.guoys.platform.commons.configuration.ConfigurationConstants;
import com.guoys.platform.commons.configuration.PropertiesUtil;
import org.apache.ibatis.mapping.MappedStatement;


import com.guoys.platform.persistence.helper.ColumnValue;
import com.guoys.platform.persistence.helper.MapperHelper;
import com.guoys.platform.persistence.helper.MapperTemplate;
import com.guoys.platform.persistence.helper.SqlHelper;


/**
 * BaseDeleteMapper实现类，基础方法实现类
 *
 * 
 */
public class BaseDeleteProvider extends MapperTemplate {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    
   

    /**
     * 通过条件删除
     *
     * @param ms
     * @return
     */
    public String delete(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        String tableName=tableName(entityClass);
        if(checkDummyTable(tableName)){
            String dummyColumnName=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNNAME);
            String dummyColumnValue=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNVALUE);
            String updateTimeColumn=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_UPDATETIMECOLUMN);
            String updateTimeFormat=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_UPDATETIMEFORMAT);
            
            List<ColumnValue> dummyColumnValues=new ArrayList<ColumnValue>();
            
            //更新日期

            if(updateTimeColumn != null && !updateTimeColumn.equals("")){
            	sql.append(SqlHelper.getBindValue(updateTimeColumn, SqlHelper.getNow(updateTimeFormat)));
            	dummyColumnValues.add(new ColumnValue(updateTimeColumn,"#{"+updateTimeColumn+"_bind}"));
            }
            
        	sql.append(SqlHelper.updateTable(entityClass, tableName));
        	//更新假删除标识字段值
        	dummyColumnValues.add(new ColumnValue(dummyColumnName,dummyColumnValue));
        	sql.append(SqlHelper.updateDummyColumnValues(dummyColumnValues.toArray(new ColumnValue[dummyColumnValues.size()])));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        }else{
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        }


        return sql.toString();
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String deleteByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        
    	String tableName=tableName(entityClass);

        if(checkDummyTable(tableName)){
        
            String dummyColumnName= PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNNAME);
            String dummyColumnValue=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNVALUE);
            String updateTimeColumn=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_UPDATETIMECOLUMN);
            String updateTimeFormat=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_UPDATETIMEFORMAT);
            
            List<ColumnValue> dummyColumnValues=new ArrayList<ColumnValue>();
            
            //更新日期
            if(updateTimeColumn != null && !"".equals(updateTimeColumn)){
            	sql.append(SqlHelper.getBindValue(updateTimeColumn, SqlHelper.getNow(updateTimeFormat)));
            	dummyColumnValues.add(new ColumnValue(updateTimeColumn,"#{"+updateTimeColumn+"_bind}"));
            }
            
        	sql.append(SqlHelper.updateTable(entityClass, tableName));
        	//更新假删除标识字段值
        	dummyColumnValues.add(new ColumnValue(dummyColumnName,dummyColumnValue));
        	sql.append(SqlHelper.updateDummyColumnValues(dummyColumnValues.toArray(new ColumnValue[dummyColumnValues.size()])));
        	
            sql.append(SqlHelper.wherePKColumns(entityClass));
        }else{
            sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.wherePKColumns(entityClass));
        }

        return sql.toString();
    }
}
