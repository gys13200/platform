package com.guoys.platform.webcomponents.dao;


import com.guoys.platform.persistence.common.BaseMapper;
import com.guoys.platform.webcomponents.entity.DO.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by gys on 2018/1/2.
 */

@Mapper
public interface UserDao extends BaseMapper<User> {


    User selectUserByUsername(String username);
}
