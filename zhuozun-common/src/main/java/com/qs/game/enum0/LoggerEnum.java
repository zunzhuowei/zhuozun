package com.qs.game.enum0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qs.game.model.BaseResult;

/**
 * Created by zun.wei on 2018/8/3 15:49.
 * Description: 日志枚举类
 */
public enum LoggerEnum {

    BaseResultLog(BaseResult.class),
    ;

    public Logger logger;

    LoggerEnum(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

}
