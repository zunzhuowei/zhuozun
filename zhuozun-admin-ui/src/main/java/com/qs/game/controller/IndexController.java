package com.qs.game.controller;

import com.qs.game.annotation.IgnoreSecurity;
import com.qs.game.api.UserApi;
import com.qs.game.api.entity.UserRequest;
import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.SecurityConstants;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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


    @ResponseBody
    @RequestMapping(value = "register")
    public BaseResult register(@RequestBody User user) {
        log.warn("register user is --::" + user);
        return userApi.add(user);
    }

    @ResponseBody
    @RequestMapping(value = "login")
    public BaseResult login(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        log.warn("login userRequest is --::" + userRequest);
        String tk = request.getHeader(SecurityConstants.DEFAULT_TOKEN_NAME);
        return indexService.login(userRequest);
    }


    @ResponseBody
    @RequestMapping(value = "/test/login/aa")
    public BaseResult testLogin(@RequestBody UserRequest userRequest) {
        log.warn("login userRequest is --::" + userRequest);
        return indexService.login(userRequest);
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
