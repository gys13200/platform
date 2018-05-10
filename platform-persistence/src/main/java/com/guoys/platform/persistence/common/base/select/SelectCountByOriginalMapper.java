/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Bosssoft Co, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年5月31日
 *******************************************************************************/


package com.guoys.platform.persistence.common.base.select;

import com.guoys.platform.persistence.provider.base.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;


/**
 * TODO 此处填写 class 信息
 *
 * @author wengzr (mailto:wzr5908@bosssoft.com.cn)
 */
public interface SelectCountByOriginalMapper<T> {

    /**
     * 根据实体中的属性查询总数, 查询结果也包含假删除记录
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    int selectCountByOriginal(T record);
}

/*
 * 修改历史
 * $Log$ 
 */