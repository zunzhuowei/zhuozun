package com.qs.game;

import com.qs.game.utils.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zun.wei on 2018/11/17 17:19.
 * Description:
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling   //注解开启对定时任务的支持
public class SpringWebSocketApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringWebSocketApp.class, args);
        SpringBeanUtil.setApplicationContext(applicationContext);
    }

}

