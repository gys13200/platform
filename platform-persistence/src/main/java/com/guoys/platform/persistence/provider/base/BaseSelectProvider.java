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

import com.guoys.platform.commons.configuration.ConfigurationConstants;
import com.guoys.platform.commons.configuration.PropertiesUtil;
import org.apache.ibatis.mapping.MappedStatement;

import com.guoys.platform.persistence.helper.MapperHelper;
import com.guoys.platform.persistence.helper.MapperTemplate;
import com.guoys.platform.persistence.helper.SqlHelper;

/**
 * BaseSelectProvider实现类，基础方法实现类
 *
 * 
 */
public class BaseSelectProvider extends MapperTemplate {

    public BaseSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String selectOne(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String tableName=tableName(entityClass);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName));
        
        if(checkDummyTable(tableName)){
            handleDummyTable(entityClass, sql);
            
        }else{
        	sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        }
        
        return sql.toString();
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String select(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String tableName=tableName(entityClass);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        
        if(checkDummyTable(tableName)){
            handleDummyTable(entityClass, sql);
            
        }else{
        	sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        }
        
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    private void handleDummyTable(Class<?> entityClass, StringBuilder sql) {
        String dummyColumnName= PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNNAME);
        String dummyColumnValue=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNVALUE);
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),SqlHelper.filterDummyColumn(dummyColumnName,dummyColumnValue)));
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String selectByRowBounds(MappedStatement ms) {
        return select(ms);
    }

    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public String selectByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        
        sql.append(SqlHelper.wherePKColumns(entityClass));
        
        return sql.toString();
    }

    /**
     * 查询总数
     *
     * @param ms
     * @return
     */
    public String selectCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String tableName=tableName(entityClass);
        
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName));
        if(checkDummyTable(tableName)){
            handleDummyTable(entityClass, sql);
        }
        else{
        	sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        }
        return sql.toString();
    }
    
    /**
     * 查询记录数，包括假删除的数据
     * @param ms
     * @return
     */
    public String selectCountByOriginal(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        String tableName=tableName(entityClass);
        
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty(),null));
        
        return sql.toString();
    }
    


    /**
     * 查询全部结果
     *
     * @param ms
     * @return
     */
    public String selectAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String tableName=tableName(entityClass);
        
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName));
        
        if(checkDummyTable(tableName)){
            String dummyColumnName=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNNAME);
            String dummyColumnValue=PropertiesUtil.getProperty(ConfigurationConstants.PERSISTENCE_DUMMY_DELETE_COLUMNVALUE);
    
        	sql.append(SqlHelper.whereCondition(SqlHelper.filterDummyColumn(dummyColumnName, dummyColumnValue)));
        }
        
        sql.append(SqlHelper.orderByDefault(entityClass));

        
        return sql.toString();
    }
}
