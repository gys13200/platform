package com.guoys.platform.webcomponents.service.impl;

import com.guoys.platform.commons.util.MD5Utils;
import com.guoys.platform.persistence.util.StringUtil;
import com.guoys.platform.webcomponents.common.PrimaryKey;
import com.guoys.platform.webcomponents.dao.UserDao;
import com.guoys.platform.webcomponents.entity.DO.User;
import com.guoys.platform.webcomponents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDAO;

	@Override
	public void saveUser(User user) {
		user.setPassword(MD5Utils.md5(user.getPassword()));
		user.setId(PrimaryKey.primaryKey("USER"));
		userDAO.insert(user);
	}

	@Override
	public void updatePassword(User user) {
		user.setPassword(MD5Utils.md5(user.getPassword()));
		userDAO.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<User> findAllUser() {
		return userDAO.selectAll();
	}

	@Override
	public String findPasswordByUsername(String username) {
        User condition = new User();
        condition.setUsername(username);
        User user = userDAO.selectOne(condition);
        return user == null ? null : user.getPassword();
	}
    @Override
	public User findUserByUsername(String username) {
        return userDAO.selectUserByUsername(username);
	}

    @Override
    public void deleteUser(String ids) {
        if(StringUtil.isNotEmpty(ids)){
			for (String s : ids.split(",")) {
				userDAO.deleteByPrimaryKey(s);
			}
        }
    }

    @Override
    public int totalUser() {
        return userDAO.selectCount(new User());
    }
}
