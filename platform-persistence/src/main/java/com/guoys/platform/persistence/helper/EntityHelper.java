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

package com.guoys.platform.persistence.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

import com.guoys.platform.persistence.annotation.ColumnType;
import com.guoys.platform.persistence.annotation.NameStyle;
import com.guoys.platform.persistence.code.IdentityDialect;
import com.guoys.platform.persistence.code.Style;
import com.guoys.platform.persistence.entity.Config;
import com.guoys.platform.persistence.entity.EntityColumn;
import com.guoys.platform.persistence.entity.EntityField;
import com.guoys.platform.persistence.entity.EntityTable;
import com.guoys.platform.persistence.util.StringUtil;

/**
 * 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
 * <p/>
 * 
 *
 * 
 */
public class EntityHelper {

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new HashMap<Class<?>, EntityTable>();

    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null) {
            throw new RuntimeException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 获取默认的orderby语句
     *
     * @param entityClass
     * @return
     */
    public static String getOrderByClause(Class<?> entityClass) {
        EntityTable table = getEntityTable(entityClass);
        if (table.getOrderByClause() != null) {
            return table.getOrderByClause();
        }
        StringBuilder orderBy = new StringBuilder();
        for (EntityColumn column : table.getEntityClassColumns()) {
            if (column.getOrderBy() != null) {
                if (orderBy.length() != 0) {
                    orderBy.append(",");
                }
                orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
            }
        }
        table.setOrderByClause(orderBy.toString());
        return table.getOrderByClause();
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }
    
    /**
     * 获取唯一索引信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getUniqueIndexColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getUniqueIndexColumns();
    }
    
        

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        }
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                //不等的时候分几种情况，例如`DESC`
                if (entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1).equalsIgnoreCase(entityColumn.getProperty())) {
                    selectBuilder.append(",");
                } else {
                    selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                }
            } else {
                selectBuilder.append(",");
            }
        }
        entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
        return entityTable.getBaseSelect();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     * @deprecated 4.x版本移除该方法
     */
    @Deprecated
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /**
     * 获取主键的Where语句
     *
     * @param entityClass
     * @return
     * @deprecated 4.x版本移除该方法
     */
    @Deprecated
    public static String getPrimaryKeyWhere(Class<?> entityClass) {
        Set<EntityColumn> entityColumns = getPKColumns(entityClass);
        StringBuilder whereBuilder = new StringBuilder();
        for (EntityColumn column : entityColumns) {
            whereBuilder.append(column.getColumnEqualsHolder()).append(" AND ");
        }
        return whereBuilder.substring(0, whereBuilder.length() - 4);
    }

    /**
     * 初始化实体属性
     *
     * @param entityClass
     * @param config
     */
    public static synchronized void initEntityNameMap(Class<?> entityClass, Config config) {
        if (entityTableMap.get(entityClass) != null) {
            return;
        }
        Style style = config.getStyle();
        //style，该注解优先于全局配置
        if (entityClass.isAnnotationPresent(NameStyle.class)) {
            NameStyle nameStyle = entityClass.getAnnotation(NameStyle.class);
            style = nameStyle.value();
        }

        //创建并缓存EntityTable
        EntityTable entityTable = null;
        
        List<String> uniqueColumnNames=new ArrayList<String>();
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!table.name().equals("")) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
            //获取唯一索引定义
            UniqueConstraint[] uniques=table.uniqueConstraints();
            if(uniques!=null && uniques.length>0){
            	for(UniqueConstraint uc:uniques){
            		uniqueColumnNames.addAll(Arrays.asList(uc.columnNames()));
            	}
            }
        }
        
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            //可以通过stye控制
            entityTable.setName(StringUtil.convertByStyle(entityClass.getSimpleName(), style));
        }
        entityTable.setEntityClassColumns(new LinkedHashSet<EntityColumn>());
        entityTable.setEntityClassPKColumns(new LinkedHashSet<EntityColumn>());
        entityTable.setUniqueIndexColumns(new LinkedHashSet<EntityColumn>());
        //处理所有列
        List<EntityField> fields = null;
        if (config.isEnableMethodAnnotation()) {
            fields = FieldHelper.getAll(entityClass);
        } else {
            fields = FieldHelper.getFields(entityClass);
        }
        for (EntityField field : fields) {
            processField(entityTable, style, field,uniqueColumnNames);
        }
        //当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().size() == 0) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }
        
        entityTableMap.put(entityClass, entityTable);
    }

    /**
     * 处理一列
     *
     * @param entityTable
     * @param style
     * @param field
     * @param uniqueIndexColumnNames 唯一索引字段名
     */
    private static void processField(EntityTable entityTable, Style style, EntityField field,List<String> uniqueIndexColumnNames) {
        //排除字段
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }
        //Id
        EntityColumn entityColumn = new EntityColumn(entityTable);
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
        }
        //Column
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name();
            entityColumn.setUpdatable(column.updatable());
            entityColumn.setInsertable(column.insertable());
        }
        //ColumnType
        if (field.isAnnotationPresent(ColumnType.class)) {
            ColumnType columnType = field.getAnnotation(ColumnType.class);
            //column可以起到别名的作用
            if (StringUtil.isEmpty(columnName) && StringUtil.isNotEmpty(columnType.column())) {
                columnName = columnType.column();
            }
            if (columnType.jdbcType() != JdbcType.UNDEFINED) {
                entityColumn.setJdbcType(columnType.jdbcType());
            }
            if (columnType.typeHandler() != UnknownTypeHandler.class) {
                entityColumn.setTypeHandler(columnType.typeHandler());
            }
        }
        //表名
        if (StringUtil.isEmpty(columnName)) {
            columnName = StringUtil.convertByStyle(field.getName(), style);
        }
        entityColumn.setProperty(field.getName());
        entityColumn.setColumn(columnName);
        entityColumn.setJavaType(field.getJavaType());
        //OrderBy
        if (field.isAnnotationPresent(OrderBy.class)) {
            OrderBy orderBy = field.getAnnotation(OrderBy.class);
            if (orderBy.value().equals("")) {
                entityColumn.setOrderBy("ASC");
            } else {
                entityColumn.setOrderBy(orderBy.value());
            }
        }
        
        //添加唯一索引字段
        for(String uniqueIndexColumnName:uniqueIndexColumnNames){
        	if(columnName.equalsIgnoreCase(uniqueIndexColumnName)){
        		entityTable.getUniqueIndexColumns().add(entityColumn);
        	}
        }
        
        //主键策略 - Oracle序列，MySql自动增长，UUID
        if (field.isAnnotationPresent(SequenceGenerator.class)) {
            SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
            if (sequenceGenerator.sequenceName().equals("")) {
                throw new RuntimeException(entityTable.getEntityClass() + "字段" + field.getName() + "的注解@SequenceGenerator未指定sequenceName!");
            }
            entityColumn.setSequenceName(sequenceGenerator.sequenceName());
        } else if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue.generator().equals("UUID")) {
                entityColumn.setUuid(true);
            } else if (generatedValue.generator().equals("JDBC")) {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator("JDBC");
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else {
                //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
                //允许通过拦截器参数设置公共的generator
                if (generatedValue.strategy() == GenerationType.IDENTITY) {
                    //mysql的自动增长
                    entityColumn.setIdentity(true);
                    if (!generatedValue.generator().equals("")) {
                        String generator = null;
                        IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(generatedValue.generator());
                        if (identityDialect != null) {
                            generator = identityDialect.getIdentityRetrievalStatement();
                        } else {
                            generator = generatedValue.generator();
                        }
                        entityColumn.setGenerator(generator);
                    }
                } else {
                    throw new RuntimeException(field.getName()
                            + " - 该字段@GeneratedValue配置只允许以下几种形式:" +
                            "\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")" +
                            "\n2.useGeneratedKeys的@GeneratedValue(generator=\\\"JDBC\\\")  " +
                            "\n3.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                }
            }
        }
        entityTable.getEntityClassColumns().add(entityColumn);
        if (entityColumn.isId()) {
            entityTable.getEntityClassPKColumns().add(entityColumn);
        }
    }
}