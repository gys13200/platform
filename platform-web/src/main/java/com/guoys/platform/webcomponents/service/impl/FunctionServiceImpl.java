package com.guoys.platform.webcomponents.service.impl;


import com.guoys.platform.webcomponents.dao.FunctionDao;
import com.guoys.platform.webcomponents.entity.DO.Function;
import com.guoys.platform.webcomponents.entity.DO.User;
import com.guoys.platform.webcomponents.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDAO;

	public List<Function> findMenu(String username) {
		// 隐含条件 generateMenu = 1 ， order by zindex
		User user = new User();
		user.setUsername(username);
		return functionDAO.selectMenu(user);
	}

}
