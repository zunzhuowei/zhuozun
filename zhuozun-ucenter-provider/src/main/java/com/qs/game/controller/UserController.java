package com.qs.game.controller;

import com.alibaba.fastjson.JSON;
import com.qs.game.api.RedisApi;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IUserService;
import com.qs.game.service.impl.RedisService;
import com.whalin.MemCached.MemCachedClient;
import io.swagger.annotations.*;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

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

    @Resource
    private RedisService redisService;

    @Resource
    private MemCachedClient memCachedClient;

    @Resource
    private MemcachedClient memcachedClient;

    @Resource
    private RedisApi redisApi;

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
    public Object getUserByList(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) throws InterruptedException, MemcachedException, TimeoutException {
        List<User> users = userService.queryListAll(new HashMap<>());
        //if (true) throw new NullPointerException();
        for (User user : users) {
            System.out.println("user = " + user.getUsername());
            boolean b = memCachedClient.set(user.getUsername(), user);
            logger.warn("b = {}", b);
            Object obj = memCachedClient.get(user.getUsername());
            logger.warn("obj = {}", obj);

            boolean bb = memcachedClient.set(user.getUsername() + "1",0, user);
            Object objj = memcachedClient.get(user.getUsername() + "1");
            logger.warn("bb = {}", bb);
            logger.warn("objj = {}", objj);
        }

        return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                .setMessage("success 1 ").setContent(users).build();
    }

    @PostMapping("/add")
    public BaseResult add(@RequestBody User user) {
        System.out.println("provider ---  user = " + user);
        if (Objects.isNull(user.getUsername())
                || Objects.isNull(user.getPassword())) {
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_1)
                    .setMessage("username or password is null").setContent(user).build();
        }

        Date nowDate = new Date();
        user.setCreateTime(nowDate).setDelStatus(false);
        redisService.set("user", JSON.toJSONString(user));
        int insert = userService.insertSelective(user);
        return insert > 0 ?
                BaseResult.getBuilder().setSuccess(true).setCode(Code.ERROR_0)
                        .setMessage("add success").setContent(user).build()
                : BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_2)
                .setMessage("insert db error").setContent(user).build();
    }

    /**
     *  根据id 获取用户信息
     * @param id user id
     * @return BaseResult
     */
    @GetMapping("get/{id}")
    public BaseResult getUserById(@PathVariable Long id) {
        return redisApi.getUserById(id);
    }


    @PostMapping("set/json")
    public BaseResult saveUserByJson(User user) {
        return redisApi.saveUserByJson(JSON.toJSONString(user));
    }

}
