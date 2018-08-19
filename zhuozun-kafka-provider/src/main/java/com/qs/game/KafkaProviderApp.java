package com.qs.game;

import com.qs.game.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  kafka bootStrap app
 *
 */
@SpringBootApplication
@EnableBinding({Channel.class})
public class KafkaProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProviderApp.class, args);
    }

}
