package com.qs.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by zun.wei on 2018/12/5 9:32.
 * Description:
 */
@SpringBootApplication
@EnableScheduling   //注解开启对定时任务的支持
public class SpringWebSocketClientApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebSocketClientApp.class, args);
    }

}
