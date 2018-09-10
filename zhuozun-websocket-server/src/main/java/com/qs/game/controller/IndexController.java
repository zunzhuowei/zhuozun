package com.qs.game.controller;

import com.qs.game.auth.JWTUtils;
import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.netty.Global;
import com.qs.game.service.IRedisService;
import com.qs.game.utils.AccessUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@Controller
public class IndexController extends BaseController {

    @Autowired
    private Global global;

    @Value("${netty.websocket.host}")
    private String websocketHost;

    @Value("${netty.websocket.port}")
    private String websocketPort;

    @Autowired
    private IRedisService redisService;

    //前往登录界面
    @RequestMapping(value = {"", "login", "login.html"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    //提交登录请求
    @RequestMapping(value = {"", "login", "login.html"}, method = RequestMethod.POST)
    public ModelAndView login(Long uid, String password) {
        ModelAndView modelAndView = null;
        if (Objects.isNull(uid) || StringUtils.isBlank(password)) {
            return new ModelAndView("redirect:login.html");
        }
        String sKey = AccessUtils.createPassWord(16);
        String token = JWTUtils.createToken(uid, sKey);
        redisService.set(CacheKey.RedisPrefix.TOKEN_PREFIX.KEY + uid, token);
        modelAndView = new ModelAndView("redirect:index.html");
        modelAndView.addObject("token", token);
        modelAndView.addObject("uid", uid);
        return modelAndView;
        //return String.format("redirect:index.html?token=%s&uid=%s", token, uid + "");
    }

    //前往聊天室
    @RequestMapping(value = {"index", "index.html"}, method = RequestMethod.GET)
    public String index(Model model,
                        @ModelAttribute("token") String token,
                        @ModelAttribute("uid") Long uid) {
        if (StringUtils.isBlank(token) || Objects.isNull(uid)) {
            return "redirect:login.html";
        }
        model.addAttribute("websocketHost", websocketHost);
        model.addAttribute("websocketPort", websocketPort);
        model.addAttribute("token", token);
        model.addAttribute("uid", uid);
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
        //Global.getChannelGroup().writeAndFlush(new TextWebSocketFrame(message)); //群发

       // global.sendMsgByMatcher("005056fffec00008-00002a2c-00000005-2610053ae3329e41"
       //         , new TextWebSocketFrame(message));// 发给匹配的用户

        //Global.channelGroup.forEach(e -> e.writeAndFlush(new TextWebSocketFrame(message)));//循环发不可取
        return "redirect:mailUi.html";
    }


}
