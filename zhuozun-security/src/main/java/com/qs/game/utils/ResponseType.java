package com.qs.game.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zun.wei on 2018/8/14 15:13.
 * Description: 返回类型
 */
public enum ResponseType {

    ModelAndView("ModelAndView"),
    ResponseEntity("ResponseEntity"),;

    public String SIMPLE_NAME;

    ResponseType(String simpleName) {
        this.SIMPLE_NAME = simpleName;
    }

    public boolean isType(String simpleName) {
        return StringUtils.equals(simpleName, SIMPLE_NAME);
    }

}
