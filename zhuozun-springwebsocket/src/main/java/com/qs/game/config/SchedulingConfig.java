package com.qs.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * Created by zun.wei on 2018/11/28.
 * 异步定时调度多线程配置
 */
@Configuration
public class SchedulingConfig  implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度2的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(1));
    }
}
