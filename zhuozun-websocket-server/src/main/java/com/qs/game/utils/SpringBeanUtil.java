package com.qs.game.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created by zun.wei on 2018/8/30 16:48.
 * Description: spring 获取bean工具
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;
    static final private Object lock = new Object();

    /**
     * 设置上下文
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (lock) {
            if (SpringBeanUtil.applicationContext == null) {
                SpringBeanUtil.applicationContext = applicationContext;
            }
        }
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
