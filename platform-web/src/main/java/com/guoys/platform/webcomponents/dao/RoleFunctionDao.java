package com.guoys.platform.webcomponents.dao;

import com.guoys.platform.persistence.common.BaseMapper;
import com.guoys.platform.webcomponents.entity.DO.RoleFunctionMapping;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by gys on 2018/1/2.
 */

@Mapper
public interface RoleFunctionDao extends BaseMapper<RoleFunctionMapping> {
}
