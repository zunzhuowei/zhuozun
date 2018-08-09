package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.mq.MessageMQ;
import com.qs.game.mq.PublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zun.wei on 2018/8/8 18:00.
 * Description: 消息队列控制器
 */
@Slf4j
@RestController
@RequestMapping("publisher")
@Api(value = "/publisher",tags = {"消息队列生产者控制器"})
public class PublisherController extends BaseController {

    @Autowired
    private PublisherService publisherService;

    //线程池
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);


    @ApiOperation(value="把用户放到消息队列中",notes="随便填写用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "string"),
    })
    @PostMapping("name/{username}")
    public BaseResult sendUserName(@PathVariable("username") String username) {
        MessageMQ messageMQ = publisherService.sendUserName(username);
        if (messageMQ.isSuccess()) {
            return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                    .setMessage("success").setContent(messageMQ).build();
        } else {
            return BaseResult.getBuilder().setCode(Code.ERROR_1).setSuccess(false)
                   .setMessage("fail").setContent(messageMQ).build();
        }
    }



    @ApiOperation(value="把用户日志放到消息队列中",notes="随便填写用户日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLog", value = "用户日志", required = true, paramType = "path", dataType = "string"),
    })
    @PostMapping("log/{userLog}")
    public BaseResult sendUserLog(@PathVariable("userLog") String userLog) {
            MessageMQ messageMQ = publisherService.sendUserLog(userLog);
            if (messageMQ.isSuccess()) {
                return BaseResult.getBuilder().setCode(Code.ERROR_0).setSuccess(true)
                        .setMessage("success").setContent(messageMQ).build();
            } else {
                return BaseResult.getBuilder().setCode(Code.ERROR_1).setSuccess(false)
                        .setMessage("fail").setContent(messageMQ).build();
            }
    }



    @ApiOperation(value="把用户名称放到消息队列中（性能测试）",notes="随便填写用户名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, paramType = "path", dataType = "string"),
    })
    @PostMapping("test/name/{username}")
    public BaseResult testSendUserName(@PathVariable("username") String username) {
        long sum = 0;
        for (; ; ) {
            executor.execute(() -> {
                log.warn("sum testSendUserName = {}",username);
                publisherService.sendUserName(username);
            });
            sum ++;
            if (sum > 1000000) break;
        }
        return BaseResult.getBuilder().setMessage("test").setSuccess(true)
                .setCode(Code.ERROR_0).build();
    }



    @ApiOperation(value="把用户日志放到消息队列中（性能测试）",notes="随便填写用户日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLog", value = "用户日志", required = true, paramType = "path", dataType = "string"),
    })
    @PostMapping("test/log/{userLog}")
    public BaseResult testSendUserLog(@PathVariable("userLog") String userLog) {
        long sum = 0;
        for (; ; ) {
            executor.execute(() -> {
                log.warn("sum testSendUserName = {}",userLog);
                publisherService.sendUserLog(userLog);
            });
            sum ++;
            if (sum > 1000000) break;
        }
        return BaseResult.getBuilder().setMessage("test").setSuccess(true)
                .setCode(Code.ERROR_0).build();
    }


}
