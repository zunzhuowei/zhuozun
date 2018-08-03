package com.qs.game.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by zun.wei on 2018/7/30.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * Integer utils
 */
public class IntUtils {


    /**
     *  String convert to Integer
     * @param strInt String value
     * @return Integer from String value
     */
    public static Integer str2Int(String strInt) {
        if (StringUtils.isBlank(strInt))
            return null;
        return Integer.parseInt(strInt);
    }


    /**
     *  Object convert to Integer
     * @param objInt Object value
     * @return Integer from Object value
     */
    public static Integer obj2Int(Object objInt) {
        if (Objects.isNull(objInt))
            return null;
        return Integer.parseInt(objInt + StringUtils.EMPTY);
    }



}
