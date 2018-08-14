package com.qs.game.utils;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/14 12:27.
 * Description: Collection 工具类
 */
public class CollectionUtil {


    public static boolean isNotEmpty(Collection<?> c){
        if (Objects.nonNull(c) && c.size() != 0 ) {
            return true;
        }
        return false;
    }

}
