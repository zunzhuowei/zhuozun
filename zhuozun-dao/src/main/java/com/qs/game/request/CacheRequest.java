package com.qs.game.request;

import com.qs.game.enum0.CacheType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/6.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 缓存请求参数包装类
 *
 */
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "缓存请求参数包装类")
public class CacheRequest<Value> implements Serializable {

    @ApiModelProperty(value = "缓存key", required = true)
    private String key;

    @ApiModelProperty(value = "缓存值")
    private Value value;

    @ApiModelProperty(value = "缓存时间(秒)", required = true)
    private int live = 0;

    @ApiModelProperty(value = "缓存类型", required = true)
    private CacheType cacheType;

}
