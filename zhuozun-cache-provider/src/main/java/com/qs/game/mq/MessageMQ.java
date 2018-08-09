package com.qs.game.mq;

import com.qs.game.enum0.MessageOnwer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Data
@Accessors(chain = true)
@ApiModel(description= "消息队列实体类")
public class MessageMQ implements Serializable {

    @ApiModelProperty(value = "消息id", required = true)
    private Long msgId;

    @ApiModelProperty(value = "队列内容", required = true)
    private String content;

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value = "消息归属者", required = true)
    private MessageOnwer onwer;

}
