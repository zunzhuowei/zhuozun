package com.qs.game.controller;

import com.qs.game.model.User;
import com.qs.game.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@RestController
public class UserController {


    @Resource
    private IUserService userService;

    @GetMapping("/list")
    public Object getAllDeptByList() {
        List<User> users = userService.queryListAll(new HashMap<>());
        for (User user : users) {
            System.out.println("user = " + user.getUsername());
        }
        return users;
    }

    @PostMapping("/add")
    public Object add(User user) {
        System.out.println("provider ---  user = " + user);
        return userService.insertSelective(user);
    }

}
