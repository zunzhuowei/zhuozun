package com.qs.game.controller;

import com.qs.game.annotation.IgnoreSecurity;
import com.qs.game.api.UserApi;
import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * index controller
 *
 */
@Slf4j
@Controller
@RequestMapping("")
public class IndexController extends BaseController {


    @Resource
    private UserApi userApi;

    @Autowired
    private IIndexService indexService;

    @GetMapping({"", "login.html", "login"})
    public String login() {
        return "login";
    }

    @GetMapping({"sign-up.html", "sign-up","signUp","signUp.html"})
    public String signUp() {
        return "sign-up";
    }

    @GetMapping({"forgot.html", "forgot"})
    public String forgot() {
        return "forgot";
    }


    @IgnoreSecurity
    @ResponseBody
    @RequestMapping(value = "register")
    public BaseResult register(@RequestBody User user) {
        log.warn("register user is --::" + user);
        return userApi.add(user);
    }

    @IgnoreSecurity
    @ResponseBody
    @RequestMapping(value = "login")
    public BaseResult login(@RequestBody User user) {
        log.warn("login user is --::" + user);
        return indexService.login(user);
    }


/*
    @ResponseBody
    @RequestMapping("register")
    public BaseResult register(@RequestBody User user) {
        log.warn("register user is --::" + user);
        return BaseResult.getBuilder().setCode(Code.ERROR_0).setMessage("register success")
                .setContent(user).build();
    }
*/

}
