package com.qs.game.base.baseentity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 基本请求参数封装类
 *
 */
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "基本请求对象")
public abstract class BaseRequest implements Serializable {

    @ApiModelProperty(value = "sessionKey",required = true)
    private String sessionKey;

}
