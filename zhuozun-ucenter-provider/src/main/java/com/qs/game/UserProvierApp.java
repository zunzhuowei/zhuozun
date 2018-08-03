package com.qs.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@EnableSwagger2
@SpringBootApplication
public class UserProvierApp {

    public static void main(String[] args) {
        SpringApplication.run(UserProvierApp.class, args);
    }

}
