package com.guoys.platform.webcomponents.web.controller;

import com.guoys.platform.webcomponents.common.ExtTreeNodeBuilder;
import com.guoys.platform.webcomponents.constant.GlobalConstant;
import com.guoys.platform.webcomponents.entity.DO.Function;
import com.guoys.platform.webcomponents.entity.DO.User;
import com.guoys.platform.webcomponents.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限操作
 * 
 * @author seawind
 * 
 */
@Controller
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/function_menu.do")
	@ResponseBody
	public Object menu(HttpSession session) {
		// 查询当前用户具有功能
		User user = (User) session.getAttribute("user");

		// 调用业务层 ，返回Function对象集合
		List<Function> functions = functionService.findMenu(user.getUsername());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", functions.size());
		result.put("rows", functions);

		return result;
	}

	@RequestMapping("/function_list.do")
	@ResponseBody
	public Object list(HttpSession session) {
		// 查询当前用户具有功能
		User user = (User) session.getAttribute("user");

		// 调用业务层 ，返回Function对象集合
		List<Function> functions = functionService.findMenu(user.getUsername());


		return null;
	}

	@RequestMapping("/function_menu_ext.do")
	@ResponseBody
	public Object menuForExt(HttpSession session, String selectModel){
		User user = (User) session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USERNAME);
		// 调用业务层 ，返回Function对象集合
		List<Function> functions = functionService.findMenu(user.getUsername());
		//if(selectModel != null &&)
		return new ExtTreeNodeBuilder(functions).build();
	}


}
