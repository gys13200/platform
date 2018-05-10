package com.guoys.platform.webcomponents.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gys on 2018/1/2.
 */
public final class PrimaryKey {

    public static String primaryKey(String prifix){
        String _prifix = prifix == null ? "" : prifix;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return String.format("%s%s", _prifix, sdf.format(new Date()));
    }
}
