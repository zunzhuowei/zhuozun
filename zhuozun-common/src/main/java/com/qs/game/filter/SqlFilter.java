package com.qs.game.filter;

import com.qs.game.base.exception.SystemException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * SQL过滤
 */
public class SqlFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.contains(keyword)){
                throw new SystemException("包含非法字符");
            }
        }

        return str;
    }

}
