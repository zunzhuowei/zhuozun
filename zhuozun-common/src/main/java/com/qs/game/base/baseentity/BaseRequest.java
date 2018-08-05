package com.qs.game.base.baseentity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 基本请求参数封装类
 *
 */
@Getter
@Setter
public abstract class BaseRequest implements Serializable {

    private String sessionKey;

}
