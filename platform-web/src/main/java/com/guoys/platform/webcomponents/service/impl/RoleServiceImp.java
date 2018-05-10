package com.guoys.platform.webcomponents.service.impl;


import com.guoys.platform.persistence.util.StringUtil;
import com.guoys.platform.webcomponents.common.PrimaryKey;
import com.guoys.platform.webcomponents.dao.RoleDao;
import com.guoys.platform.webcomponents.dao.RoleFunctionDao;
import com.guoys.platform.webcomponents.entity.DO.Role;
import com.guoys.platform.webcomponents.entity.DO.RoleFunctionMapping;
import com.guoys.platform.webcomponents.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gys on 2017/4/3.
 */

@Service
@Transactional
public class RoleServiceImp implements RoleService {

    public RoleServiceImp(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    private RoleDao roleDao;

    @Autowired
    private RoleFunctionDao roleFunctionDao;

    @Override
    public List<Role> listRole() {
        return roleDao.selectAll();
    }

    @Override
    public void saveRole(Role role, String functionIds) {
        String roleId = PrimaryKey.primaryKey("ROLE");
        role.setId(roleId);
        roleDao.insert(role);

        if(!StringUtil.isEmpty(functionIds)){
            for (String s : functionIds.split(",")) {
                RoleFunctionMapping roleFunction = new RoleFunctionMapping();
                roleFunction.setFunctionId(s);
                roleFunction.setRoleId(roleId);
                roleFunctionDao.insert(roleFunction);
            }
        }
    }

    @Override
    public Role get(String id) {
        return roleDao.selectWithFunction(id);
    }
}
