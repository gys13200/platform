package com.guoys.platform.webcomponents.service;


import com.guoys.platform.webcomponents.entity.DO.User;

import java.util.List;

public interface UserService {
	void saveUser(User user);

	void updatePassword(User user);

	/**
	 * 根据用户名 查询密码
	 * 
	 * @param username
	 * @return
	 */
	String findPasswordByUsername(String username);

	/**
	 * 查询用户 对应 角色和权限信息
	 * 
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);

	List<User> findAllUser();

	void deleteUser(String ids);

	int totalUser();
}
