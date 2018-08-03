package com.qs.game.controller;

import com.qs.game.enum0.DateEnum;
import com.qs.game.model.User;
import com.qs.game.service.IUserService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.qs.game.model.BaseResult;
import com.qs.game.enum0.Code;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *  用户控制器
 */
@RestController
@Api(value = "/user",tags = {"用户中心控制器"})
@RequestMapping("/user")
public class UserController {

    //http://192.168.1.204:8001/swagger-ui.html#/

    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @GetMapping("/list/{page}/{size}/{q}")
    @ApiOperation(value="分页查找用户列表",notes="页码必须为数字，页面大小也不需要为数字，关键字随便填")
    @ApiImplicitParams({//, paramType = "form",
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页面大小", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "q", value = "查询关键字", required = true, paramType = "path", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 401, message = "请求路径未授权"),
            @ApiResponse(code = 403, message = "请求路径被禁止"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 0, message = "请求成功", response = User.class)
    })
    public Object getUserByList(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) {
        List<User> users = userService.queryListAll(new HashMap<>());
        for (User user : users) {
            System.out.println("user = " + user.getUsername());
        }
        return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                .setMessage("success 1 ").setContent(users).build();
    }

    @PostMapping("/add")
    public Object add(User user) {
        System.out.println("provider ---  user = " + user);
        if (Objects.isNull(user.getUsername())
                || Objects.isNull(user.getStatus())
                || Objects.isNull(user.getPassword())) {
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_1)
                    .setMessage("username or password is null").setContent("aaaabbbccc").build();
        }

        Date nowDate = new Date();
        user.setCreateTime(nowDate).setDelStatus(false);
        return userService.insertSelective(user);
    }

}
