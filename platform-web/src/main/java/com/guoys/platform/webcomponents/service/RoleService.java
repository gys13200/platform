package com.guoys.platform.webcomponents.service;



import com.guoys.platform.webcomponents.entity.DO.Role;

import java.util.List;

/**
 * Created by gys on 2017/4/3.
 */
public interface RoleService {

    List<Role> listRole();

    void saveRole(Role role, String functionIds);

    Role get(String id);
}
