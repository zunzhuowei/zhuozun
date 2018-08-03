package qs.game.enum0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qs.game.model.BaseResult;

/**
 * Created by zun.wei on 2018/8/3 15:49.
 * Description: 日志枚举类
 */
public enum LogEnum {

    BaseResultLog(BaseResult.class),
    ;

    public Logger logger;

    LogEnum(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

}
