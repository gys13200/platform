package com.guoys.platform.webcomponents.dao;

import com.guoys.platform.persistence.common.BaseMapper;
import com.guoys.platform.webcomponents.entity.DO.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gys on 2018/1/2.
 */

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    Role selectWithFunction(@Param("id") String roleId);
}
