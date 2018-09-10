package com.qs.game.model.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 核心实体类
 */
@Data
@Accessors(chain = true)
public class Core implements Serializable {

//    private String nickName; //昵称
//
//    private String avatarUrl; //头像
//
//    private Integer gender; //性别
//
//    private String city; //城市
//
//    private String province; //省份
//
//    private String country; //国家
//
//    private String language; //语言


    private String code; //小游戏登录code

    private Integer type; //登录类型

    private String encryptedData; //加密数据

    private String iv; //加密向量


}
