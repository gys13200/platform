package com.guoys.platform.webcomponents.dao;

import com.guoys.platform.persistence.common.BaseMapper;
import com.guoys.platform.webcomponents.entity.DO.Function;
import com.guoys.platform.webcomponents.entity.DO.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by gys on 2018/1/2.
 */

@Mapper
public interface FunctionDao extends BaseMapper<Function> {

    /**
     * 根据用户查找可见的菜单
     * @param user
     * @return
     */
    List<Function> selectMenu(User user);
}
