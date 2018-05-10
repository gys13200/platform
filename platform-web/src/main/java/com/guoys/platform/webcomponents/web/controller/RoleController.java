package com.guoys.platform.webcomponents.web.controller;

import com.guoys.platform.webcomponents.entity.DO.Role;
import com.guoys.platform.webcomponents.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gys on 2017/4/3.
 */

@Controller
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @RequestMapping("/role_list")
    @ResponseBody
    public Object listRole(){
        Map<String, Object> result = new HashMap<String, Object>();
        List<Role> roles = roleService.listRole();
        result.put("total", roles.size());
        result.put("rows", roles);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/toEditRole")
    public ModelAndView toEditRole(String roleId){
        ModelAndView modelAndView = new ModelAndView("admin/role_add");
        Role role = roleService.get(roleId);
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @RequestMapping("/role_save")
    public String saveRole(Role role, String functionIds){
        roleService.saveRole(role, functionIds);
        return "admin/role";
    }

    @RequestMapping("/listRoleAsEnum")
    @ResponseBody
    public Object listRoleAsEnum(){
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<Role> roles = roleService.listRole();
        for (Role role : roles) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", role.getId());
            map.put("text", role.getName());
            result.add(map);
        }

        return result;
    }
}
