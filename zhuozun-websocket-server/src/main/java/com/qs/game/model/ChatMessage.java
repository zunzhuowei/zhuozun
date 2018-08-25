package com.qs.game.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.qs.game.common.Constants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


@Data
@Accessors(chain = true)
public class ChatMessage implements Serializable {

    //发送消息则
    private UserInfo from;

    //发送内容
    private String message;

    //接收者列表
    private Map<String, UserInfo> to;

    //发送时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public ChatMessage() {

    }


    public ChatMessage(UserInfo from,String message) {
        this.from = from;
        this.message = message;
        this.to = Constants.onlines;
        this.createTime = new Date();
    }


}
