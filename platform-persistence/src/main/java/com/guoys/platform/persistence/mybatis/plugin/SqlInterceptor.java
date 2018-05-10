package com.guoys.platform.persistence.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.guoys.platform.persistence.extension.ExtensionLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

import com.guoys.platform.persistence.mybatis.plugin.SqlStatement.CommandType;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * 
 * SQL拦截器，只拦截数据库的变更(新增/修改/删除),将拦截的SQL语句对象交由处理器{@link SqlStatementHandler}处理
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
/*
 * 修改历史
 * $Log$
 */
@Intercepts(value = { 
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
		})
public class SqlInterceptor implements Interceptor {
	
	static ExecutorService executorService;
		
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		Object result = null;
		if (target instanceof Executor) {
			long start = System.currentTimeMillis();
			/** 执行方法 */
			result = invocation.proceed();
			long end = System.currentTimeMillis();
			final Object[] args = invocation.getArgs();

			// 获取原始的ms
			MappedStatement ms = (MappedStatement) args[0];
			String commandName = ms.getSqlCommandType().name();
			CommandType commandType=null;
			if (commandName.startsWith("INSERT")) {
				commandType=CommandType.INSERT;
			} else if (commandName.startsWith("UPDATE")) {
				commandType=CommandType.UPDATE;
			} else if (commandName.startsWith("DELETE")) {
				commandType=CommandType.DELETE;
			} else if (commandName.startsWith("SELECT")) {
				commandType=CommandType.SELECT;
			}
	
			Set<String> handlerNames = ExtensionLoader.getExtensionLoader(SqlStatementHandler.class).getSupportedExtensions();
			if(handlerNames != null && handlerNames.size()>0){
				Object parameterObject = args[1];
				BoundSql boundSql = ms.getBoundSql(parameterObject);
				String sql = new String(boundSql.getSql());
				
				final SqlStatement sqlStatement=new SqlStatement();
				sqlStatement.setCommandType(commandType);
				sqlStatement.setCost(end-start);
				sqlStatement.setSqlId(ms.getId());
				List<KeyValuePair> parameters=new ArrayList<KeyValuePair>();
				
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

				MetaObject metaObject = MetaObject.forObject(parameterObject, ms.getConfiguration().getObjectFactory(),
                        ms.getConfiguration().getObjectWrapperFactory(), ms.getConfiguration().getReflectorFactory());
				//MetaObject metaObject=  MetaObject.forObject(parameterObject, ms.getConfiguration().getObjectFactory(),ms.getConfiguration().getObjectWrapperFactory());
				if(parameterMappings!=null){
					for(ParameterMapping pm:parameterMappings){
						Object value=null;
//						if(parameterObject instanceof Map){
//							value = ((Map<?,?>)parameterObject).get(pm.getProperty());
//						}
						if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass()))   {    
							value = parameterObject;    
		                }
						else if (boundSql.hasAdditionalParameter(pm.getProperty())) {    
							value = boundSql.getAdditionalParameter(pm.getProperty());    
		                }
						else {
							value=metaObject!=null?metaObject.getValue(pm.getProperty()):null;
			            }
						
						parameters.add(new KeyValuePair(pm.getProperty(), value,pm.getJavaType().getSimpleName()));
						
					}
				}
				
				sqlStatement.setSql(sql);
				sqlStatement.setParameters(parameters);
				sqlStatement.setCreateTime(System.currentTimeMillis());
				Statement statement=null;
				try{
					statement = CCJSqlParserUtil.parse(sql);
				}catch(JSQLParserException jsqle){
					//SQL解析出错则去掉where语句
					String sqlLower=sql.toLowerCase();
					if(sqlLower.indexOf("where")!=-1){
						String nonWhereSql= StringUtils.substringBefore(sqlLower, "where");
						try{
							statement = CCJSqlParserUtil.parse(nonWhereSql);
						}catch(Throwable ingore){
						}
					}
					
				}
				
				if(statement==null)
					return result;
				
				TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
				List<String> tableList=new ArrayList<String>();
				if(commandType==CommandType.SELECT){
					tableList = tablesNamesFinder.getTableList((Select) statement);
				}
				else if(commandType==CommandType.INSERT){
					tableList = tablesNamesFinder.getTableList((Insert) statement);
				}
				else if(commandType==CommandType.UPDATE){
					tableList = tablesNamesFinder.getTableList((Update) statement);
				}
				else if(commandType==CommandType.DELETE){
					tableList = tablesNamesFinder.getTableList((Delete) statement);
				}
				//INSERT/UPDATE/DELETE语句有且仅有一个表名
				String tableName=tableList!=null && tableList.size()>0?tableList.get(0):"";
				if(tableName!=null)
					tableName=tableName.toUpperCase();
				
				sqlStatement.setTable(tableName);
				
				for(String handlerName : handlerNames){
					final SqlStatementHandler sqlStatementHandler = ExtensionLoader.getExtensionLoader(SqlStatementHandler.class).getExtension(handlerName);
					if(sqlStatementHandler!=null && containCommandType(sqlStatementHandler.supportCommands(),commandType)){
						if(containTable(sqlStatementHandler.includeTables(), sqlStatementHandler.excludeTables(),tableName)){
							executorService.execute(new Runnable(){
								@Override
								public void run() {
									sqlStatementHandler.handle(sqlStatement);
								}

							});
							
						}

					}
				}
			}

		}
		
		return result;
	}
	
	
	private boolean containCommandType(CommandType[] commandTypes,CommandType commandType){
		if(commandTypes!=null){
			for(CommandType ct:commandTypes){
				if(ct==commandType){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean containTable(String[] includeTables,String[] excludeTables,String table){
		if(excludeTables!=null){
			for(String excludeTable:excludeTables){
				excludeTable=excludeTable.toUpperCase();
				
				if(table.equalsIgnoreCase(excludeTable)){
					return false;
				}
				if(excludeTable.startsWith("*") && table.endsWith(excludeTable.substring(1))){
					return false;
				}
				if(excludeTable.endsWith("*") && table.startsWith(excludeTable.substring(0, excludeTable.length()-1))){
					return false;
				}
				if(excludeTable.startsWith("*") && excludeTable.endsWith("*") && table.indexOf(excludeTable.substring(1, excludeTable.length()-1))!=-1){
					return false;
				}
			}
		}
		
		if(includeTables!=null){
			for(String t:includeTables){
				t=t.toUpperCase();
				
				if(t.equals("*")){
					return true;
				}
				if(t.startsWith("*") && table.endsWith(t.substring(1))){
					return true;
				}
				if(t.endsWith("*") && table.startsWith(t.substring(0, t.length()-1))){
					return true;
				}
				if(t.startsWith("*") && t.endsWith("*") && table.indexOf(t.substring(1, t.length()-1))!=-1){
					return true;
				}
				
				if(t.equalsIgnoreCase(table)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		executorService=Executors.newFixedThreadPool(5);
	}
}