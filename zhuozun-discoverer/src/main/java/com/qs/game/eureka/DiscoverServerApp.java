package com.qs.game.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by zun.wei on 2018/8/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoverServerApp {


    public static void main(String[] args) {
        SpringApplication.run(DiscoverServerApp.class, args);
    }

}
