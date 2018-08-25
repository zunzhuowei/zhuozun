package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.common.Global;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class IndexController extends BaseController {

    //前往聊天室
    @RequestMapping(value = {"", "index", "index.html"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    //前往后台发邮件view
    @RequestMapping(value = {"mail", "mailUi", "mailUi.html"}, method = RequestMethod.GET)
    public String toMailUi() {
        return "mail";
    }

    //发送邮件给所有人
    @RequestMapping(value = {"mail", "mail.html"}, method = RequestMethod.POST)
    public String sendMail(String message) {
        Global.channelGroup.writeAndFlush(new TextWebSocketFrame(message)); //群发
        //Global.channelGroup.forEach(e -> e.writeAndFlush(new TextWebSocketFrame(message)));//循环发不可取
        return "redirect:mailUi.html";
    }


}
