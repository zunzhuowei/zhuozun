package com.qs.game.annotation;

import java.lang.annotation.*;

/**
 * Created by zun.wei on 2018/8/14 12:42.
 * Description:自定义注解       标识是否忽略REST安全性检查
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSecurity {

}
