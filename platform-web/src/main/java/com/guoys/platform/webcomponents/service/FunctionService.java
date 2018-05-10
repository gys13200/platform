package com.guoys.platform.webcomponents.service;



import com.guoys.platform.webcomponents.entity.DO.Function;

import java.util.List;

/**
 * 权限操作
 * 
 * @author seawind
 * 
 */
public interface FunctionService {

	/**
	 * 查询用户可以看见的菜单
	 * 
	 * @param username
	 * @return
	 */
	List<Function> findMenu(String username);

}
