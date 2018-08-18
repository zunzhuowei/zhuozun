package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.channel.ShopChannel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  start to use kafka controller
 *
 */
@RestController
@RequestMapping("kafka")
public class KafkaStartController extends BaseController {

    @Resource(name = ShopChannel.SHOP_OUTPUT)
    private MessageChannel sendShopMessageChannel;

    @GetMapping("/sendMsg")
    public String sendShopMessage(String content) {
        boolean isSendSuccess = sendShopMessageChannel.
                send(MessageBuilder.withPayload(content).build());
        return isSendSuccess ? "发送成功" : "发送失败";
    }

    @StreamListener(ShopChannel.SHOP_INPUT)
    public void receive(Message<String> message) {
        System.out.println(message.getPayload());
    }

}
