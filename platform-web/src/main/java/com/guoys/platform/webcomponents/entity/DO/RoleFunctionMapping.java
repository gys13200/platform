package com.guoys.platform.webcomponents.entity.DO;

import com.guoys.platform.persistence.annotation.NameStyle;

import javax.persistence.Table;

/**
 * Created by gys on 2018/1/2.
 */

@Table(name = "platform_role_function")
@NameStyle
public class RoleFunctionMapping {

    private String roleId;

    private String functionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}
