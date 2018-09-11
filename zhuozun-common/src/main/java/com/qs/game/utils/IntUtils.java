package com.qs.game.utils;

import lombok.NonNull;
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
    public static Integer str2Int(@NonNull String strInt) {
        Integer result = null;
        try {
            result = Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }


    /**
     *  Object convert to Integer
     * @param objInt Object value
     * @return Integer from Object value
     */
    public static Integer obj2Int(@NonNull Object objInt) {
        Integer result = null;
        try {
            result = Integer.parseInt(objInt + StringUtils.EMPTY);
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }



}
