package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  start to use kafka controller
 *
 */
@Slf4j
@RestController
@RequestMapping("kafka")
public class KafkaStartController extends BaseController {

    @Resource(name = Channel.SHOP_OUTPUT)
    private MessageChannel sendShopMessageChannel;

    @Resource(name = Channel.USER_OUTPUT)
    private MessageChannel sendUserMessageChannel;


    @GetMapping("/sendMsg")
    public String sendShopMessage(String content) {
        Message message = MessageBuilder.withPayload(content)
                .setHeader("headerName","headerValue")
                .setHeader("headerName2","headerValue2")
                .build();
        boolean isSendSuccess = sendShopMessageChannel.
                send(message);
        return isSendSuccess ? "发送成功" : "发送失败";
    }

    @StreamListener(Channel.SHOP_INPUT)
    public void receive(Message<String> message) {
        MessageHeaders messageHeaders = message.getHeaders();
        for (Map.Entry<String, Object> entry : messageHeaders.entrySet()) {
            log.info("KafkaStartController receive messageHeaders --::{} , {}", entry.getKey(), entry.getValue());
        }
        log.info("message.getPayload = {}",message.getPayload());
    }


    @GetMapping("/sendUserMsg")
    public String sendUserMessage(String content) {
        Message message = MessageBuilder.withPayload(content)
                .setHeader("headerName","headerValue")
                .setHeader("headerName2","headerValue2")
                .build();
        boolean isSendSuccess = sendUserMessageChannel.
                send(message);
        return isSendSuccess ? "发送成功" : "发送失败";
    }

    @StreamListener(Channel.USER_INPUT)
    public void receiveUserMsg(Message<String> message) {
        MessageHeaders messageHeaders = message.getHeaders();
        for (Map.Entry<String, Object> entry : messageHeaders.entrySet()) {
            log.info("KafkaStartController receiveUserMsg messageHeaders --::{} , {}", entry.getKey(), entry.getValue());
        }
        log.info("message.getPayload = {}",message.getPayload());
    }

}
