package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.mq.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zun.wei on 2018/8/8 18:00.
 * Description: 消息队列控制器
 */
@RestController
@RequestMapping("publisher")
public class PublisherController extends BaseController {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("{name}")
    public String sendMessage(@PathVariable("name") String name) {
        return publisherService.sendMessage(name);
    }

}
