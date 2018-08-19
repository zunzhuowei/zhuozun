package com.qs.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class AdminUiApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminUiApp.class, args);
    }

}
